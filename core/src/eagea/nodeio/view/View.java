package eagea.nodeio.view;

import com.badlogic.gdx.graphics.Color;

import eagea.nodeio.GameScreen;
import eagea.nodeio.model.Model;
import eagea.nodeio.view.object.background.Parallax;
import eagea.nodeio.view.object.map.MapV;
import eagea.nodeio.view.object.player.PlayerV;

/**
 * Handle all the graphical representations.
 */
public class View
{
    // Model.
    private final Model mModel;

    // The background.
    private final Parallax mBackground;
    // The world map.
    private final MapV mMap;
    // Associated player.
    private final PlayerV mPlayer;

    public View(Model model)
    {
        mModel = model;
        mBackground = new Parallax();
        mMap = new MapV(model.getMap(), model.getPlayer());
        mPlayer = new PlayerV(model.getPlayer(), getPlayerColor());
    }

    public void render(float delta)
    {
        mBackground.render(delta);
        mMap.render(delta);
        mPlayer.render(delta);
    }

    public MapV getMap()
    {
        return mMap;
    }

    public String getPlayerColor()
    {
        switch ((int) (Math.random() * 3))
        {
            case 0: return "red";
            case 1: return "green";
            case 2: return "blue";
        }

        return null;
    }
}