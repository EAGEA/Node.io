package eagea.nodeio.model.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import eagea.nodeio.model.logic.map.MapM;

/**
 * Handle all the RabbitMQ communications with other players.
 */
public class Node
{
    private Channel mChannel;
    private MapM mMap;

    private static final String EXCHANGE_NODES = "nodes";
    private static final String QUEUE_CONNECT = "connect";

    public Node(MapM map)
    {
        // Initiate connection.
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try
        {
            Connection connection = factory.newConnection();
            mChannel = connection.createChannel();
            mChannel.exchangeDeclare(EXCHANGE_NODES,"fanout");
            //Queue for connection
            mChannel.queueDeclare(QUEUE_CONNECT,false,false,false,null);
            mChannel.queueBind(QUEUE_CONNECT,EXCHANGE_NODES,"");

            mMap = map;
        }
        catch (TimeoutException | IOException e)
        {
            e.printStackTrace();
        }
    }

    //Connection to the game
    public void Connect()
    {

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
