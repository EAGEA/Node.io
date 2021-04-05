package eagea.nodeio.view.object.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    private final int FRAMES_PER_ANIMATION = 2;
    private final float TIME_PER_FRAME = 0.5f;

    // Model.
    private final ZoneM mZone;
    private final PlayerM mPlayer;
    // Zone's cells.
    private final CellV[][] mCells;
    // Cells' animation.
    private boolean mHighlighted;
    private float mTimeSinceLastRender;

    public ZoneV(ZoneM zone, PlayerM player)
    {
        // Get the model and observe it.
        mPlayer = player;
        mZone = zone;
        mZone.addObserver(this);
        // Load the cells.
        mCells = new CellV[ZoneM.SIZE][ZoneM.SIZE];
        mHighlighted = false;
        mTimeSinceLastRender = 0f;
        // Texture common to all the cell animation.
        TextureRegion texture = GameScreen.mEnvironmentAtlas.findRegion("highlighted");

        for (int i = 0; i < ZoneM.SIZE; i ++)
        {
            for (int j = 0; j < ZoneM.SIZE; j ++)
            {
                mCells[i][j] = new CellV(mZone, mPlayer, i, j,
                        new TextureRegion[]{ getCellTexture(), texture });
            }
        }
    }

    public void render(float delta)
    {
        // Check if cells should be highlighted.
        if (mZone.getOwner() == mPlayer)
        {
            mTimeSinceLastRender += delta;

            if (mTimeSinceLastRender >= TIME_PER_FRAME)
            {
                mTimeSinceLastRender = 0f;
                mHighlighted = ! mHighlighted;
            }
        }
        // Reverse render order because of isometric rendering.
        for (int i = ZoneM.SIZE - 1; i >= 0 ; i --)
        {
            for (int j = ZoneM.SIZE - 1; j >= 0 ; j --)
            {
                mCells[i][j].render(delta, mHighlighted);
            }
        }
    }

    @Override
    public void update(Observable observable, java.lang.Object o)
    {
        // Zone model has changed.
        // TODO type change
    }

    /**
     * Return a random texture for a cell of this zone.
     */
    private TextureRegion getCellTexture()
    {
        String name = mZone.getType().name().toLowerCase();

        return GameScreen.mEnvironmentAtlas.findRegion(name, (int) (Math.random() * 2d) + 1);
    }
}