package eagea.nodeio.view.object.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Observable;
import java.util.Observer;

import eagea.nodeio.Main;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * A cell of a zone render with isometric projection.
 */
public class CellV
{
    public static final float TILE_SIZE = 2f;

    // Model.
    private final ZoneM mZone;
    private final PlayerM mPlayer;
    // Current frame.
    private final TextureRegion[] mTextures;
    // Coordinates in the current zone (array-like).
    private final Vector2 mPosition;

    public CellV(ZoneM zone, PlayerM player, float i, float j, TextureRegion[] textures)
    {
        // Get the model.
        mZone = zone;
        mPlayer = player;
        // Frames.
        mTextures = textures;
        // Position in the zone.
        mPosition = new Vector2(i, j);
    }

    public void render(float delta, boolean highlight)
    {
        // Get coordinates.
        int playerZ = mPlayer.getZone();
        int playerI = mPlayer.getI();
        int playerJ = mPlayer.getJ();
        int zoneZ = mZone.getPositionInMap();
        int zoneI = (int) mPosition.x;
        int zoneJ = (int) mPosition.y;
        // Map them in the map grid.
        int playerIinMap = (playerZ / MapM.ZONE_LINE) * ZoneM.SIZE + playerI;
        int zoneIinMap = (zoneZ / MapM.ZONE_LINE) * ZoneM.SIZE + zoneI;
        int playerJinMap = (playerZ % MapM.ZONE_LINE) * ZoneM.SIZE + playerJ;
        int zoneJinMap = (zoneZ % MapM.ZONE_LINE) * ZoneM.SIZE + zoneJ;
        // Get difference between them (player is always in 0, 0).
        int i = zoneIinMap - playerIinMap;
        int j = zoneJinMap - playerJinMap;

        // Draw.
        Main.mBatch.draw(mTextures[0],
                getXPosition(i, j),
                getYPosition(i, j),
                TILE_SIZE,
                TILE_SIZE);

        if (highlight)
        {
            // Highlight the player cells.
            Main.mBatch.draw(mTextures[1],
                    getXPosition(i, j),
                    getYPosition(i, j),
                    TILE_SIZE,
                    TILE_SIZE);
        }
    }

    /**
     * Return the isometric X position of the cell in the world,
     * depending on its coordinates in the map and the player's.
     * @param i _
     * @param j difference between the player's position and cell's one.
     */
    private float getXPosition(int i, int j)
    {
        // Space between tiles for grid.
        float space = (i - j) * TILE_SIZE / 10f;
        // To center.
        float center = -TILE_SIZE / 2f;

        return (i - j) * (TILE_SIZE / 2f) + space + center;
    }

    /**
     * Return the isometric Y position of the cell in the world,
     * depending on its coordinates in the map and the player's.
     * @param i _
     * @param j difference between the player's position and cell's one.
     */
    private float getYPosition(int i, int j)
    {
        // Space between tiles for grid.
        float space = (i + j) * TILE_SIZE / 10f;
        // To center.
        float center = -TILE_SIZE / 2f;
        // The tile graphical elevation.
        float elevation = -(i + j) * TILE_SIZE / 3.75f;

        return (i + j) * (TILE_SIZE / 2f) + elevation + space + center;
    }
}