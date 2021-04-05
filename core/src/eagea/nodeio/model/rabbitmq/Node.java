package eagea.nodeio.model.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import eagea.nodeio.model.Model;
import eagea.nodeio.model.rabbitmq.action.Action;
import eagea.nodeio.model.rabbitmq.action.Disconnection;
import eagea.nodeio.model.rabbitmq.action.Move;

/**
 * Handle all the RabbitMQ communications with other players.
 */
public class Node
{
    // RabbitMQ server URL.
    private final String AMPQ_URI = "amqps://gkpyuliw:ifaqqxycOvHebZEJVbHubCCu8ovJA9zn@rat"
            + ".rmq2.cloudamqp.com/gkpyuliw";

    // Host.
    private final String HOST_QUEUE_URI = "rabbitmq://host/queue";

    private Channel mChannel;
    private final Model mModel;

    private static final String EXCHANGE_NODES = "exnodes";
    private static final String QUEUE_NODES = "qnodes";

    public Node(Model model)
    {
        mModel = model;
        initConnection();
        checkIfHost();
    }

    private void checkIfHost()
    {
        try
        {
            AMQP.Queue.DeclareOk ok = mChannel.queueDeclarePassive(HOST_QUEUE_URI);
            System.out.println("NO!");
        }
        catch (Exception e)
        {
            System.out.println("YES!");

        }
    }

    private void initConnection()
    {
        // Initiate connection.
        ConnectionFactory factory = new ConnectionFactory();

        try
        {
            factory.setUri(AMPQ_URI);
            Connection connection = factory.newConnection();
            mChannel = connection.createChannel();
            //mChannel.exchangeDeclare(EXCHANGE_NODES,"fanout");
            //Queue for connection
            //mChannel.queueDeclare(QUEUE_NODES,false,false,false,null);
            //mChannel.queueBind(QUEUE_NODES,EXCHANGE_NODES,"");
            //mChannel.basicConsume(QUEUE_NODES,true,this::onReceiveNewNode,consumerTag -> {});
        }
        catch (TimeoutException | IOException e)
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //Connection to the game
    public void notifyNewNode(Action action)
    {
        try
        {
            mChannel.basicPublish(EXCHANGE_NODES,QUEUE_NODES,null, SerializationUtils.serialize(action));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void onReceiveNewNode(String consumertag, Delivery delivery)
    {
        Action receivedAction = SerializationUtils.deserialize(delivery.getBody());

        if (receivedAction instanceof eagea.nodeio.model.rabbitmq.action.Connection)
        {
            //Add new player and new zone for each node map and model
            mModel.getMap().add(((eagea.nodeio.model.rabbitmq.action.Connection) receivedAction).getZone());
            mModel.addPlayer(((eagea.nodeio.model.rabbitmq.action.Connection) receivedAction).getPlayer());
        }

        if (receivedAction instanceof Disconnection)
        {
            //Remove player from the model and modify the owner of the zone. (Add removePlayer() to the model)
            //mModel.removePlayer(((Disconnection) receivedAction).getPlayer());
            mModel.getMap().getZone(((Disconnection) receivedAction).getZone().getPositionInMap()).setOwner(((Disconnection) receivedAction).getNewOwner());
        }

        if (receivedAction instanceof Move)
        {
            //PlayerM player = mModel.getInPlayers(((Move) receivedAction).getPlayer());
            //player.setPos(((Move) receivedAction).getPosI(),((Move) receivedAction).getPosJ());
        }
    }
}