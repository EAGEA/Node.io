package eagea.nodeio.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import eagea.nodeio.GameScreen;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.model.logic.player.PlayersM;
import eagea.nodeio.model.rabbitmq.Node;
import eagea.nodeio.model.rabbitmq.action.Action;
import eagea.nodeio.model.rabbitmq.action.Catch;
import eagea.nodeio.model.rabbitmq.action.Connection;
import eagea.nodeio.model.rabbitmq.action.Disconnection;
import eagea.nodeio.model.rabbitmq.action.Speak;
import eagea.nodeio.model.rabbitmq.action.Move;

/**
 * Handle all the logic of the game, and the rabbitMQ communications with other
 * players.
 */
public class Model
{
    // Player ID (i.e. its color, and its zone type).
    public enum Type { BLACK, GRASS, GRAVEL, ROCK, SAND, SNOW }
    // Current game state.
    public enum State { MENU, GAME, CAUGHT }

    // Context.
    private final GameScreen mGameScreen;

    // RabbitMQ.
    private final Node mNode;
    // The map.
    private MapM mMap;
    // The player.
    private PlayerM mPlayer;
    // All the players.
    private PlayersM mPlayers;
    // State.
    private State mState;

    public Model(GameScreen screen)
    {
        mGameScreen = screen;
        mNode = new Node(this);
        mState = State.MENU;
    }

