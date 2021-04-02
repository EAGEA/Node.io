package eagea.nodeio.view.object.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import eagea.nodeio.view.object.Object;

/**
 * The game map on which players are moving.
 */
public class Map extends Object
{
    private final Zone[] mZones;

    public Map(float x, float y, float width, float height, TextureRegion... textures)
    {
        super(x, y, width, height, textures);

        mZones = new Zone[1];

        mZones[0] = new Zone(x, y, width, height, textures);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        for (int i = 0 ; i < 1 ; i ++)
        {
            mZones[i].render(batch);
        }
    }

    @Override
    public void update(float delta)
    {
        for (int i = 0 ; i < 1 ; i ++)
        {
            mZones[i].update(delta);
        }
    }
}
