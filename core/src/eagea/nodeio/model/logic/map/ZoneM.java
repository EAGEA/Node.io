package eagea.nodeio.model.logic.map;

import java.io.Serializable;
import java.util.Observable;

import eagea.nodeio.model.Model;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.view.object.player.PlayerV;

/**
 * A zone of the game map.
 */
public class ZoneM extends Observable implements Serializable
{
    private static final long serialVersionUID = -3060804350757580941L;

    // Width and height.
    public static final int SIZE = 4;

    private PlayerM mOwner;
    // Type of zone.
    private Model.Type mType;
    // Zone position in map.
    private final int mPositionInMap;

    public ZoneM(PlayerM owner, Model.Type type, int position)
    {
        mOwner = owner;
        mType = type;
        mPositionInMap = position;
    }

    public void setOwner(PlayerM owner)
    {
        mOwner = owner;
    }

    public void setType(Model.Type type)
    {
        mType = type;
    }

    public PlayerM getOwner()
    {
        return mOwner;
    }

    public Model.Type getType()
    {
        return mType;
    }

    public int getPositionInMap() { return mPositionInMap; }
}