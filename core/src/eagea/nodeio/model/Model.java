package eagea.nodeio.model;

import java.util.ArrayList;

import eagea.nodeio.GameScreen;
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

    // Context.
    private GameScreen mGameScreen;

    // RabbitMQ.
    private Node mNode;
    // The map.
    private MapM mMap;
    // The player.
    private PlayerM mPlayer;
    // All the players.
    private ArrayList<PlayerM> mPlayers;

    public Model(GameScreen screen)
    {
        mGameScreen = screen;

        initNode();
    }

    /**
     * Initialize the player node.
     */
    private void initNode()
    {
        mNode = new Node(this);

        if (! mNode.isHost())
        {
            // Not the host; request for game model.
            mNode.notifyHost(new Connection());
        }
        else
        {
            // The host initiates game model:
            // - Create Map.
            mMap = new MapM();
            // - Create player.
            mPlayer = new PlayerM(0, 0, 0, mMap);
            mPlayers = new ArrayList<>();
            mPlayers.add(mPlayer);
            // - Create player's zone.
            ZoneM zone = new ZoneM(mPlayer,
                    Type.values()[(int) (Math.random() * Type.values().length)], 0);
            mMap.add(zone);

            // TODO REMOVE
            mGameScreen.createView(this);
            mGameScreen.createController(this);
        }
    }

    /**
     * Process and play action received from host.
     */
    public void play(Action action)
    {
        System.out.println("[DEBUG]: play " + action.getClass().getSimpleName());

        if (action instanceof Connection)
        {
            boolean iamTheNewGuy = (mPlayer == null);

            if (iamTheNewGuy)
            {
                // Get whole map and player list from host.
                mMap = ((Connection) action).getMap();
                mPlayers = ((Connection) action).getPlayers();
                mPlayer = mPlayers.get(mPlayers.size() - 1);

                // TODO REMOVE
                mGameScreen.createView(this);
                mGameScreen.createController(this);

                // Place the character on the current cell i.e. (0, 0) of its zone.
                for (int i = 0; i < ZoneM.SIZE * (mPlayer.getZone() % MapM.ZONE_LINE); i ++)
                {
                    mPlayer.notify(PlayerM.Event.LEFT);
                }

                for (int i = 0; i < ZoneM.SIZE * (mPlayer.getZone() / MapM.ZONE_LINE); i ++)
                {
                    mPlayer.notify(PlayerM.Event.UP);
                }
            }
            else
            {
                // Get new map and updated player list from host.
                MapM map = ((Connection) action).getMap();

                mMap.add(map.getZone(map.getNbZones() - 1));
                mPlayers = ((Connection) action).getPlayers();
                mMap.notify(MapM.Event.ADD);
            }
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
            // Add new player and zone.
            PlayerM player = new PlayerM(0, 0, mMap.getNbZones(), mMap);
            ZoneM zone = new ZoneM(player,
                    Type.values()[(int) (Math.random() * Type.values().length)],
                    mMap.getNbZones());
            mPlayers.add(player);
            mMap.add(zone);
            // Send it.
            return new Connection(mMap, mPlayers);
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