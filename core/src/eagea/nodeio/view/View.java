package eagea.nodeio.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import eagea.nodeio.model.Model;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.view.object.game.background.Parallax;
import eagea.nodeio.view.object.game.hud.Catch;
import eagea.nodeio.view.object.game.hud.Caught;
import eagea.nodeio.view.object.game.hud.Exit;
import eagea.nodeio.view.object.game.hud.Joystick;
import eagea.nodeio.view.object.game.hud.Score;
import eagea.nodeio.view.object.game.hud.Speak;
import eagea.nodeio.view.object.game.map.CellV;
import eagea.nodeio.view.object.game.map.MapV;
import eagea.nodeio.view.object.game.player.PlayersV;
import eagea.nodeio.view.object.menu.Logo;
import eagea.nodeio.view.object.menu.Start;

/**
 * Handle all the graphical representations.
 */
public class View
{
    // Model.
    private final Model mModel;
    // Game:
    // - The background.
    private Parallax mBackground;
    // - The world map.
    private MapV mMap;
    // - Associated players.
    private PlayersV mPlayers;
    // - HUD.
    private Score mScore;
    private Exit mExit;
    private Speak mSpeak;
    private Joystick mJoystick;
    private Catch mCatch;
    private Caught mCaught;
    // Menu GUI:
    private final Logo mLogo;
    private final Start mStart;

    public View(Model model)
    {
        mModel = model;
        // Menu.
        mLogo = new Logo(this);
        mStart = new Start(this);
    }

    public void onStartGame()
    {
        mBackground = new Parallax();
        mMap = new MapV(mModel.getMap(), mModel.getPlayer());
        mPlayers = new PlayersV(mModel.getPlayers(), mModel.getPlayer());
        mScore = new Score(this);
        mExit = new Exit(this);
        mSpeak = new Speak(this);
        mJoystick = new Joystick(this);
        mCatch = new Catch(this);
        mCaught = new Caught(this);
        // Can start game now.
        mModel.setState(Model.State.GAME);
    }

    public void render(float delta)
    {
        switch (mModel.getState())
        {
            case MENU:
                // Render menu screen.
                mLogo.render(delta);
                mStart.render(delta);
                break;
            case CAUGHT:
            case GAME:
                // Render game screen.
                mBackground.render(delta);
                mMap.render(delta);
                mPlayers.render(delta);
                mScore.render(delta);
                mExit.render(delta);
                mSpeak.render(delta);
                mJoystick.render(delta);
                mCatch.render(delta);
                mCaught.render(delta);
                break;
        }
    }

    /**
     * Check if an item of the HUD was touched. Return true if so.
     */
    public boolean isTouched(Vector2 position)
    {
        switch (mModel.getState())
        {
            case MENU:
                return mStart.isTouched(position);
            case GAME:
                return mExit.isTouched(position)
                        || mSpeak.isTouched(position)
                        || mJoystick.isTouched(position)
                        || mCatch.isTouched(position);
            case CAUGHT:
                return mCaught.isTouched(position);
        }

        return false;
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