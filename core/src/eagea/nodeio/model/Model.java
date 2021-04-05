package eagea.nodeio.model;

import java.util.ArrayList;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.model.rabbitmq.Node;
import eagea.nodeio.model.rabbitmq.action.Action;
import eagea.nodeio.model.rabbitmq.action.Connection;
import eagea.nodeio.model.rabbitmq.action.Disconnection;
import eagea.nodeio.model.rabbitmq.action.Move;

/**
 * Handle all the logic of the game, and the rabbitMQ communications with other
 * players.
 */
public class Model
{
    // Player ID (i.e. its color, and its zone type).
    public enum Type { BLACK, GRASS, GRAVEL, ROCK, SAND, SNOW }

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
        initPlayers();
        initMap();

        // Notify RabbitMQ new player.
        //mNode.notifyNewNode(mPlayer, mMap.get(mMap.size() - 1)));
    }

    /**
     * Initialize the player node.
     */
    private void initNode()
    {
        mNode = new Node(this);

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
        }
        // In all the cases, add our zone to the map.
        ZoneM zone = new ZoneM(mPlayer,
                Type.values()[(int) (Math.random() * Type.values().length)], mMap.getNbZones());
        mMap.add(zone);
        // Set player's attributes.
        mPlayer.setZone(zone.getPositionInMap());
        mPlayer.setMap(mMap);



        // !!!!TEST!!!!!
        for (int i = 0 ; i <= 30 ; i ++)
        {
            zone = new ZoneM(null,
                    Type.values()[(int) (Math.random() * Type.values().length)], mMap.getNbZones());
            mMap.add(zone);
        }
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
        // In all the cases, add our player to the map.
        mPlayer = new PlayerM(0, 0);
        mPlayers.add(mPlayer);
    }

    /**
     * Process and play action received from host.
     */
    public void play(Action action)
    {
        if (action instanceof Connection)
        {

        }
        else if (action instanceof Disconnection)
        {

        }
        else if (action instanceof Move)
        {

        }
    }

    /**
     * Host only.
     * Check if the action can be done.
     * If so, return the corresponding one, otherwise null.
     */
    public Action check(Action action)
    {
        if (action instanceof Connection)
        {
            return action;
        }
        else if (action instanceof Disconnection)
        {
            return action;
        }
        else if (action instanceof Move)
        {
            return action;
        }

        return null;
    }

    public void addPlayer(PlayerM player)
    {
        mPlayers.add(player);
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