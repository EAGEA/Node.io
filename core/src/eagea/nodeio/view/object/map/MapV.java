package eagea.nodeio.view.object.map;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.view.object.player.PlayerV;

/**
 * Manage game zones, and players.
 */
public class MapV implements Observer
{
    // Model.
    private final MapM mMap;
    private final PlayerM mPlayer;
    // Current zones on the map.
    private final ArrayList<ZoneV> mZones;

    public MapV(MapM map, PlayerM player)
    {
        // Get the model and observe it.
        mMap = map;
        mPlayer = player;
        mMap.addObserver(this);
        mPlayer.addObserver(this);
        // Load the zones.
        mZones = new ArrayList<>();
        mMap.getZones().forEach(z -> mZones.add(new ZoneV(z, player)));

    }

    public void render(float delta)
    {
        // Reverse render order because of isometric rendering.
        for (int i = mZones.size() - 1 ; i >= 0 ; i --)
        {
            mZones.get(i).render(delta);
        }
    }

    @Override
    public void update(Observable observable, Object o)
    {
        if (observable == mMap)
        {
            // Add, remove zones.....
            // TODO
        }
        else if (observable == mPlayer)
        {
            if (o != null)
            {
                // Player has moved.
                PlayerM.Orientation orientation = (PlayerM.Orientation) o;
                final Vector2 delta = new Vector2();
                // Get the coordinates of the movement.
                switch (orientation)
                {
                    case LEFT: delta.y = -1; break;
                    case RIGHT: delta.y = 1; break;
                    case UP: delta.x = -1; break;
                    case DOWN: delta.x = 1; break;
                }
                // Shift each zone with this delta.
                mZones.forEach(z -> z.updatePosition((int) delta.x, (int) delta.y));
            }
        }
    }
}