package eagea.nodeio.model.logic.player;

import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.map.MapM;

public class PlayerM
{
    private String mPseudo;
    private int mPosj,mPosi;
    private ZoneM mZone;
    private MapM mMap; //Here the mMap is to attribute new zones

    //Player
    public void Player(String pPseudo, int initPosj, int initPosi,ZoneM zoneM,MapM mapM) {
        mPseudo = pPseudo;
        mPosj = initPosj;
        mPosi = initPosi;
        mZone = zoneM;
        mMap = mapM;
    }

    //Action to move the player to the right
    public void MoveRight()
    {
        //Next zone or impossible move
        if(mPosj+1 >= mZone.SIZE)
        {
            //The player can not go over limits
            if(((mZone.getId() + 1) % MapM.ZONE_LINE) == 0)
            {
                System.err.println("ZONE : Impossible move !");
            }
            //The player exit the current zone
            else
            {
                mPosj = 0;
                setZone(mMap.getZone(mZone.getId() + 1));
            }
        }
        //Right move within the zone
        else
        {
            mPosj++;
        }
    }

    //Action to move the player to the left
    public void MoveLeft()
    {
        //Next zone or impossible move
        if(mPosj-1 < 0)
        {
            //The player can not go over limits
            if(((mZone.getId() - 1) % MapM.ZONE_LINE) == MapM.ZONE_LINE - 1)
            {
                System.err.println("ZONE : Impossible move !");
            }
            //The player exit the current zone
            else
            {
                mPosj = mZone.SIZE - 1;
                setZone(mMap.getZone(mZone.getId() - 1));
            }
        }
        //Left move within the zone
        else
        {
            mPosj--;
        }
    }

    //Action to move the player to the top
    public void MoveUp()
    {
        mPosi ++;
    }

    //Action to move the player to the bottom
    public void MoveDown()
    {
        mPosi --;
    }

    //Return the pseudo
    public String getPseudo()
    {
        return mPseudo;
    }

    //Return the x position of the player
    public int getPosj()
    {
        return mPosj;
    }

    //Return the y position of the player
    public int getPosi()
    {
        return mPosi;
    }

    //Return the current zone in which is the player
    public ZoneM getZone()
    {
        return mZone;
    }

    //Attributes a new zone to the player
    public void setZone(ZoneM zone)
    {
        mZone = zone;
    }
}
