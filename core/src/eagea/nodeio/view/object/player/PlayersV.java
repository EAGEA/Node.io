package eagea.nodeio.view.object.player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.model.logic.player.PlayersM;
import eagea.nodeio.view.object.map.ZoneV;

/**
 * Manage all the game players.
 */
public class PlayersV implements Observer
{
    // Model.
    private final PlayersM mPlayers;
    private final PlayerM mPlayer;
    // Current zones on the map.
    private final ArrayList<PlayerV> mPlayersV;

    public PlayersV(PlayersM players, PlayerM player)
    {
        // Get the model and observe it.
        mPlayers = players;
        mPlayer = player;
        mPlayers.addObserver(this);
        // Load the players.
        mPlayersV = new ArrayList<>();
        mPlayers.getPlayers().forEach(p -> mPlayersV.add(new PlayerV(mPlayer, p,
                p.getColor().toString().toLowerCase())));

    }

    public void render(float delta)
    {
        mPlayersV.forEach(p -> p.render(delta));
    }

    @Override
    public void update(Observable observable, Object o)
    {
        if (observable == mPlayers)
        {
            if (o != null)
            {
                // Map has changed.
                PlayersM.EventContainer container = (PlayersM.EventContainer) o;
                PlayersM.Event event = container.getEvent();
                PlayerM player = container.getPlayer();

                switch (event)
                {
                    case ADD:
                        mPlayersV.add(new PlayerV(mPlayer, player,
                                player.getColor().toString().toLowerCase()));
                        break;
                    case REMOVE:
                        final PlayerV[] to_remove = new PlayerV[1];
                        // Search for player to remove.
                        mPlayersV.forEach(p ->
                        {
                            if (p.getRealPlayer().equals(player))
                            {
                                to_remove[0] = p;
                            }
                        });
                        // Remove it.
                        if (to_remove[0] != null)
                        {
                            mPlayersV.remove(to_remove[0]);
                        }
                }
            }
        }
    }
}