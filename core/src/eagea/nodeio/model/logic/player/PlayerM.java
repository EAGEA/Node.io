package eagea.nodeio.model.logic.player;

import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.map.MapM;

public class PlayerM
{
    private String mPseudo;
    private int mPosj,mPosi;
    private ZoneM mZone;

    //Player
    public void Player(String pPseudo, int initPosj, int initPosi,ZoneM zoneM) {
        mPseudo = pPseudo;
        mPosj = initPosj;
        mPosi = initPosi;
        mZone = zoneM;
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
    public int getposj()
    {
        return mPosj;
    }

    //Return the y position of the player
    public int getposi()
    {
        return mPosi;
    }

    //Return the current zone in which is the player
    public ZoneM getzone()
    {
        return mZone;
    }
}
