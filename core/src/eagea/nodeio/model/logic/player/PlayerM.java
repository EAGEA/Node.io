package eagea.nodeio.model.logic.player;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.util.Observable;

import eagea.nodeio.model.logic.map.CellM;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;

/**
 * Handle the player movements.
 */
public class PlayerM extends Observable implements Serializable
{
    private static final long serialVersionUID = 4863938596442714449L;

    // Event; Direction of movements.
    public enum Event { LEFT, RIGHT, UP, DOWN }
    // Event; speak.
    public enum Speak { HELLO, LOOSER, BYE }
    // Hair color.
    public enum Color { RED, GREEN, BLUE }

    // Unique ID (ie.e the rabbit queue name).
    private final String mID;
    // Coordinate in its current zone (array like).
    private int mZone;
    private final Vector2 mPosition;
    // Her/his hair color.
    private final Color mColor;
    // Her/his sentence to say.
    private Speak mSentence;
    // Current environment.
    private MapM mMap;

    public PlayerM(String ID, int i, int j, int zone, MapM map)
    {
        mID = ID;
        mPosition = new Vector2(i, j);
        mZone = zone;
        mMap = map;
        mColor = Color.values()[(int) (Math.random() * Color.values().length)];
    }

    public boolean moveRight()
    {
        if (mPosition.y - 1 < 0)
        {
            // Next zone or impossible move.
            if (mZone % MapM.ZONE_LINE == 0)
            {
                // The player can not go over limits.
                return false;
            }
            else
            {
                if (mMap.get(mZone - 1).getCells()[(int) mPosition.x][ZoneM.SIZE - 1].getType()
                        != CellM.Type.EMPTY)
                {
                    // Cell is not empty (there is a bush
                    // or something else on it), can't go on it.
                    return false;
                }
                // The player exit the current zone.
                mPosition.y = ZoneM.SIZE - 1;
                mZone --;
            }
        }
        else
        {
            if (mMap.get(mZone).getCells()[(int) mPosition.x][(int) (mPosition.y - 1)].getType()
                    != CellM.Type.EMPTY)
            {
                // Cell is not empty (there is a bush
                // or something else on it), can't go on it.
                return false;
            }
            // Move within the zone.
            mPosition.y --;
        }
        // Notify that player has moved to the left.
        notify(Event.RIGHT);

        return true;
    }

    public boolean moveLeft()
    {
        if (mPosition.y + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (mZone >= mMap.getNbZones() -1 || ((mZone + 1) % MapM.ZONE_LINE) == 0)
            {
                // The player can not go over limits.
                return false;
            }
            else
            {
                if (mMap.get(mZone + 1).getCells()[(int) mPosition.x][0].getType()
                        != CellM.Type.EMPTY)
                {
                    // Cell is not empty (there is a bush
                    // or something else on it), can't go on it.
                    return false;
                }
                // The player exit the current zone.
                mPosition.y = 0;
                mZone ++;
            }
        }
        else
        {
            if (mMap.get(mZone).getCells()[(int) mPosition.x][(int) (mPosition.y + 1)].getType()
                    != CellM.Type.EMPTY)
            {
                // Cell is not empty (there is a bush
                // or something else on it), can't go on it.
                return false;
            }
            // Move within the zone.
            mPosition.y ++;
        }
        // Notify that player has moved to the right.
        notify(Event.LEFT);

        return true;
    }

    public boolean moveUp()
    {
        if (mPosition.x + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (((mZone + 1) + MapM.ZONE_LINE) > mMap.getNbZones())
            {
                // The player can not go over limits.
                return false;
            }
            else
            {
                if (mMap.get(mZone + MapM.ZONE_LINE).getCells()[0][(int) (mPosition.y)].getType()
                        != CellM.Type.EMPTY)
                {
                    // Cell is not empty (there is a bush
                    // or something else on it), can't go on it.
                    return false;
                }
                // The player exit the current zone.
                mPosition.x = 0;
                mZone += MapM.ZONE_LINE;
            }
        }
        else
        {
            if (mMap.get(mZone).getCells()[(int) mPosition.x + 1][(int) (mPosition.y)].getType()
                    != CellM.Type.EMPTY)
            {
                // Cell is not empty (there is a bush
                // or something else on it), can't go on it.
                return false;
            }
            // Move within the zone.
            mPosition.x ++;
        }
        // Notify that player has moved upwards.
        notify(Event.UP);

        return true;
    }

    public boolean moveDown()
    {
        if (mPosition.x - 1 < 0)
        {
            // Next zone or impossible move.
            if ((mZone - MapM.ZONE_LINE) < 0)
            {
                // The player can not go over limits.
                return false;
            }
            else
            {
                if (mMap.get(mZone - MapM.ZONE_LINE).getCells()[ZoneM.SIZE - 1]
                        [(int) (mPosition.y)].getType() != CellM.Type.EMPTY)
                {
                    // Cell is not empty (there is a bush
                    // or something else on it), can't go on it.
                    return false;
                }
                // The player exit the current zone.
                mPosition.x = ZoneM.SIZE - 1;
                mZone -= MapM.ZONE_LINE;
            }
        }
        else
        {
            if (mMap.get(mZone).getCells()[(int) mPosition.x - 1][(int) (mPosition.y)].getType()
                    != CellM.Type.EMPTY)
            {
                // Cell is not empty (there is a bush
                // or something else on it), can't go on it.
                return false;
            }
            // Move within the zone.
            mPosition.x --;
        }
        // Notify that player has moved downwards.
        notify(Event.DOWN);

        return true;
    }

    public void speak(Speak sentence)
    {
        mSentence = sentence;
        // Notify that player wants to speak.
        setChanged();
        notifyObservers(null);
    }

    public void notify(Event event)
    {
        setChanged();
        notifyObservers(event);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof PlayerM)
        {
            return mID.equals(((PlayerM) o).getID());
        }

        return false;
    }

    /**
     * @return player position but relative to the whole map
     * (not only her/his zone).
     */
    public Vector2 getMapPosition()
    {
        return new Vector2(((float) getZone() / MapM.ZONE_LINE) * ZoneM.SIZE + getI(),
                (getZone() % MapM.ZONE_LINE) * ZoneM.SIZE + getJ());
    }

    public String getID()
    {
        return mID;
    }

    public int getZone()
    {
        return mZone;
    }

    public Speak getSpeak()
    {
        return mSentence;
    }

    public int getJ()
    {
        return (int) mPosition.y;
    }

    public int getI()
    {
        return (int) mPosition.x;
    }

    public Color getColor()
    {
        return mColor;
    }
}