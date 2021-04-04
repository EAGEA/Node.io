package eagea.nodeio.model.rabbitmq.action;

import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Player is connecting.
 */
public class Connection extends Action
{
    private PlayerM mPlayer;
    private ZoneM mZone;

    //Connection of a new node
    public Connection(PlayerM player, ZoneM zone)
    {
        mPlayer = player;
        mZone = zone;
    }

    public PlayerM getPlayer() { return mPlayer; }

    public ZoneM getZone() { return mZone; }
}
