package eagea.nodeio.model.logic.player;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.util.Observable;

import eagea.nodeio.model.Model;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;

/**
 * Handle the player movements.
 */
public class PlayerM extends Observable implements Serializable
{
    // Direction of movements.
    public enum Orientation { LEFT, RIGHT, UP, DOWN }

    // Coordinate in its current zone (array like).
    private int mZone;
    private final Vector2 mPosition;
    // Current environment.
    private MapM mMap;

    public PlayerM(int i, int j)
    {
        mPosition = new Vector2(i, j);
    }

    public void moveRight()
    {
        if (mPosition.y - 1 < 0)
        {
            // Next zone or impossible move.
            if (mZone % MapM.ZONE_LINE == 0)
            {
                // The player can not go over limits.
                System.err.println("ZONE: Impossible move!");
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
        setChanged();
        notifyObservers(Orientation.RIGHT);
    }

    public void moveLeft()
    {
        if (mPosition.y + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (mZone >= mMap.getNbZones() -1 || ((mZone + 1) % MapM.ZONE_LINE) == 0)
            {
                // The player can not go over limits.
                System.err.println("ZONE: Impossible move!");
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
        setChanged();
        notifyObservers(Orientation.LEFT);
    }

    public void moveUp()
    {
        if (mPosition.x + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (((mZone + 1) + MapM.ZONE_LINE) > mMap.getNbZones())
            {
                // The player can not go over limits.
                System.err.println("ZONE: Impossible move!");
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
        setChanged();
        notifyObservers(Orientation.UP);
    }

    public void moveDown()
    {
        if (mPosition.x - 1 < 0)
        {
            // Next zone or impossible move.
            if ((mZone - MapM.ZONE_LINE) < 0)
            {
                // The player can not go over limits.
                System.err.println("ZONE: Impossible move!");
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
        setChanged();
        notifyObservers(Orientation.DOWN);
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

    public int getJ()
    {
        return (int) mPosition.y;
    }

    public int getI()
    {
        return (int) mPosition.x;
    }
}