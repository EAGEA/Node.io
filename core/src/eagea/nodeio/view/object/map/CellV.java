package eagea.nodeio.view.object.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Observable;
import java.util.Observer;

import eagea.nodeio.Main;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.view.View;

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
        Vector2 coord = View.getCoordinates(
                new Vector3(mPlayer.getI(), mPlayer.getJ(), mPlayer.getZone()),
                new Vector3(mPosition, mZone.getPositionInMap()));

        // Draw.
        Main.mBatch.draw(mTextures[0], coord.x, coord.y, TILE_SIZE, TILE_SIZE);

        if (highlight)
        {
            // Highlight the player cells.
            Main.mBatch.draw(mTextures[1], coord.x, coord.y, TILE_SIZE, TILE_SIZE);
        }
    }
}