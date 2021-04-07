package eagea.nodeio.view.object.game.background;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;

/**
 * A layer used by the parallax background.
 */
public class Layer
{
    private final float COEF = 2f;
    private final float WIDTH = Main.WORLD_WIDTH / COEF;
    private final float HEIGHT = Main.WORLD_HEIGHT / COEF;

    // Frame.
    private final TextureRegion mTexture;
    // Translation.
    private final Vector2 mCoordinates;
    private final float mSpeed;

    public Layer(int i, float speed)
    {
        mTexture = GameScreen.mEnvironmentAtlas.findRegion("background", i);
        mSpeed = speed;
        mCoordinates = new Vector2(-Main.WORLD_WIDTH / 2f, -Main.WORLD_HEIGHT / 2f);
    }

    public void render(float delta)
    {
        translate(delta);
        // Draw.
        for (int i = -1; i < COEF; i ++)
        {
            for (int j = 0; j < COEF; j ++)
            {
                Main.mBatch.draw(mTexture, mCoordinates.x + i * WIDTH,
                        mCoordinates.y + j * HEIGHT,
                        WIDTH, HEIGHT);
            }
        }
    }

    private void translate(float delta)
    {
        mCoordinates.x += delta * mSpeed;

        if (mCoordinates.x > -Main.WORLD_WIDTH / 2f + WIDTH)
        {
            mCoordinates.x = -Main.WORLD_WIDTH / 2f;
        }
    }
}