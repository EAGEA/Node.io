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

    private String mNewOwner, mNewHost;
    private ArrayList<Integer> mIndexes;

    /**
     * Disconnection from a node to the host for check.
     */
    public Disconnection(String ID)
    {
        super(ID);
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

    /**
     * Disconnection from the host to the nodes : disconnection of the host
     */
    public Disconnection(String ID, String newHost, String newOwner, ArrayList<Integer> indexes)
    {
        super(ID);
        mNewHost = newHost;
        mNewOwner = newOwner;
        mIndexes = indexes;
    }

    public String getNewOwner() { return mNewOwner; }

    public String getNewHost() { return mNewHost; }

    public ArrayList<Integer> getIndexes() { return mIndexes; }
}
