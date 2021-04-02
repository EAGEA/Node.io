package eagea.nodeio.model.rabbitmq;

import com.rabbitmq.client.Channel;

/**
 * Handle all the RabbitMQ communications with other players.
 */
public class Node
{
    private Channel mChannel;

    public Node(Channel channel)
    {
        mChannel = channel;
    }

}
