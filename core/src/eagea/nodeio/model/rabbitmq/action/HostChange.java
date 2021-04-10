package eagea.nodeio.model.rabbitmq.action;

/**
 * The host is disconnecting or has been caught.
 * Need to elect a new host, or close the game.
 */
public class HostChange extends Action
{
    private static final long serialVersionUID = 2250892038770002804L;

    /**
     * Sent by the host.
     */
    public HostChange(String newHost)
    {
        super(newHost);
    }
}