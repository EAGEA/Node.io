package eagea.nodeio.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eagea.nodeio.model.Model;
import eagea.nodeio.view.object.map.MapV;

/**
 * Handle all the graphical representations.
 */
public class View
{
    // The world map.
    private final MapV mMap;
    // Associated player.
    //private final PlayerV mPlayer;

    public View(Model model)
    {
        mMap = new MapV(model.getMap());
    }

    public void render()
    {
        mMap.render();
    }

    public MapV getMap()
    {
        return mMap;
    }
}