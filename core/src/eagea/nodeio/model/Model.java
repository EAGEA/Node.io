package eagea.nodeio.model;

import java.util.ArrayList;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.model.rabbitmq.Node;

/**
 * Handle all the logic of the game, and the rabbitMQ communications with other
 * players.
 */
public class Model
{
    // RabbitMQ.
    private Node mNode;
    // The map.
    private MapM mMap;
    // The player.
    private PlayerM mPlayer;
    // All the players.
    private ArrayList<PlayerM> mPlayers;

    public Model()
    {
        initNode();
        initMap();
        initPlayers();
    }

    /**
     * Initialize the player node.
     */
    private void initNode()
    {
        mNode = new Node(mMap);

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
            // TODO
        }
        else
        {
            mMap = new MapM();
            // !!!!TEST!!!!!
            for (int i = 0 ; i <= 30 ; i ++)
            {
                mMap.add(new ZoneM(null, mMap.getNbZones()));
            }
        }
        // In all the cases, add our zone to the map.
        ZoneM zone = new ZoneM(mPlayer, mMap.getNbZones());
        mMap.add(zone);
        // Notify RabbitMQ; zone added.
        // TODO
        // mNode.sendZone(zone)
    }

    /**
     * Request and load the players from previous user.
     * Also add the player's zone.
     */
    public void initPlayers()
    {
        // Check if already players playing
        // RabbitMQ stuff...
        if (false)
        {
            // If a players:
            // -> mPlayers = getPlayersFromRabbitMQ()
            // TODO
        }
        else
        {
            mPlayers = new ArrayList<>();
        }
        // In all the cases, add our player to the map with an Id
        mPlayer = new PlayerM("John", 0, 0, mMap);
        mPlayers.add(mPlayer);
        // Notify RabbitMQ; player added.
        // TODO
    }

    public MapM getMap()
    {
        return mMap;
    }

    public PlayerM getPlayer()
    {
        return mPlayer;
    }
}