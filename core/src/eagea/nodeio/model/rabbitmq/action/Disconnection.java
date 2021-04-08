package eagea.nodeio.model.rabbitmq.action;

import java.util.ArrayList;

import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Player is disconnecting. Send this action to the host so that it can
 * notify everyone, and give our zone to a random player.
 */
public class Disconnection extends Action
{
    private static final long serialVersionUID = 2116895505000334818L;

    private String mNewOwner;
    private ArrayList<Integer> mIndexes;
    private Boolean mIsHost;

    /**
     * Disconnection from a node to the host for check.
     */
    public Disconnection(String ID,Boolean host)
    {
        super(ID);
        mIsHost = host;
    }

    /**
     * Disconnection from the host to the nodes.
     */
    public Disconnection(String ID, String newOwner, ArrayList<Integer> indexes)
    {
        super(ID);
        mNewOwner = newOwner;
        mIndexes = indexes;
    }

    public String getNewOwner() { return mNewOwner; }

    public ArrayList<Integer> getIndexes() { return mIndexes; }
}
