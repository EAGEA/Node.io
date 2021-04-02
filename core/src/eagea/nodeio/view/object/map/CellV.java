package eagea.nodeio.view.object.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.Main;

/**
 * A cell of a zone render with isometric projection.
 */
public class CellV
{
    public static final float TILE_SIZE = 4f;

    // Current frame.
    private final TextureRegion mTexture;
    // Positions in the zone.
    private final Vector2 mPosition;
    // Current size.
    private float mSize;

    public CellV(float i, float j, TextureRegion texture)
    {
        mTexture = texture;
        mPosition = new Vector2(i, j);
        mSize = TILE_SIZE;
    }

    public void render()
    {
        // Draw.
        Main.mBatch.draw(mTexture, getXPosition(), getYPosition(), mSize, mSize);
    }

    public void update(float delta)
    {
        // To keep same size even when resizing.
        //mSize = TILE_SIZE / (Gdx.graphics.getWidth() / Main.mCamera.viewportWidth);
    }

    /**
     * Return the isometric X position of the cell, depending on its
     * coordinates in the map.
     */
    private float getXPosition()
    {
        // Space between tiles for grid.
        float space = (mPosition.x - mPosition.y) * mSize / 10f;

        return  -50 + (mPosition.x - mPosition.y) * (mSize / 2f) + space;
    }

    /**
     * Return the isometric Y position of the cell, depending on its
     * coordinates in the map.
     */
    private float getYPosition()
    {
        // The tile graphical elevation.
        float elevation = -(mPosition.x + mPosition.y) * mSize / 3.75f;
        // Space between tiles for grid.
        float space = (mPosition.x + mPosition.y) * mSize / 10f;

        return  -50 + (mPosition.x + mPosition.y) * (mSize / 2f) + elevation + space;
    }
}