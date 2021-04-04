package eagea.nodeio.model.rabbitmq.action;

import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Player has moved.
 */
public class Move extends Action
{
    PlayerM mPlayer;
    int mPosI,mPosJ;

    public Move(PlayerM player, int posi, int posj)
    {
        mPlayer = player;
        mPosI = posi;
        mPosJ = posj;
    }

    public PlayerM getPlayer() { return mPlayer; }

    public int getPosI() { return mPosI; }

    public int getPosJ() { return mPosJ; }
}
