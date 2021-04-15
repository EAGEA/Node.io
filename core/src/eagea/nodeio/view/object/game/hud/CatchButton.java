package eagea.nodeio.view.object.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.Screen;
import eagea.nodeio.Main;
import eagea.nodeio.view.View;

/**
 * Button to catch characters.
 */
public class CatchButton
{
    private final float ICON_WIDTH = 3f;
    private final float ICON_HEIGHT = 3f;
    // On touch animation.
    private final float ANIMATION_TIME = 0.2f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icon.
    private final TextureRegion[] mTexture;
    // Animation.
    private boolean mIsAnimated;
    private float mTimeSinceLastRender;

    public CatchButton(View view)
    {
        mView = view;
        mPosition = new Vector2();
        mTexture = new TextureRegion[2];
        // Load frames.
        mTexture[0] = Screen.mHUDAtlas.findRegion("catch");
        mTexture[1] = Screen.mHUDAtlas.findRegion("catch_on_touch");
        // Animation.
        mIsAnimated = false;
        mTimeSinceLastRender = 0f;
    }

    public void render(float delta)
    {
        updatePosition();
        updateAnimation(delta);
        // Draw.
        Main.mBatch.draw(mIsAnimated ? mTexture[1] : mTexture[0],
                mPosition.x,
                mPosition.y,
                ICON_WIDTH, ICON_HEIGHT);
    }

    private void updatePosition()
    {
        Vector2 world = Main.mViewPortGame.unproject(
                new Vector2(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight()));
        mPosition.x = world.x - 1.5f - ICON_WIDTH;
        mPosition.y = world.y + 5.5f - ICON_HEIGHT / 2f;
    }

    private void updateAnimation(float delta)
    {
        if (mIsAnimated)
        {
            mTimeSinceLastRender += delta;

            if (mTimeSinceLastRender >= ANIMATION_TIME)
            {
                mTimeSinceLastRender = 0f;
                mIsAnimated = false;
            }
        }
    }

    public boolean isTouched(Vector2 position)
    {
        if (mPosition.x <= position.x && position.x <= mPosition.x + ICON_WIDTH
                && mPosition.y <= position.y && position.y <= mPosition.y + ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click catch");
            mView.getModel().askForCatch();
            mIsAnimated = true;
            return true;
        }

        return false;
    }
}