package eagea.nodeio.model;

public class Player
{
    private String pseudo;
    private int posX,posY;

    //Player
    public void Player(String pPseudo,int initPosX,int initPosY)
    {
        pseudo = pPseudo;
        posX = initPosX;
        posY = initPosY;
    }

    //Action to move the player to the left
    public void MoveLeft()
    {
        /*TO DO : Add the map constraints (waiting the Map class) ...*/
        posX ++;
    }

    //Action to move the player to the right
    public void MoveRight()
    {
        /*TO DO : Add the map constraints (waiting the Map class) ...*/
        posX --;
    }

    //Action to move the player to top
    public void MoveUp()
    {
        /*TO DO : Add the map constraints (waiting the Map class) ...*/
        posY ++;
    }

    //Action to move the player to the bottom
    public void MoveDown()
    {
        /*TO DO : Add the map constraints (waiting the Map class) ...*/
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
}
