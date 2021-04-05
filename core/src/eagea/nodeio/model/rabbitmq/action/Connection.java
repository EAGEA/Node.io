package eagea.nodeio.model.rabbitmq.action;

import java.util.ArrayList;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Player is connecting. Send this action to the host so that it
 * can notify everyone and send to everyone the updated map and players.
 */
public class Connection extends Action
{
    private MapM mMap;
    private ArrayList<PlayerM> mPlayers;

    /**
     * Empty constructor send to host to request her/him the map and players.
     */
    public Connection() { }

    /**
     * Sent by the host to update map and players of everyone.
     */
    public Connection(MapM map, ArrayList<PlayerM> players)
    {
        mMap = map;
        mPlayers = players;
    }

    public MapM getMap()
    {
        return mMap;
    }

    public ArrayList<PlayerM> getPlayers()
    {
        return mPlayers;
    }
}