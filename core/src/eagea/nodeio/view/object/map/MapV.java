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
        mMap.getZones().forEach(z -> mZones.add(new ZoneV(z)));

    }

    public void render()
    {
        // Reverse render order because of isometric rendering.
        for (int i = mZones.size() - 1 ; i >= 0 ; i --)
        {
            mZones.get(i).render();
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
            System.out.println(mPlayer.getZone() + " " + mPlayer.getI() + " " + mPlayer.getJ());
            // Player has moved; update zones position.
            Vector2 delta = (Vector2) o;
            mZones.forEach(z -> z.updatePosition((int) delta.x, (int) delta.y));
        }
    }
}