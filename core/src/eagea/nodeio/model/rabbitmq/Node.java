package eagea.nodeio.model.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

import org.apache.commons.lang3.SerializationUtils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

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
    // RabbitMQ exchange and queues.
    private final String EXCHANGE_URI = "amq.fanout"; // Default one (no need to declare).
    private final String HOST_QUEUE_URI = "rabbitmq://host/queue";

    // RabbitMQ connection.
    private Connection mConnection;
    private Channel mChannel;
    private final Model mModel;
    private String mQueueName;
    private boolean mIsHost;
    private boolean mIsCreated;

    public Node(Model model)
    {
        mModel = model;
        mIsCreated = false;
    }

    /**
     * Create the rabbitMQ entity associated to this player.
     */
    public void create()
    {
        if (mIsCreated)
        {
            return;
        }

        openConnection();
        checkIfHost();

        mIsCreated = true;
    }

    /**
     * Start RabbitMQ connection.
     */
    private void openConnection()
    {
        System.out.println("[DEBUG]: connection");

        ConnectionFactory factory = new ConnectionFactory();

        try
        {
            factory.setUri(AMPQ_URI);
            mConnection = factory.newConnection();
            openChannel();
        }
        catch (Exception e)
        {
            System.err.println("[ERROR]: connection");
            System.exit(-1);
        }
    }

    private void openChannel() throws IOException
    {
        mChannel = mConnection.createChannel();
    }

    /**
     * Declare the queue which receives all players actions (only for non-host players).
     */
    private void declareQueue()
    {
        try
        {
            if (mChannel == null)
            {
                System.err.println("[ERROR]: channel queue declaration 1");
                System.exit(-1);
            }

            // Get a queue.
            mQueueName = mChannel.queueDeclare().getQueue();
            // Bind it.
            mChannel.queueBind(mQueueName, EXCHANGE_URI, "");
            // Handler.
            mChannel.basicConsume(mQueueName, true,
                    this::onReceive,
                    consumerTag -> { });
            System.out.println("[DEBUG]: queue created " + mQueueName);
        }
        catch (IOException e)
        {
            System.err.println("[ERROR]: channel queue declaration 2");
        }
    }

    /**
     * Check if the user is the first one to connect. If so, she/he is the host.
     */
    private void checkIfHost()
    {
        try
        {
            // If no exception, host queue already exists, so host too.
            AMQP.Queue.DeclareOk hostQueue = mChannel.queueDeclarePassive(HOST_QUEUE_URI);
            // Non-host players have a queue to receive actions.
            declareQueue();

            mIsHost = false;
            System.out.println("[DEBUG]: i'm not HOST");
        }
        catch (Exception e)
        {
            // This user will be the host:
            try
            {
                // Re-open the channel (closed with exception before).
                openChannel();
                // Declare the host queue.
                mChannel.queueDeclare(HOST_QUEUE_URI,
                        false, false, true,
                        null);
                mChannel.basicConsume(HOST_QUEUE_URI, true,
                        this::onHostReceive,
                        consumerTag -> { });
                // Just to get and ID:
                mQueueName = mChannel.queueDeclare().getQueue();
                mChannel.queueDelete(mQueueName);
                // She/he is the host!
                mIsHost = true;
                System.out.println("[DEBUG]: i'm HOST");
            }
            catch (Exception e_)
            {
                e_.printStackTrace();
            }
        }
    }

    /**
     * Send an action to the host. It will confirm or decline it.
     * If this node is host, also push the action in the queue so that
     * the host has no priority.
     */
    public void notifyHost(Action action)
    {
        System.out.println("[DEBUG]: send action");

        try
        {
            mChannel.basicPublish("", HOST_QUEUE_URI,
                    null,
                    SerializationUtils.serialize(action));
        }
        catch (Exception e)
        {
            System.err.println("[ERROR]: send action");
        }
    }

    /**
     * Host process Action received.
     */
    private void onHostReceive(String consumerTag, Delivery delivery)
    {
        System.out.println("[DEBUG]: HOST receive action");
        Action action = SerializationUtils.deserialize(delivery.getBody());
        action = mModel.check(action);

        if (action != null)
        {
            // Action validated by host.
            // Send it to all the players.
            System.out.println("[DEBUG]: HOST publish action");

            try
            {
                mChannel.basicPublish(EXCHANGE_URI, "",
                        null,
                        SerializationUtils.serialize(action));
            }
            catch (Exception e)
            {
                System.err.println("[ERROR]: HOST publish action");
            }
        }
    }

    /**
     * Player play the action received.
     */
    public void onReceive(String consumerTag, Delivery delivery)
    {
        System.out.println("[DEBUG]: receive action");

        try
        {
            Action action = SerializationUtils.deserialize(delivery.getBody());
            mModel.play(action);
        }
        catch (Exception e)
        {
            System.err.println("[ERROR]: receive action");
            e.printStackTrace();
        }
    }

    public boolean isHost()
    {
        return mIsHost;
    }

    public String getID()
    {
        return mQueueName;
    }
}