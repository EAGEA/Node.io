package eagea.nodeio.view;

import com.badlogic.gdx.graphics.Color;

import eagea.nodeio.GameScreen;
import eagea.nodeio.model.Model;
import eagea.nodeio.view.object.map.MapV;
import eagea.nodeio.view.object.player.PlayerV;

/**
 * Handle all the graphical representations.
 */
public class View
{
    // Model.
    private final Model mModel;

    // The world map.
    private final MapV mMap;
    // Associated player.
    private final PlayerV mPlayer;

    public View(Model model)
    {
        mModel = model;
        mMap = new MapV(model.getMap(), model.getPlayer());
        mPlayer = new PlayerV(GameScreen.mPlayerAtlas.findRegion("down"),
                getPlayerColor());
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

    public Color getPlayerColor()
    {
        switch (mModel.getType())
        {
            case BLACK: return Color.WHITE;
            case GRASS: return Color.MAROON;
            case GRAVEL: return Color.RED;
            case ROCK: return Color.BLUE;
            case SAND: return Color.OLIVE;
            case SNOW: return Color.GRAY;
        }

        return null;
    }
}