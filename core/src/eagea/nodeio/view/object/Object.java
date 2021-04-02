package eagea.nodeio.view.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Abstract class to update and render.
 */
public abstract class Object
{
    // Characteristics.
    protected final Vector2 mPosition;
    protected final Rectangle mBounds;
    protected final float mWidth;
    protected final float mHeight;
    // Textures (frames).
    private final TextureRegion[] mTextures;
    private final int mFrame;

    protected Object(float x, float y, float width, float height, TextureRegion... textures)
    {
        mPosition = new Vector2(x, y);
        mWidth = width;
        mHeight = height;
        mBounds = new Rectangle(x - width / 2f, y - height / 2f, width, height);
        mTextures = textures;
        mFrame = 0;
    }

    protected TextureRegion getCurrentFrame()
    {
        return mTextures[mFrame];
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update(float delta);
}