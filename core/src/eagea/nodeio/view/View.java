package eagea.nodeio.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.model.Model;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.view.object.background.Parallax;
import eagea.nodeio.view.object.hud.Exit;
import eagea.nodeio.view.object.hud.Score;
import eagea.nodeio.view.object.hud.Speak;
import eagea.nodeio.view.object.map.CellV;
import eagea.nodeio.view.object.map.MapV;
import eagea.nodeio.view.object.player.PlayersV;

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
    // Associated players.
    private final PlayersV mPlayers;
    // HUD.
    private final Exit mExit;
    private final Score mScore;
    private final Speak mSpeak;

    public View(Model model)
    {
        mModel = model;
        mBackground = new Parallax();
        mMap = new MapV(model.getMap(), model.getPlayer());
        mPlayers = new PlayersV(model.getPlayers(), model.getPlayer());
        mExit = new Exit(this);
        mScore = new Score(this);
        mSpeak = new Speak(this);
    }

    public void render(float delta)
    {
        mBackground.render(delta);
        mMap.render(delta);
        mPlayers.render(delta);
        mExit.render(delta);
        mScore.render(delta);
        mSpeak.render(delta);
    }

    /**
     * Check if an item of the HUD was touched. Return true if so.
     */
    public boolean isTouched(Vector2 position)
    {
        return mExit.isTouched(position) || mSpeak.isTouched(position);
    }

    public Model getModel()
    {
        return mModel;
    }

    public MapV getMap()
    {
        return mMap;
    }

    public PlayersV getPlayers()
    {
        return mPlayers;
    }

    /**
     * Return the isometric coordinate of the item in the world (the
     * rendering coordinate system), depending on its coordinates in the map
     * and the player ones.
     * @param playerPosition the position of the player in the map.
     * @param itemPosition the position of the wanted item in the map.
     * @return the coordinate of the item in the world, centered on the corresponding cell.
     */
    public static Vector2 getCoordinates(Vector3 playerPosition, Vector3 itemPosition)
    {
        // Map the positions in the full map grid.
        int playerIinMap = (((int) playerPosition.z / MapM.ZONE_LINE) * ZoneM.SIZE
                + (int) playerPosition.x);
        int zoneIinMap = (((int) itemPosition.z / MapM.ZONE_LINE) * ZoneM.SIZE
                + (int) itemPosition.x);
        int playerJinMap = (((int) playerPosition.z % MapM.ZONE_LINE) * ZoneM.SIZE
                + (int) playerPosition.y);
        int zoneJinMap = (((int) itemPosition.z % MapM.ZONE_LINE) * ZoneM.SIZE
                + (int) itemPosition.y);
        // Get difference between them (player is always in 0, 0).
        int i = zoneIinMap - playerIinMap;
        int j = zoneJinMap - playerJinMap;

        return new Vector2(getCoordinateX(i, j), getCoordinateY(i, j));
    }

    private static float getCoordinateX(int i, int j)
    {
        // Space between tiles for grid.
        float space = (i - j) * CellV.TILE_SIZE / 10f;
        // To center.
        float center = -CellV.TILE_SIZE / 2f;

        return (i - j) * (CellV.TILE_SIZE / 2f) + space + center;
    }

    private static float getCoordinateY(int i, int j)
    {
        // Space between tiles for grid.
        float space = (i + j) * CellV.TILE_SIZE / 10f;
        // To center.
        float center = -CellV.TILE_SIZE / 2f;
        // The tile graphical elevation.
        float elevation = -(i + j) * CellV.TILE_SIZE / 3.75f;

        return (i + j) * (CellV.TILE_SIZE / 2f) + elevation + space + center;
    }
}