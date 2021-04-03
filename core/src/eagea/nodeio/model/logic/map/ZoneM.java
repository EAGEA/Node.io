package eagea.nodeio.model.logic.map;

import java.util.ArrayList;
import java.util.Observable;

import eagea.nodeio.model.logic.player.PlayerM;

/**
 * A zone of the game map.
 */
public class ZoneM extends Observable
{
    public static final int SIZE = 4;

    public enum Type { BLACK, GRASS, GRAVEL, ROCK, SAND, SNOW }

    // Player who owns this zone.
    private PlayerM mOwner;
    // Type of map.
    private Type mType;
    // Zone ID.
    private final int mId;

    public ZoneM(PlayerM player, int id)
    {
        mOwner = player;
        // Assign a random type.
        mType = Type.values()[(int) (Math.random() * Type.values().length)];
        // Assign an ID to the zone
        mId = id;
    }

    /**
     * Change the owner of this zone.
     */
    public void changeOwner(PlayerM player, Type type)
    {
        mOwner = player;
        mType = type;
        // Notify the associated view.
        hasChanged();
        notifyObservers();
    }

    public PlayerM getOwner()
    {
        return mOwner;
    }

    public Type getType()
    {
        return mType;
    }

    public int getId() { return mId; }

    public void setOwner(PlayerM player)
    {
        mOwner = player;
    }

    public void setType(Type type)
    {
        mType = type;
    }
}
