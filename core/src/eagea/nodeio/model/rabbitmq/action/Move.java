package eagea.nodeio.model.rabbitmq.action;

import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Player has moved. Send this action to the host so that it can
 * confirm by sending it to everyone.
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
