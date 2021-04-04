package eagea.nodeio.model.rabbitmq.action;

import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Player is disconnecting. Send this action to the host so that it can
 * notify everyone, and give our zone to a random player.
 */
public class Disconnection extends Action
{
    private PlayerM mPlayer,mNewOwner;
    private ZoneM mZone;

    //Connection of a new node
    public Disconnection(PlayerM player, ZoneM zone, PlayerM newOwner)
    {
        mPlayer = player;
        mZone = zone;
        mNewOwner = newOwner;
    }

    public PlayerM getPlayer() { return mPlayer; }

    public ZoneM getZone() { return mZone; }

    public PlayerM getNewOwner() { return mNewOwner; }
}