    /**
     * Action.
     * Connect player to the host.
     */
    private void askForConnection()
    {
        if (! mNode.isHost())
        {
            mPlayer = null;
            mPlayers = null;
            mMap = null;
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
            mGameScreen.onStartGame();
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
     * Ask the host for speaking.
     */
    public void askForSpeak(PlayerM.Speak sentence)
    {
        // Request for move.
        mNode.notifyHost(new Speak(mPlayer, sentence));
    }

    /**
     * Action.
     * Ask the host for catching someone.
     */
    public void askForCatch()
    {
        // Request for catch.
        mNode.notifyHost(new Catch(mPlayer));
    }

    /**
     * Action.
     * Disconnect the player from the host.
     */
    public void askForDisconnection()
    {
        // Request for disconnection.
        mNode.notifyHost(new Disconnection(mPlayer));
    }

    /**
     * Process and play action received from host.
     */
    public void play(Action action)
    {
        System.out.println("[DEBUG]: play " + action.getClass().getSimpleName());

        if (action instanceof Connection)
        {
            playConnection((Connection) action);
        }
        else if (action instanceof Move)
        {
            playMove((Move) action);
        }
        else if (action instanceof Speak)
        {
            playSpeak((Speak) action);
        }
        else if (action instanceof Catch)
        {
            playCatch((Catch) action);
        }
        else if (action instanceof Disconnection)
        {
            playDisconnection((Disconnection) action);
        }
    }

    private void playConnection(Connection action)
    {
        boolean iamTheNewGuy = (mPlayer == null);

        if (iamTheNewGuy)
        {
            // Set map and players.
            mMap = action.getMap();
            mPlayers = action.getPlayers();
            System.out.println(mPlayers.getNbPlayers());
            System.out.println(action.getPlayers().getNbPlayers());
            mPlayer = mPlayers.get(mPlayers.getNbPlayers() - 1);
            // Start rendering.
            mGameScreen.onStartGame();
            // Auto-disconnection.
            Runtime.getRuntime().addShutdownHook(new Thread(
                    () ->
                    {
                        if (mState == State.GAME)
                        {
                            askForDisconnection();
                        }
                    }
                )
            );
        }
        else
        {
            if (! mNode.isHost())
            {
                // Update map and players.
                MapM map = action.getMap();
                PlayersM players = action.getPlayers();

                mMap.add(map.get(map.getNbZones() - 1));
                mPlayers.addWithoutNotify(players.get(players.getNbPlayers() - 1));
                mMap.notify(MapM.Event.ADD);
            }
        }
    }

    private void playMove(Move action)
    {
        // Find the corresponding player (reference).
        PlayerM player = mPlayers.find(action.getPlayer());
        // Check if found.
        if (player == null)
        {
            System.err.println("[ERROR]: can't play action");
            return;
        }
        // Move it.
        switch (action.getOrientation())
        {
            case LEFT: player.moveLeft(); break;
            case RIGHT: player.moveRight(); break;
            case UP: player.moveUp(); break;
            case DOWN: player.moveDown(); break;
        }
    }

    private void playSpeak(Speak action)
    {
        // Find the corresponding player (reference).
        PlayerM player = mPlayers.find(action.getPlayer());
        // Check if found.
        if (player == null)
        {
            System.err.println("[ERROR]: can't play action");
            return;
        }
        // Make her/him speak.
        player.speak(action.getSentence());
    }

    private void playCatch(Catch action)
    {
        // Find the corresponding player (reference).
        PlayerM player = mPlayers.find(action.getPlayer());
        // Check if found.
        if (player == null)
        {
            System.err.println("[ERROR]: can't play action");
            return;
        }
        // Remove each player from the game.
        action.getCaught().forEach(p ->
                {
                    // Change zones owner.
                    mMap.getZones().forEach(z ->
                            {
                                if (z.getOwner().equals(p))
                                {
                                    z.setOwner(player);
                                }
                            }
                    );
                    // Remove players from list.
                    mPlayers.remove(p);
                }
        );
        // If I'm caught.
        if (action.getCaught().contains(mPlayer))
        {
            mState = State.CAUGHT;
        }
    }

    private void playDisconnection(Disconnection action)
    {
        // Get the new zone owner.
        PlayerM nOwner = mPlayers.find(action.getNewOwner());
        // Set to the corresponding zones.
        action.getIndexes().forEach(i ->
            {
                mMap.getZones().get(i).setOwner(nOwner);
            }
        );
        // And remove the disconnected user.
        mPlayers.remove(action.getPlayer());
        // If I'm the disconnected guy.
        if (mPlayer.equals(action.getPlayer()))
        {
            goToMenu();
        }
    }

    /**
     * Host only.
     * Check if the action can be done.
     * If so, return the corresponding one, otherwise null.
     */
    public Action check(Action action)
    {
        System.out.println("[DEBUG]: HOST check " + action.getClass().getSimpleName());

        if (action instanceof Connection)
        {
            return checkConnection((Connection) action);
        }
        else if (action instanceof Move)
        {
            return checkMove((Move) action);
        }
        else if (action instanceof Speak)
        {
            return checkSpeak((Speak) action);
        }
        else if (action instanceof Catch)
        {
            return checkCatch((Catch) action);
        }
        else if (action instanceof Disconnection)
        {
            return checkDisconnection((Disconnection) action);
        }

        return null;
    }

    private Action checkConnection(Connection action)
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

    private Action checkMove(Move action)
    {
        final Action[] result = { action };
        PlayerM player = action.getPlayer();
        // Convert player position in the whole map ones.
        Vector2 position = player.getMapPosition();
        // Get the cell in which the player would like to go.
        switch (action.getOrientation())
        {
            case LEFT: position.y ++; break;
            case RIGHT: position.y --; break;
            case UP: position.x ++; break;
            case DOWN: position.x --; break;
        }
        // Check if a player is already in this cell.
        mPlayers.getPlayers().forEach(p ->
                {
                    Vector2 pPosition = p.getMapPosition();

                    if (pPosition.x == position.x
                            && pPosition.y == position.y)
                    {
                        // Can't do this move.
                        result[0] = null;
                    }
                }
        );
        // Send it if not null.
        return result[0];
    }

    private Action checkSpeak(Speak action)
    {
        // TODO check if connected.
        // Send it.
        return action;
    }

    private Action checkCatch(Catch action)
    {
        PlayerM player = action.getPlayer();
        ArrayList<PlayerM> caught = new ArrayList<>();
        // Convert player position in the whole map ones.
        Vector2 position = player.getMapPosition();
        // Check if a player is adjacent to player cell.
        mPlayers.getPlayers().forEach(p ->
                {
                    Vector2 pPosition = p.getMapPosition();

                    if (pPosition.y == position.y)
                    {
                        if (Math.abs(pPosition.x - position.x) == 1)
                        {
                            caught.add(p);
                        }
                    }
                    else if (pPosition.x == position.x)
                    {
                        if (Math.abs(pPosition.y - position.y) == 1)
                        {
                            caught.add(p);
                        }
                    }
                }
        );
        // Send it.
        return caught.isEmpty() ? null : new Catch(player, caught);
    }

    private Action checkDisconnection(Disconnection action)
    {
        PlayerM player = action.getPlayer();
        // Create the a list to pick a random player to be the owner of
        // the disconnected player's zone.
        ArrayList<PlayerM> toPick = new ArrayList<>(mPlayers.getPlayers());
        toPick.remove(player);
        // Pick at random index.
        PlayerM newOwner = toPick.get((int) (Math.random() * toPick.size()));
        // Get the corresponding indexes of the zones.
        ArrayList<ZoneM> zones = mMap.getZones();
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < zones.size(); i ++)
        {
            if (zones.get(i).getOwner().equals(player))
            {
                indexes.add(i);
            }
        }
        // Send it.
        return new Disconnection(player, newOwner, indexes);
    }

    public void goToGame()
    {
        mNode.openConnection();
        mNode.checkIfHost();
        mNode.declareQueue();
        askForConnection();
    }

    public void goToMenu()
    {
        mState = State.MENU;
    }

    public void setState(State state)
    {
        mState = state;
    }

    public State getState()
    {
        return mState;
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