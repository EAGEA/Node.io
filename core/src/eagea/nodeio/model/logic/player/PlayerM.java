package eagea.nodeio.model.logic.player;

import eagea.nodeio.model.logic.map.MapM;

public class PlayerM
{
    private String pseudo;
    private int posX,posY;
    private MapM mapM;

    //Player
    public void Player(String pPseudo, int initPosX, int initPosY, MapM m)
    {
        pseudo = pPseudo;
        posX = initPosX;
        posY = initPosY;
        mapM = m;
    }
    /*

    //Action to move the player to the left
    public void MoveLeft()
    {
        if(posX+1 > MapM.SIZE || mapM.IsOccupied(posX+1,posY))
            System.err.println("Error, impossible move !");
        posX ++;
    }

    //Action to move the player to the right
    public void MoveRight()
    {
        if(posX-1 < 0 || mapM.IsOccupied(posX-1,posY))
            System.err.println("Error, impossible move !");
        posX --;
    }

    //Action to move the player to the top
    public void MoveUp()
    {
        if(posY+1 > MapM.SIZE || mapM.IsOccupied(posX,posY+1))
            System.err.println("Error, impossible move !");
        posY ++;
    }

    //Action to move the player to the bottom
    public void MoveDown()
    {
        if(posY-1 < MapM.SIZE || mapM.IsOccupied(posX,posY-1))
            System.err.println("Error, impossible move !");
        posY --;
    }

    //Return the pseudo
    public String getPseudo()
    {
        return pseudo;
    }

    //Return the x position of the player
    public int getposX()
    {
        return posX;
    }

    //Return the y position of the player
    public int getposY()
    {
        return posY;
    }
     */
}
