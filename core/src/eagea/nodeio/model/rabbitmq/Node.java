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
    // RabbitMQ exchange and queues.
    private final String EXCHANGE_URI = "rabbitmq://all/exchange";
    private final String HOST_QUEUE_URI = "rabbitmq://host/queue";
    private final String QUEUE_URI = "rabbitmq://all/queue";

    // RabbitMQ connection.
    private Connection mConnection;
    private Channel mChannel;
    private final Model mModel;
    //
    private boolean mIsHost;

//    private static final String EXCHANGE_NODES = "exnodes";
  //  private static final String QUEUE_NODES = "qnodes";

    public Node(Model model)
    {
        mModel = model;

        openConnection();
        declareQueue();
        checkIfHost();
    }

    /**
     * Start RabbitMQ connection.
     */
    private void openConnection()
    {
        ConnectionFactory factory = new ConnectionFactory();

        try
        {
            factory.setUri(AMPQ_URI);
            mConnection = factory.newConnection();
            openChannel();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void openChannel() throws IOException
    {
        mChannel = mConnection.createChannel();
    }

    /**
     * Declare the queue which receives players actions.
     */
    private void declareQueue()
    {
        try
        {
            mChannel.exchangeDeclare(EXCHANGE_URI, "fanout");
            mChannel.queueDeclare(QUEUE_URI,
                    false, false, false,
                    null);
            mChannel.queueBind(EXCHANGE_URI, QUEUE_URI,"");
            mChannel.basicConsume(QUEUE_URI, true,
                    this::onReceive,
                    consumerTag -> {});
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
            mIsHost = false;
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
                        false, false, false,
                        null);
                mChannel.basicConsume(HOST_QUEUE_URI, true,
                        this::onHostReceive,
                        consumerTag -> { });
                // She/he is the host!
                mIsHost = true;
                addShutDownHook();
            }
            catch (Exception e_)
            {
                e_.printStackTrace();
            }
        }
    }

    /**
     * Only for host.
     * End the game when host disconnects. Remove rabbitMQ host data.
     */
    private void addShutDownHook()
    {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() ->
                {
                    if (mIsHost)
                    {
                        try
                        {
                            mChannel.queueDelete(HOST_QUEUE_URI);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            )
        );
    }

    /**
     * Send an action to the host. It will confirm or decline it.
     * If this node is host, also push the action in the queue so that
     * the host has no priority.
     */
    public void notifyHost(Action action)
    {
        try
        {
            mChannel.basicPublish("", HOST_QUEUE_URI,
                    null,
                    SerializationUtils.serialize(action));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Host process Action received.
     */
    private void onHostReceive(String consumerTag, Delivery delivery) throws IOException
    {
        Action action = SerializationUtils.deserialize(delivery.getBody());
        action = mModel.check(action);

        if (action != null)
        {
            // Action validated by host.
            // Send it to all the players.
            mChannel.basicPublish(EXCHANGE_URI, "", null,
                    SerializationUtils.serialize(action));
        }
    }

    /**
     * Player play the action received.
     */
    public void onReceive(String consumerTag, Delivery delivery)
    {
        Action action = SerializationUtils.deserialize(delivery.getBody());
        mModel.play(action);
    }

    /*

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
     */
}