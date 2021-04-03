package eagea.nodeio.view.object.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.Main;
import eagea.nodeio.view.object.map.CellV;

/**
 * The player representation.
 */
public class PlayerV
{
    public static final float WIDTH = 1f;
    public static final float HEIGHT = 1f;

    // Current frame and the player color.
    private final TextureRegion mTexture;
    private final Color mColor;
    // Position in the world.
    private final Vector2 mCoordinates;

    public PlayerV(TextureRegion texture, Color color)
    {
        mTexture = texture;
        mColor = color;
        // Always the same position.
        mCoordinates = new Vector2(-WIDTH / 2f, -HEIGHT / 2f + CellV.TILE_SIZE / 2f);
    }

    public void render()
    {
        // Draw.
        Main.mBatch.setColor(mColor);
        Main.mBatch.draw(mTexture, mCoordinates.x, mCoordinates.y, WIDTH, HEIGHT);
        Main.mBatch.setColor(Color.WHITE);
    }
}
