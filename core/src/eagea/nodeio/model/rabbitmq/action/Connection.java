package eagea.nodeio.model.rabbitmq.action;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.player.PlayersM;

/**
 * Player is connecting. Send this empty action to the host so that it
 * can notify everyone and send back this action to everyone,
 * with the updated map and players.
 */
public class Connection extends Action
{
    private static final long serialVersionUID = 8956958123191408048L;

    private MapM mMap;
    private PlayersM mPlayers;

    /**
     * Constructor to send host, to request her/him the map and players.
     */
    public Connection(String ID)
    {
        super(ID);
    }

    /**
     * Sent by the host to update map and players of everyone.
     */
    public Connection(String ID, MapM map, PlayersM players)
    {
        super(ID);
        mMap = map;
        mPlayers = players;
    }

    public MapM getMap()
    {
        return mMap;
    }

    public PlayersM getPlayers()
    {
        return mPlayers;
    }
}