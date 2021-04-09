package eagea.nodeio.view.object.game.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import eagea.nodeio.GameScreen;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * A zone of the game map.
 */
public class ZoneV
{
    // Model.
    private final ZoneM mZone;
    private final PlayerM mPlayer;
    // Zone's cells.
    private final CellV[][] mCells;

    public ZoneV(ZoneM zone, PlayerM player)
    {
        // Get the model and observe it.
        mPlayer = player;
        mZone = zone;
        // Load the cells.
        mCells = new CellV[ZoneM.SIZE][ZoneM.SIZE];
        // Texture common to all the cell animation.
        TextureRegion texture = GameScreen.mEnvironmentAtlas.findRegion("highlighted");

        for (int i = 0; i < ZoneM.SIZE; i ++)
        {
            for (int j = 0; j < ZoneM.SIZE; j ++)
            {
                mCells[i][j] = new CellV(mZone, mPlayer, i, j,
                        new TextureRegion[]{ getCellTexture(), texture, getCellBushTexture() });
            }
        }
    }

    public void render(float delta, boolean highlighted)
    {
        // Reverse render order because of isometric rendering.
        for (int i = ZoneM.SIZE - 1; i >= 0 ; i --)
        {
            for (int j = ZoneM.SIZE - 1; j >= 0 ; j --)
            {
                mCells[i][j].render(delta, highlighted);
            }
        }
    }

    /**
     * Return a random texture for a cell of this zone.
     */
    private TextureRegion getCellTexture()
    {
        String name = mZone.getType().name().toLowerCase();

        return GameScreen.mEnvironmentAtlas.findRegion(name, (int) (Math.random() * 2d) + 1);
    }

    /**
     * Return the bush texture for a cell of this zone.
     */
    private TextureRegion getCellBushTexture()
    {
        String name = mZone.getType().name().toLowerCase();

        return GameScreen.mEnvironmentAtlas.findRegion("bush_" + name);
    }

    public ZoneM getZone()
    {
        return mZone;
    }
}