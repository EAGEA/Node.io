package eagea.nodeio.model;

import eagea.nodeio.GameScreen;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.model.logic.player.PlayersM;
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
    private PlayersM mPlayers;

    public Model(GameScreen screen)
    {
        mGameScreen = screen;
        mNode = new Node(this);

        askForConnection();
    }

    /**
     * Action.
     * Connect player to the host.
     */
    private void askForConnection()
    {
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
            mPlayers = new PlayersM();
            mPlayers.add(mPlayer);
            // - Create player's zone.
            ZoneM zone = new ZoneM(mPlayer,
                    Type.values()[(int) (Math.random() * Type.values().length)], 0);
            mMap.add(zone);
            // Start rendering.
            mGameScreen.onModelIsReady(this);
        }
    }

    /**
     * Action.
     * Ask the host for moving player.
     */
    public void askForMove(PlayerM.Event orientation)
    {
        // Request for move.
        mNode.notifyHost(new Move(mPlayer, orientation));
    }

    /**
     * Action.
     * Disconnect the player from the host.
     */
    public void askForDeconnection()
    {
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
                // Set map and players.
                mMap = ((Connection) action).getMap();
                mPlayers = ((Connection) action).getPlayers();
                mPlayer = mPlayers.get(mPlayers.getNbPlayers() - 1);
                // Start rendering.
                mGameScreen.onModelIsReady(this);
            }
            else
            {
                // Update map and players.
                MapM map = ((Connection) action).getMap();
                PlayersM players = ((Connection) action).getPlayers();

                mMap.add(map.get(map.getNbZones() - 1));
                mPlayers.add(players.get(players.getNbPlayers() - 1));
                mMap.notify(MapM.Event.ADD);
            }
        }
        else if (action instanceof Disconnection)
        {

        }
        else if (action instanceof Move)
        {
            final PlayerM[] p_ = {((Move) action).getPlayer()};
            final PlayerM[] player = new PlayerM[1];
            // Find the corresponding player (reference).
            mPlayers.getPlayers().forEach(p ->
            {
                if (p.getI() == p_[0].getI()
                        && p.getJ() == p_[0].getJ()
                        && p.getZone() == p_[0].getZone())
                {
                    player[0] = p;
                }
            });


            if (player[0] == null)
            {
                System.err.println("[ERROR]: can't play action");
            }

            switch (((Move) action).getOrientation())
            {
                case LEFT: player[0].moveLeft(); break;
                case RIGHT: player[0].moveRight(); break;
                case UP: player[0].moveUp(); break;
                case DOWN: player[0].moveDown(); break;
            }
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

    public PlayersM getPlayers()
    {
        return mPlayers;
    }
}