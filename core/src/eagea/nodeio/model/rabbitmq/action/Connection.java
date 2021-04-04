package eagea.nodeio.model.rabbitmq.action;

import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Player is connecting. Send this action to the host so that it
 * can notify everyone and send us the current map and the players.
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
