package eagea.nodeio.model.logic.map;

import java.util.Observable;

/**
 * A zone of the game map.
 */
public class ZoneM extends Observable
{
    public static final int SIZE = 32;

    public enum Type { BLACK, GRASS, GRAVEL, ROCK, SAND, SNOW }

    // Player who owns of the map.
    private String mOwner; // To be changed
    // Type of map.
    private Type mType;

    public ZoneM(String player)
    {
        mOwner = player;
        // Assign a random type.
        mType = Type.values()[(int) (Math.random() * Type.values().length)];
    }

    /**
     * Change the owner of this zone.
     */
    public void changeOwner(String player, Type type)
    {
        mOwner = player;
        mType = type;
        // Notify the associated view.
        notifyObservers();
    }

    public String getOwner()
    {
        return mOwner;
    }

    public Type getType()
    {
        return mType;
    }

    public void setOwner(String player)
    {
        mOwner = player;
    }

    public void setType(Type type)
    {
        mType = type;
    }
}
