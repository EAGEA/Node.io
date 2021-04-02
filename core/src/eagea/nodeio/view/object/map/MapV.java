package eagea.nodeio.view.object.map;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import eagea.nodeio.model.logic.map.MapM;

/**
 * Manage game zones, and players.
 */
public class MapV implements Observer
{
    // Model.
    private final MapM mMap;
    // Current zones on the map.
    private final ArrayList<ZoneV> mZones;

    public MapV(MapM map)
    {
        // Get the model and observe it.
        mMap = map;
        mMap.addObserver(this);
        // Load the zones.
        mZones = new ArrayList<>();
        mMap.getZones().forEach(z -> mZones.add(new ZoneV(z)));

    }

    public void render()
    {
        mZones.forEach(ZoneV::render);
    }

    public void update(float delta)
    {
        mZones.forEach(z -> z.update(delta));
    }

    @Override
    public void update(Observable observable, Object o)
    {
        // Map model has changed.
        // Add, remove zones.....
    }
}