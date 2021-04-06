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

    private PlayerM mPlayer, mNewOwner;
    private ArrayList<Integer> mIndexes;

    /**
     * Disconnection from a node to the host for check**
     */
    public Disconnection(PlayerM player)
    {
        mPlayer = player;
    }

    /**
     * Disconnection from the host to the nodes**
     */
    public Disconnection(PlayerM newOwner, ArrayList<Integer> indexes)
    {
        mNewOwner = newOwner;
        mIndexes = indexes;
    }

    public PlayerM getPlayer() { return mPlayer; }

    public PlayerM getNewOwner() { return mNewOwner; }

    public ArrayList<Integer> getIndexes() { return mIndexes; }
}
