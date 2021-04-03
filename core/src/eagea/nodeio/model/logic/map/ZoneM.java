package eagea.nodeio.model.logic.map;

import java.io.Serializable;
import java.util.Observable;

import eagea.nodeio.model.Model;

/**
 * A zone of the game map.
 */
public class ZoneM extends Observable implements Serializable
{
    public static final int SIZE = 4;

    // Type of zone (ID).
    private Model.Type mType;
    // Zone position in map.
    private final int mPositionInMap;

    public ZoneM(Model.Type type, int id)
    {
        mType = type;
        // Position in map.
        mPositionInMap = id;
    }

    public void setType(Model.Type type)
    {
        mType = type;
    }

    public Model.Type getType()
    {
        return mType;
    }

    public int getPositionInMap() { return mPositionInMap; }
}