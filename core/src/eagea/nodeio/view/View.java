package eagea.nodeio.view;

import eagea.nodeio.GameScreen;
import eagea.nodeio.model.Model;
import eagea.nodeio.view.object.map.MapV;
import eagea.nodeio.view.object.player.PlayerV;

/**
 * Handle all the graphical representations.
 */
public class View
{
    // The world map.
    private final MapV mMap;
    // Associated player.
    private final PlayerV mPlayer;

    public View(Model model)
    {
        mMap = new MapV(model.getMap(), model.getPlayer());
        mPlayer = new PlayerV(GameScreen.mPlayerAtlas.findRegion("player"));
    }

    public void render()
    {
        mMap.render();
        mPlayer.render();
    }

    public MapV getMap()
    {
        return mMap;
    }
}