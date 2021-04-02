package eagea.nodeio.model.logic.map;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Manage game zones, and players.
 */
public class MapM extends Observable
{
    public static final int SIZE = 30;
    // Current zones on the map.
    private final ArrayList<ZoneM> mZones;

    public MapM()
    {
        mZones = new ArrayList<>();
    }

    public ArrayList<ZoneM> getZones()
    {
        return mZones;
    }

    public void remove(ZoneM zone)
    {
        mZones.remove(zone);
        // Notify the associated view.
        notifyObservers();
    }

    public void add(ZoneM zone)
    {
        mZones.add(zone);
        // Notify the associated view.
        notifyObservers();
    }
}