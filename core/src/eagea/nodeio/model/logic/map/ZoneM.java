package eagea.nodeio.model.logic.map;

import java.io.Serializable;
import java.util.Observable;

/**
 * A zone of the game map.
 */
public class ZoneM extends Observable implements Serializable
{
    private static final long serialVersionUID = -3060804350757580941L;

    // Type of zone.
    public enum Type { BLACK, GRASS, GRAVEL, ROCK, SAND, SNOW }

    // Width and height.
    public static final int SIZE = 5;

    // ID of the player who owns this zone.
    private String mOwner;
    // Type of zone.
    private Type mType;
    // Zone position in map.
    private final int mPositionInMap;

    public ZoneM(String owner, Type type, int position)
    {
        mOwner = owner;
        mType = type;
        mPositionInMap = position;
    }

    public void setOwner(String owner)
    {
        mOwner = owner;
    }

    public void setType(Type type)
    {
        mType = type;
    }

    public String getOwner()
    {
        return mOwner;
    }

    public Type getType()
    {
        return mType;
    }

    public int getPositionInMap() { return mPositionInMap; }
}