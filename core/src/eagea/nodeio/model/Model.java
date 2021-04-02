package eagea.nodeio.model;

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
        mNode = new Node();
        // Stuff.....
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
        // In all the cases, add our zone to the map.
        mMap.add(new ZoneM("Albert"));
    }

    public MapM getMap()
    {
        return mMap;
    }
}