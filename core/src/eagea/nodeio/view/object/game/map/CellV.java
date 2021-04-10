package eagea.nodeio.view.object.game.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import eagea.nodeio.Main;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.view.View;

/**
 * A cell of a zone render with isometric projection.
 */
public class CellV
{
    public static final float TILE_SIZE = 2f;
    public static final float BUSH_SIZE = 0.85f;

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
        Vector2 coord = View.getCoordinates(
                new Vector3(mPlayer.getI(), mPlayer.getJ(), mPlayer.getZone()),
                new Vector3(mPosition, mZone.getPositionInMap()));

        // Switch the type of cell.
        switch (mZone.getCells()[(int) mPosition.x][(int) mPosition.y].getType())
        {
            case EMPTY:
                // Draw.
                Main.mBatch.draw(mTextures[0], coord.x, coord.y, TILE_SIZE, TILE_SIZE);

                if (highlight)
                {
                    // Highlight the player cells.
                    Main.mBatch.draw(mTextures[1], coord.x, coord.y, TILE_SIZE, TILE_SIZE);
                }
                break;
            case VOID:
                break;
            case BUSH:
                // Draw.
                Main.mBatch.draw(mTextures[0], coord.x, coord.y, TILE_SIZE, TILE_SIZE);
                Main.mBatch.draw(mTextures[2],
                        coord.x + TILE_SIZE / 2f - BUSH_SIZE / 2f,
                        coord.y + TILE_SIZE - TILE_SIZE / 4f - BUSH_SIZE / 2f,
                        BUSH_SIZE, BUSH_SIZE);

                if (highlight)
                {
                    // Highlight the player cells.
                    Main.mBatch.draw(mTextures[1], coord.x, coord.y, TILE_SIZE, TILE_SIZE);
                }
                break;
        }
    }
}