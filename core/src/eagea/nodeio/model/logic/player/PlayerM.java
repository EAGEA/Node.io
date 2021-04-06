package eagea.nodeio.model.logic.player;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Observable;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;

/**
 * Handle the player movements.
 */
public class PlayerM extends Observable implements Serializable
{
    private static final long serialVersionUID = 4604898990645685618L;

    // Event; Direction of movements.
    public enum Event { LEFT, RIGHT, UP, DOWN }
    // Event; speak.
    public enum Speak { HELLO, LOOSER, BYE }
    // Hair color.
    public enum Color { RED, GREEN, BLUE }

    // Coordinate in its current zone (array like).
    private int mZone;
    private final Vector2 mPosition;
    // Her/his hair color.
    private final Color mColor;
    // Her/his sentence to say.
    private Speak mSentence;
    // Current environment.
    private MapM mMap;

    public PlayerM(int i, int j, int zone, MapM map)
    {
        mPosition = new Vector2(i, j);
        mZone = zone;
        mMap = map;
        mColor = Color.values()[(int) (Math.random() * Color.values().length)];
    }

    public void moveRight()
    {
        if (mPosition.y - 1 < 0)
        {
            // Next zone or impossible move.
            if (mZone % MapM.ZONE_LINE == 0)
            {
                // The player can not go over limits.
                return;
            }
            else
            {
                // The player exit the current zone.
                mPosition.y = ZoneM.SIZE - 1;
                mZone --;
            }
        }
        else
        {
            // Move within the zone.
            mPosition.y --;
        }
        // Notify that player has moved to the left.
        notify(Event.RIGHT);
    }

    public void moveLeft()
    {
        if (mPosition.y + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (mZone >= mMap.getNbZones() -1 || ((mZone + 1) % MapM.ZONE_LINE) == 0)
            {
                // The player can not go over limits.
                return;
            }
            else
            {
                // The player exit the current zone.
                mPosition.y = 0;
                mZone ++;
            }
        }
        else
        {
            // Move within the zone.
            mPosition.y ++;
        }
        // Notify that player has moved to the right.
        notify(Event.LEFT);
    }

    public void moveUp()
    {
        if (mPosition.x + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (((mZone + 1) + MapM.ZONE_LINE) > mMap.getNbZones())
            {
                // The player can not go over limits.
                return;
            }
            else
            {
                // The player exit the current zone.
                mPosition.x = 0;
                mZone += MapM.ZONE_LINE;
            }
        }
        else
        {
            // Move within the zone.
            mPosition.x ++;
        }
        // Notify that player has moved upwards.
        notify(Event.UP);
    }

    public void moveDown()
    {
        if (mPosition.x - 1 < 0)
        {
            // Next zone or impossible move.
            if ((mZone - MapM.ZONE_LINE) < 0)
            {
                // The player can not go over limits.
                return;
            }
            else
            {
                // The player exit the current zone.
                mPosition.x = ZoneM.SIZE - 1;
                mZone -= MapM.ZONE_LINE;
            }
        }
        else
        {
            // Move within the zone.
            mPosition.x --;
        }
        // Notify that player has moved downwards.
        notify(Event.DOWN);
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
            return ((PlayerM) o).getI() == getI()
                    && ((PlayerM) o).getJ() == getJ()
                    && ((PlayerM) o).getZone() == getZone();
        }

        return false;
    }

    public void setMap(MapM map)
    {
        mMap = map;
    }

    public void setZone(int zone)
    {
        mZone = zone;
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