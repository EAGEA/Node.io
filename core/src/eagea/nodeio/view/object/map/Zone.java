package eagea.nodeio.view.object.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import eagea.nodeio.view.object.Object;

/**
 * A zone of the game map. Associated to a node (a player).
 */
public class Zone extends Object
{
    private final Cell[][] mCells;

    public Zone(float x, float y, float width, float height, TextureRegion... textures)
    {
        super(x, y, width, height, textures);

        mCells = new Cell[30][30];

        for (int i = 0 ; i < 30 ; i ++)
        {
            for (int j = 0; j < 30; j ++)
            {
                mCells[i][j] = new Cell(mPosition.x, mPosition.y, i, j, textures[0]);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch)
    {
        for (int i = 29 ; i >= 0 ; i --)
        {
            for (int j = 29; j >= 0; j --)
            {
                mCells[i][j].render(batch);
            }
        }
    }

    @Override
    public void update(float delta)
    {
        for (int i = 0 ; i < 4 ; i ++)
        {
            for (int j = 0; j < 3; j ++)
            {
                mCells[i][j].update(delta);
            }
        }
    }
}
