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

    private boolean mContainHost;
    private final ArrayList<String> mCaught;

    /**
     * Sent by the player to request.
     */
    public Catch(String ID)
    {
        super(ID);
        mCaught = null;
    }

    /**
     * Sent by the host in response.
     */
    public Catch(String ID, ArrayList<String> IDs)
    {
        super(ID);
        mCaught = IDs;
    }

    public ArrayList<String> getCaught()
    {
        return mCaught;
    }
}