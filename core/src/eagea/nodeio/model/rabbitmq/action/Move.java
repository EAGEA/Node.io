package eagea.nodeio.model.rabbitmq.action;

import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Player has moved. Send this action to the host so that it can
 * confirm it if no collision, by sending it to everyone.
 */
public class Move extends Action
{
    private static final long serialVersionUID = -2331051169444334502L;

    private final PlayerM.Event mOrientation;

    public Move(String ID, PlayerM.Event orientation)
    {
        super(ID);
        mOrientation = orientation;
    }

    public PlayerM.Event getOrientation()
    {
        return mOrientation;
    }
}
