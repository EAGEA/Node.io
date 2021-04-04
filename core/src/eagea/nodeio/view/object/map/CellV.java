package eagea.nodeio.view.object.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.Main;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;

/**
 * A cell of a zone render with isometric projection.
 */
public class CellV
{
    public static final float TILE_SIZE = 2f;

    // Parent.
    private final ZoneM mZone;
    // Current frame.
    private final TextureRegion[] mTextures;
    // Position relative to the player, in number of cells.
    private final Vector2 mPosition;

    public CellV(ZoneM zone, float i, float j, TextureRegion[] textures)
    {
        mZone = zone;
        mTextures = textures;
        // From zone indexes to map ones.
        int deltaI = (zone.getPositionInMap() / MapM.ZONE_LINE) * ZoneM.SIZE;
        int deltaJ = (zone.getPositionInMap() % MapM.ZONE_LINE) * ZoneM.SIZE;
        mPosition = new Vector2(i + deltaI, j + deltaJ);
    }

    public void render(float delta, boolean highlight)
    {
        // Draw.
        Main.mBatch.draw(mTextures[0],
                getXPosition(),
                getYPosition(),
                TILE_SIZE,
                TILE_SIZE);

        if (highlight)
        {
            // Highlight the player cells.
            Main.mBatch.draw(mTextures[1],
                    getXPosition(),
                    getYPosition(),
                    TILE_SIZE,
                    TILE_SIZE);
        }
    }

    /**
     * Change position of each cell when player has moved of "deltaI"
     * and "deltaJ".
     */
    public void updatePosition(int deltaI, int deltaJ)
    {
        mPosition.x += deltaI;
        mPosition.y += deltaJ;
    }

    /**
     * Return the isometric X position of the cell, depending on its
     * coordinates in the map.
     */
    private float getXPosition()
    {
        // Space between tiles for grid.
        float space = (mPosition.x - mPosition.y) * TILE_SIZE / 10f;
        // To center.
        float center = -TILE_SIZE / 2f;

        return (mPosition.x - mPosition.y) * (TILE_SIZE / 2f) + space + center;
    }

    /**
     * Return the isometric Y position of the cell, depending on its
     * coordinates in the map.
     */
    private float getYPosition()
    {
        // Space between tiles for grid.
        float space = (mPosition.x + mPosition.y) * TILE_SIZE / 10f;
        // To center.
        float center = -TILE_SIZE / 2f;
        // The tile graphical elevation.
        float elevation = -(mPosition.x + mPosition.y) * TILE_SIZE / 3.75f;

        return (mPosition.x + mPosition.y) * (TILE_SIZE / 2f) + elevation + space + center;
    }
}