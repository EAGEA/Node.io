package eagea.nodeio.model;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.rabbitmq.Node;

/**
 * Handle all the logic of the game, and the rabbitMQ communications with other
 * players.
 */
public class Model
{
    // RabbitMQ:
    private Node mNode;
    // The map.
    private MapM mMap;

    public Model()
    {
        initNode();
        initMap();
    }

    /**
     * Initialize the player node.
     */
    private void initNode()
    {
        //Creation of the connexion
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            mNode = new Node(channel);
            // Stuff.....
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
        /**
     * Request and load the map from previous user, or create the map.
     * Also add the player's zone.
     */
    private void initMap()
    {
        // Check if a map is already existing among players.
        // RabbitMQ stuff...
        if (false)
        {
            // If a map already exists.
            // -> mMap = getMapFromRabbitMQUser()
        }
        else
        {
            mMap = new MapM();
        }
        // In all the cases, add our zone to the map with an Id
        mMap.add(new ZoneM("Albert", mMap.getNbZone()));
        mMap.add(new ZoneM("John", mMap.getNbZone()));
    }

    public MapM getMap()
    {
        return mMap;
    }
}