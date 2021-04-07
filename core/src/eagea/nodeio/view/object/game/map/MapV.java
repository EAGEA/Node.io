package eagea.nodeio.view.object.game.map;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * Manage game zones, and players.
 */
public class MapV implements Observer
{
    // Cells' animation.
    private final float TIME_PER_FRAME = 1f;

    // Model.
    private final MapM mMap;
    private final PlayerM mPlayer;
    // Current zones on the map.
    private final ArrayList<eagea.nodeio.view.object.game.map.ZoneV> mZones;
    // Cells' animation.
    private boolean mHighlighted;
    private float mTimeSinceLastRender;

    public MapV(MapM map, PlayerM player)
    {
        // Get the model and observe it.
        mMap = map;
        mPlayer = player;
        mMap.addObserver(this);
        // Load the zones.
        mZones = new ArrayList<>();
        mMap.getZones().forEach(z -> mZones.add(new eagea.nodeio.view.object.game.map.ZoneV(z, player)));
        // Load cells' animation.
        mHighlighted = false;
        mTimeSinceLastRender = 0f;

    }

    public void render(float delta)
    {
        // Reverse render order because of isometric rendering.
        for (int i = mZones.size() - 1 ; i >= 0 ; i --)
        {
            eagea.nodeio.view.object.game.map.ZoneV zone = mZones.get(i);
            // Update cell highlighted animation
            mTimeSinceLastRender += delta;

            if (mTimeSinceLastRender >= TIME_PER_FRAME)
            {
                mTimeSinceLastRender = 0f;
                mHighlighted = ! mHighlighted;
            }

            zone.render(delta, mHighlighted && zone.getZone().getOwner()
                    .equals(mPlayer.getID()));
        }
    }

    @Override
    public void update(Observable observable, Object o)
    {
        if (observable == mMap)
        {
            if (o != null)
            {
                // Map has changed.
                MapM.Event event = (MapM.Event) o;

                switch (event)
                {
                    case ADD:
                        mZones.add(new ZoneV(mMap.get(mMap.getNbZones() - 1), mPlayer));
                        break;
                }
            }
        }
    }
}