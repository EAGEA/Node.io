package eagea.nodeio.model.logic.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import eagea.nodeio.model.logic.map.ZoneM;

/**
 * Manage all the game players.
 */
public class PlayersM extends Observable implements Serializable
{
    private static final long serialVersionUID = -6069243052017409241L;

    // Event.
    public enum Event { ADD, REMOVE }
    // To notify observers.
    public static class EventContainer
    {
        private final Event mEvent;
        private final PlayerM mPlayer;

        public EventContainer(Event event, PlayerM player)
        {
            mEvent = event;
            mPlayer = player;
        }

        public Event getEvent()
        {
            return mEvent;
        }

        public PlayerM getPlayer()
        {
            return mPlayer;
        }
    }

    // Current players on the map.
    private final ArrayList<PlayerM> mPlayers;

    public PlayersM()
    {
        mPlayers = new ArrayList<>();
    }

    public ArrayList<PlayerM> getPlayers()
    {
        return mPlayers;
    }

    public PlayerM find(PlayerM player)
    {
        final PlayerM[] result = { null };

        mPlayers.forEach(p ->
                {
                    if (p.equals(player))
                    {
                        result[0] = p;
                    }
                }
        );

        return result[0];
    }

    public void remove(PlayerM player)
    {
        mPlayers.remove(player);
        // Notify the associated view.
        notify(Event.REMOVE, player);
    }

    public void addWithoutNotify(PlayerM player)
    {
        mPlayers.add(player);
    }

    public void add(PlayerM player)
    {
        mPlayers.add(player);
        // Notify the associated view.
        notify(Event.ADD, player);
    }

    public PlayerM get(int id)
    {
        return mPlayers.get(id);
    }

    public void notify(Event event, PlayerM player)
    {
        setChanged();
        notifyObservers(new EventContainer(event, player));
    }

    public int getNbPlayers()
    {
        return mPlayers.size();
    }
}