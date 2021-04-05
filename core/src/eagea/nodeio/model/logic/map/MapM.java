package eagea.nodeio.model.logic.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Manage game zones, and players.
 */
public class MapM extends Observable implements Serializable
{
    // Event.
    public enum Event { ADD, REMOVE }
    // Number of zone in the same row.
    public static final int ZONE_LINE = 4;

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
    }

    public void add(ZoneM zone)
    {
        mZones.add(zone);
        // Notify the associated view.
        notify(Event.ADD);
    }

    public void notify(Event event)
    {
        setChanged();
        notifyObservers(event);
    }

    public int getNbZones()
    {
        return mZones.size();
    }

    public ZoneM getZone(int id)
    {
        return mZones.get(id);
    }
}