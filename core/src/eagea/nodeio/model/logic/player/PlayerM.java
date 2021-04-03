package eagea.nodeio.model.logic.player;

import com.badlogic.gdx.math.Vector2;

import java.util.Observable;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;

/**
 * Handle the player movements.
 */
public class PlayerM extends Observable
{
    private final String mPseudo;
    // Coordinate in its current zone (array like).
    private int mZone;
    private final Vector2 mPosition;
    // Current environment.
    private final MapM mMap;

    public PlayerM(String pseudo, int i, int j, MapM mapM)
    {
        mPseudo = pseudo;
        mPosition = new Vector2(i, j);
        mMap = mapM;
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
        notifyObservers(new Vector2(0, 1));
    }

    public void moveLeft()
    {
        if (mPosition.y + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (mZone < mMap.getNbZone() || ((mZone + 1) % MapM.ZONE_LINE) == 0)
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
        notifyObservers(new Vector2(0, -1));
    }

    public void moveUp()
    {
        if (mPosition.x + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (((mZone + 1) + MapM.ZONE_LINE) > mMap.getNbZone())
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
        notifyObservers(new Vector2(-1, 0));
    }

    public void moveDown()
    {
        if (mPosition.x - 1 < 0)
        {
            // Next zone or impossible move.
            if (((mZone - 1) - MapM.ZONE_LINE) < 0)
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
        notifyObservers(new Vector2(1, 0));
    }

    public String getPseudo()
    {
        return mPseudo;
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