package eagea.nodeio.model.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Handle all the RabbitMQ communications with other players.
 */
public class Node
{
    private Channel mChannel;

    public Node()
    {
        // Initiate connection.
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try
        {
            Connection connection = factory.newConnection();
            mChannel = connection.createChannel();
            // Stuff.....
        }
        catch (TimeoutException | IOException e)
        {
            e.printStackTrace();
        }
    }

}
