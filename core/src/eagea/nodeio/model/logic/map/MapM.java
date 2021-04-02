package eagea.nodeio.model.logic.map;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Manage game zones, and players.
 */
public class MapM extends Observable
{
    public static final int ZONE_LINE = 5;

    private int nbZone;
    // Current zones on the map.
    private final ArrayList<ZoneM> mZones;

    public MapM()
    {
        mZones = new ArrayList<>();
        nbZone = 0;
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
        nbZone++;
        // Notify the associated view.
        notifyObservers();
    }

    public int getnbZone()
    {
        return nbZone;
    }

    //Return the zone n° id
    public ZoneM getZone(int id)
    {
        return mZones.get(id);
    }
}