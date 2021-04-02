package eagea.nodeio.model.logic.player;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;

/**
 * Handle the player movements.
 */
public class PlayerM
{
    private String mPseudo;
    // Coordinate in its current zone.
    private int mZone;
    private int mI, mJ;
    // Current environment.
    private MapM mMap;

    public void Player (String pPseudo, int i, int j, MapM mapM)
    {
        mPseudo = pPseudo;
        mI = i;
        mJ = j;
        mMap = mapM;
    }

    public void moveRight()
    {
        if (mJ + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (((mZone + 1) % MapM.ZONE_LINE) == 0)
            {
                // The player can not go over limits.
                System.err.println("ZONE: Impossible move!");
            }
            else
            {
                // The player exit the current zone.
                mJ = 0;
                mZone ++;
            }
        }
        else
        {
            // Move within the zone.
            mJ ++;
        }
    }

    public void moveLeft()
    {
        if (mJ - 1 < 0)
        {
            // Next zone or impossible move.
            if (((mZone - 1) % MapM.ZONE_LINE) == MapM.ZONE_LINE - 1)
            {
                // The player can not go over limits.
                System.err.println("ZONE: Impossible move!");
            }
            else
            {
                // The player exit the current zone.
                mJ = ZoneM.SIZE - 1;
                mZone --;
            }
        }
        else
        {
            // Move within the zone.
            mJ --;
        }
    }

    public void moveUp()
    {
        if (mI - 1 < 0)
        {
            // Next zone or impossible move.
            if (((mZone - 1) - MapM.ZONE_LINE) < 0)
            {
                // The player can not go over limits.
                System.err.println("ZONE: Impossible move!");
            }
            else
            {
                // The player exit the current zone.
                mI = ZoneM.SIZE - 1;
                mZone -= MapM.ZONE_LINE;
            }
        }
        else
        {
            // Move within the zone.
            mJ --;
        }
    }

    public void moveDown()
    {
        if (mI + 1 >= ZoneM.SIZE)
        {
            // Next zone or impossible move.
            if (((mZone + 1) + MapM.ZONE_LINE) > mMap.getNbZone())
            {
                // The player can not go over limits.
                System.err.println("ZONE: Impossible move!");
            }
            else
            {
                // The player exit the current zone.
                mI = 0;
                mZone += MapM.ZONE_LINE;
            }
        }
        else
        {
            // Move within the zone.
            mJ ++;
        }
    }

    public String getPseudo()
    {
        return mPseudo;
    }

    public int getJ()
    {
        return mJ;
    }

    public int getI()
    {
        return mI;
    }
}