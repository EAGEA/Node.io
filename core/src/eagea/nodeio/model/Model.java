package eagea.nodeio.model;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import eagea.nodeio.GameScreen;
import eagea.nodeio.model.logic.map.CellM;
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
    // Current game state.
    public enum State { MENU, STARTING, GAME, CAUGHT }

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
     * Connect player to the host, and create model.
     */
    private void askForConnection()
    {
        if (! mNode.isHost())
        {
            // Not the host; request for game model.
            mNode.notifyHost(new Connection(mNode.getID()));
        }
        else
        {
            // The host initiates game model (the one who create it):
            // - Create Map.
            mMap = new MapM();
            // - Create player's zone.
            ZoneM zone = new ZoneM(mNode.getID(),
                    ZoneM.Type.values()[(int) (Math.random() * ZoneM.Type.values().length)], 0);
            mMap.add(zone);
            // - Create player.
            Vector2 appear = findCellToAppear(zone);
            mPlayer = new PlayerM(mNode.getID(), (int) appear.x, (int) appear.y, 0, mMap);
            mPlayers = new PlayersM();
            mPlayers.add(mPlayer);
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
        mNode.notifyHost(new Move(mNode.getID(), orientation));
    }

    /**
     * Action.
     * Ask the host for speaking.
     */
    public void askForSpeak(PlayerM.Speak sentence)
    {
        // Request for move.
        mNode.notifyHost(new Speak(mNode.getID(), sentence));
    }

    /**
     * Action.
     * Ask the host for catching someone.
     */
    public void askForCatch()
    {
        // Request for catch.
        mNode.notifyHost(new Catch(mNode.getID()));
    }

    /**
     * Action.
     * Disconnect the player from the host.
     */
    public void askForDisconnection()
    {
        // Request for disconnection.
        mNode.notifyHost(new Disconnection(mNode.getID()));
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
        if (mState == State.CAUGHT)
        {
            return;
        }

        // Check if we are the new player.
        if (action.getPlayer().equals(mNode.getID()))
        {
            // Set map and players.
            mMap = action.getMap();
            mPlayers = action.getPlayers();
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
            // Already updated for the host (she/he is the one who sent the model).
            if (! mNode.isHost())
            {
                // Update map and players.
                MapM map = action.getMap();
                PlayersM players = action.getPlayers();

                mMap.add(map.get(map.getNbZones() - 1));
                mPlayers.add(players.get(players.getNbPlayers() - 1));
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
        // Remove each player from the game.
        action.getCaught().forEach(p ->
                {
                    // Change zones owner.
                    mMap.getZones().forEach(z ->
                            {
                                if (z.getOwner().equals(p))
                                {
                                    z.setOwner(action.getPlayer());
                                }
                            }
                    );
                    // Remove players from list.
                    mPlayers.remove(mPlayers.find(p));
                }
        );
        // If I'm caught.
        if (action.getCaught().contains(mPlayer.getID()))
        {
            mState = State.CAUGHT;
        }
    }

    private void playDisconnection(Disconnection action)
    {
        // If I'm the disconnected guy.
        if (mPlayer.getID().equals(action.getPlayer()))
        {
            goToMenu();
            return;
        }
        // If I'm the new host.
        if (mPlayer.getID().equals(action.getNewHost()))
        {
            System.out.println("[DEBUG]: I'm the new HOST");
            mNode.becomeHost();
        }
        // Set to the corresponding zones.
        action.getIndexes().forEach(i -> mMap.getZones().get(i)
                .setOwner(action.getNewOwner()));
        // And remove the disconnected user.
        mPlayers.remove(mPlayers.find(action.getPlayer()));
    }

    /**
     * Host only.
     * Check if the action can be done.
     * If so, return the corresponding one, otherwise null.
     */
    public Action check(Action action)
    {
        if (mState != State.GAME)
        {
            return null;
        }

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

        // Add new zone.
        ZoneM zone = new ZoneM(action.getPlayer(),
                ZoneM.Type.values()[(int) (Math.random() * ZoneM.Type.values().length)],
                mMap.getNbZones());
        // Get a cell on which the player can appear.
        Vector2 appear = findCellToAppear(zone);
        // Add new player on this cell.
        PlayerM player = new PlayerM(action.getPlayer(),
                (int) appear.x, (int) appear.y,
                mMap.getNbZones(), mMap);
        // Update the model.
        mPlayers.add(player);
        mMap.add(zone);
        // Send model.
        return new Connection(action.getPlayer(), mMap, mPlayers);
    }

    private Action checkMove(Move action)
    {
        final Action[] result = { action };
        PlayerM player = mPlayers.find(action.getPlayer());
        // Convert player position in the whole map ones.
        Vector2 position = player.getMapPosition();
        // Get the cel in which the player would like to go.
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
        // Play it for the host.
        if (result[0] != null)
        {
            playMove((Move) result[0]);
        }
        // Send it.
        return result[0];
    }

    private Action checkSpeak(Speak action)
    {
        // Play it for the host.
        playSpeak(action);
        // And send it.
        return action;
    }

    private Action checkCatch(Catch action)
    {
        ArrayList<String> caught = new ArrayList<>();
        PlayerM player = mPlayers.find(action.getPlayer());
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
                            caught.add(p.getID());
                        }
                    }
                    else if (pPosition.x == position.x)
                    {
                        if (Math.abs(pPosition.y - position.y) == 1)
                        {
                            caught.add(p.getID());
                        }
                    }
                }
        );

        if (caught.isEmpty())
        {
            // Send nothing.
            return null;
        }
        else
        {
            Catch catch_ = new Catch(action.getPlayer(), caught);
            // Play it for the host.
            playCatch(catch_);
            // And send it.
            return catch_;
        }
    }

    private Action checkDisconnection(Disconnection action)
    {
        PlayerM player = mPlayers.find(action.getPlayer());

        if (player == null)
        {
            // Caught before being able to disconnect.
            return null;
        }

        // Create the a list to pick a random player to be the owner of
        // the disconnected player's zone.
        ArrayList<PlayerM> toPick = new ArrayList<>(mPlayers.getPlayers());
        toPick.remove(player);
        PlayerM newOwner = null;
        ArrayList<Integer> indexes = new ArrayList<>();

        if (! toPick.isEmpty())
        {
            // Pick new owner at random index.
            newOwner = toPick.get((int) (Math.random() * toPick.size()));
            // Get the corresponding indexes of the zones.
            ArrayList<ZoneM> zones = mMap.getZones();

            for (int i = 0; i < zones.size(); i ++)
            {
                if (zones.get(i).getOwner().equals(player.getID()))
                {
                    indexes.add(i);
                }
            }
        }

        if (newOwner != null)
        {
            if (player.equals(mPlayer))
            {
                // If the disconnection received is from the host (host->host).
                // Node elicitation: new host (let's say newOwner).
                // Send special disconnection action to notify the new host.
                action = new Disconnection(action.getPlayer(),
                        newOwner.getID(), indexes, newOwner.getID());
                mNode.looseHost(false);
                // Play it for the host.
                playDisconnection(action);
            }
            else
            {
                // If the disconnection is not from the host.
                action = new Disconnection(action.getPlayer(),
                        newOwner.getID(), indexes);
                // Play it for the host.
                playDisconnection(action);
            }
            // And send it.
            return action;
        }

        // Play it for the host.
        playDisconnection(action);

        if (player.equals(mPlayer))
        {
            // If the disconnection received is from the host (host->host).
            // Loose host state, and destroy the host queue because last person in game.
            mNode.looseHost(true);
        }

        return null;
    }

    /**
     * Find a cell on which there is no environment obstacle, to make player
     * appear on it.
     */
    private Vector2 findCellToAppear(ZoneM zone)
    {
        ArrayList<Vector2> cells = new ArrayList<>();

        for (int i = 0; i < ZoneM.SIZE; i ++)
        {
            for (int j = 0; j < ZoneM.SIZE; j ++)
            {
                if (zone.getCells()[i][j].getType() == CellM.Type.EMPTY)
                {
                    cells.add(new Vector2(i, j));
                }
            }
        }

        return cells.get((int) (Math.random() * cells.size()));
    }

    public void goToGame()
    {
        mState = State.STARTING;
        mNode.create();
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
