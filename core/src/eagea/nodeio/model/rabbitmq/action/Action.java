package eagea.nodeio.model.rabbitmq.action;

import java.io.Serializable;

/**
 * Action type send to other nodes (i.e. users) on event.
 */
public abstract class Action implements Serializable
{
    private static final long serialVersionUID = 48659982445051210L;

    // The player identified with it's ID
    private final String mPlayer;

    public Action(String ID)
    {
        mPlayer = ID;
    }

    public String getPlayer()
    {
        return mPlayer;
    }
}
