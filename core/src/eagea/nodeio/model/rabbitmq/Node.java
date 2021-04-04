package eagea.nodeio.model.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import eagea.nodeio.model.Model;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Handle all the RabbitMQ communications with other players.
 */
public class Node
{
    private Channel mChannel;
    private final Model mModel;

    private static final String EXCHANGE_NODES = "nodes";
    private static final String QUEUE_NEWNODE = "newnode";

    public Node(Model model)
    {
        mModel = model;
        initConnection("localhost");
    }

    private void initConnection(String host)
    {
        // Initiate connection.
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        try
        {
            Connection connection = factory.newConnection();
            mChannel = connection.createChannel();
            mChannel.exchangeDeclare(EXCHANGE_NODES,"fanout");
            //Queue for connection
            mChannel.queueDeclare(QUEUE_NEWNODE,false,false,false,null);
            mChannel.queueBind(QUEUE_NEWNODE,EXCHANGE_NODES,"");
            mChannel.basicConsume(QUEUE_NEWNODE,true,this::onReceiveNewNode,consumerTag -> {});
        }
        catch (TimeoutException | IOException e)
        {
            e.printStackTrace();
        }
    }

    //Connection to the game
    public void notifyNewNode(PlayerM player, ZoneM zone)
    {
        try
        {
            ArrayList<Object> newNode = new ArrayList<Object>();
            newNode.add(player);  newNode.add(zone);
            mChannel.basicPublish(EXCHANGE_NODES,QUEUE_NEWNODE,null, SerializationUtils.serialize(newNode));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void onReceiveNewNode(String consumertag, Delivery delivery)
    {
        ArrayList<Object> newNode = (ArrayList<Object>) SerializationUtils.deserialize(delivery.getBody());
        mModel.addPlayer((PlayerM) newNode.get(0));
        mModel.getMap().add((ZoneM) newNode.get(1));
    }

    //When a player move, update positions for all other nodes
    public void MovingPlayer()
    {

    }

    //Disconnection of the game
    public void Disconnect()
    {

    }
}