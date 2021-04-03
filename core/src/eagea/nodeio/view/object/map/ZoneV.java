package eagea.nodeio.view.object.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Observable;
import java.util.Observer;

import eagea.nodeio.GameScreen;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;

/**
 * A zone of the game map.
 */
public class ZoneV implements Observer
{
    // Model.
    private final ZoneM mZone;
    // Zone's cells.
    private final CellV[][] mCells;

    public ZoneV(ZoneM zone)
    {
        // Get the model and observe it.
        mZone = zone;
        mZone.addObserver(this);
        // Load the cells.
        mCells = new CellV[ZoneM.SIZE][ZoneM.SIZE];

        for (int i = 0; i < ZoneM.SIZE; i ++)
        {
            for (int j = 0; j < ZoneM.SIZE; j ++)
            {
                mCells[i][j] = new CellV(mZone.getId(), i, j, getCellTexture());
            }
        }
    }

    public void render()
    {
        // Reverse render order because of isometric rendering.
        for (int i = ZoneM.SIZE - 1; i >= 0 ; i --)
        {
            for (int j = ZoneM.SIZE - 1; j >= 0 ; j --)
            {
                mCells[i][j].render();
            }
        }
    }

    @Override
    public void update(Observable observable, java.lang.Object o)
    {
        // Zone model has changed.
    }

    /**
     * Change position of each cell when player has moved of "deltaI"
     * and "deltaJ".
     */
    public void updatePosition(int deltaI, int deltaJ)
    {
        for (int i = 0; i < ZoneM.SIZE; i ++)
        {
            for (int j = 0; j < ZoneM.SIZE; j ++)
            {
                mCells[i][j].updatePosition(deltaI, deltaJ);
            }
        }
    }

    /**
     * Return a random texture for a cell of this zone.
     */
    private TextureRegion getCellTexture()
    {
        String name = mZone.getType().name().toLowerCase();

        return GameScreen.mMapAtlas.findRegion(name, (int) (Math.random() * 2d) + 1);
    }
}