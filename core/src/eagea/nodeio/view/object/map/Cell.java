package eagea.nodeio.view.object.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import eagea.nodeio.view.object.Object;

/**
 * A cell of a zone render with isometric projection.
 */
public class Cell extends Object
{
    public static final float TILE_WIDTH = 20f;
    public static final float TILE_HEIGHT = 20f;

    public Cell(float originX, float originY, float x, float y, TextureRegion... textures)
    {
        super(originX + (x - y) * TILE_WIDTH / 2f,
                originY + (x + y) * TILE_HEIGHT / 2f - y * 3f,
                TILE_WIDTH, TILE_HEIGHT, textures);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(getCurrentFrame(), mPosition.x, mPosition.y, mWidth, mHeight) ;
    }

    @Override
    public void update(float delta)
    {
    }
}