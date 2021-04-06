package eagea.nodeio.model.rabbitmq.action;

import java.util.ArrayList;

import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Player wants to catch someone. Send this action to the host so that it can
 * confirm it, and send it to everyone if validated.
 */
public class Catch extends Action
{
    private static final long serialVersionUID = 2505852453794582472L;

    private final PlayerM mPlayer;
    private final ArrayList<PlayerM> mCaught;

    /**
     * Sent by the player to request.
     */
    public Catch(PlayerM player)
    {
        mPlayer = player;
        mCaught = null;
    }

    /**
     * Sent by the host in response.
     */
    public Catch(PlayerM player, ArrayList<PlayerM> caught)
    {
        mPlayer = player;
        mCaught = caught;
    }

    public PlayerM getPlayer() { return mPlayer; }

    public ArrayList<PlayerM> getCaught()
    {
        return mCaught;
    }
}