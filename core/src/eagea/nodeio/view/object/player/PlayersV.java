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
                PlayersM.Event event = (PlayersM.Event) o;
                PlayerM player;

                switch (event)
                {
                    case ADD:
                        player = mPlayers.get(mPlayers.getNbPlayers() - 1);
                        mPlayersV.add(new PlayerV(mPlayer, player,
                                player.getColor().toString().toLowerCase()));
                        break;
                    case REMOVE:
                        // TODO
                        /*
                        mPlayersV.forEach(p ->
                        {
                            if (p)
                        });
                        player = mPlayers.get(mPlayers.getNbPlayers() - 1);
                        break;
                         */
                }
            }
        }
    }
}