package eagea.nodeio.model.logic.map;

import java.util.ArrayList;
import java.util.Observable;

import eagea.nodeio.model.logic.player.PlayerM;

/**
 * A zone of the game map.
 */
public class ZoneM extends Observable
{
    public static final int SIZE = 32;

    public enum Type { BLACK, GRASS, GRAVEL, ROCK, SAND, SNOW }

    // Player who owns this zone.
    private PlayerM mOwner;
    // Type of map.
    private Type mType;
    // Zone ID.
    private final int mId;
    //Players within the zone
    private ArrayList<PlayerM> mPlayers;

    public ZoneM(PlayerM player, int id)
    {
        mOwner = player;
        // Assign a random type.
        mType = Type.values()[(int) (Math.random() * Type.values().length)];
        // Assign an ID to the zone
        mId = id;

        mPlayers = new ArrayList<PlayerM>();
        mPlayers.add(mOwner);
    }

    /**
     * Change the owner of this zone.
     */
    public void changeOwner(PlayerM player, Type type)
    {
        mPlayers.remove(mOwner);
        mOwner = player;
        mPlayers.add(mOwner);
        mType = type;
        // Notify the associated view.
        notifyObservers();
    }

    //Add player to the zone
    public void add(PlayerM player)
    {
        mPlayers.add(player);
    }

    //Remove player of the zone
    public void remove(PlayerM player)
    {
        mPlayers.remove(player);
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
