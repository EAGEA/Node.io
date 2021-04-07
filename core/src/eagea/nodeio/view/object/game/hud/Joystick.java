package eagea.nodeio.view.object.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.view.View;

/**
 * Button to control the character.
 */
public class Joystick
{
    private final float ICON_WIDTH = 5f;
    private final float ICON_HEIGHT = 5f;
    // On touch animation.
    private final float ANIMATION_TIME = 0.1f;
    // Delta error when clicking on button, on the x and y axis.
    private final float TOUCH_DELTA = 0.5f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icon.
    private final TextureRegion[] mTextures;
    // Animation.
    private boolean[] mIsAnimated;
    private float[] mTimeSinceLastRender;

    public Joystick(View view)
    {
        mView = view;
        mPosition = new Vector2(- ICON_WIDTH / 2f, 0f);
        // Load textures.
        mTextures = new TextureRegion[5];
        mTextures[0] = GameScreen.mHUDAtlas.findRegion("joystick");
        mTextures[1] = GameScreen.mHUDAtlas.findRegion("joystick_on_touch_left");
        mTextures[2] = GameScreen.mHUDAtlas.findRegion("joystick_on_touch_right");
        mTextures[3] = GameScreen.mHUDAtlas.findRegion("joystick_on_touch_up");
        mTextures[4] = GameScreen.mHUDAtlas.findRegion("joystick_on_touch_down");
        // Animation.
        mIsAnimated = new boolean[] { false, false, false, false };
        mTimeSinceLastRender = new float[] { 0f, 0f, 0f, 0f };
    }

    public void render(float delta)
    {
        updatePosition();
        updateAnimation(delta);
        // Draw.
        Main.mBatch.draw(mTextures[0],
                mPosition.x,
                mPosition.y,
                ICON_WIDTH, ICON_HEIGHT);
        // Check if one animated.
        for (int i = 0; i < 4; i ++)
        {
            if (mIsAnimated[i])
            {
                Main.mBatch.draw(mTextures[i + 1],
                        mPosition.x,
                        mPosition.y,
                        ICON_WIDTH, ICON_HEIGHT);
            }
        }
    }

    private void updatePosition()
    {
        Vector2 world = Main.mViewPortGame.unproject(
                new Vector2(0, Gdx.app.getGraphics().getHeight()));
        mPosition.x = world.x + 1.5f;
        mPosition.y = world.y + 5.5f - ICON_HEIGHT / 2f;
    }

    private void updateAnimation(float delta)
    {
        for (int i = 0; i < 4; i ++)
        {
            if (mIsAnimated[i])
            {
                mTimeSinceLastRender[i] += delta;

                if (mTimeSinceLastRender[i] >= ANIMATION_TIME)
                {
                    mTimeSinceLastRender[i] = 0f;
                    mIsAnimated[i] = false;
                }
            }
        }
    }

    public boolean isTouched(Vector2 position)
    {
        if (mPosition.x - TOUCH_DELTA <= position.x
                && position.x <= mPosition.x + ICON_WIDTH / 3f
                && mPosition.y <= position.y && position.y <= mPosition.y + ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click joystick left");
            mView.getModel().askForMove(PlayerM.Event.LEFT);
            mIsAnimated[0] = true;
            return true;
        }
        else if (mPosition.x + 2 * ICON_WIDTH / 3f <= position.x
                && position.x <= mPosition.x + ICON_WIDTH + TOUCH_DELTA
                && mPosition.y <= position.y && position.y <= mPosition.y + ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click joystick right");
            mView.getModel().askForMove(PlayerM.Event.RIGHT);
            mIsAnimated[1] = true;
            return true;
        }
        else if (mPosition.y + 2 * ICON_HEIGHT / 3f <= position.y
                && position.y <= mPosition.y + ICON_HEIGHT + TOUCH_DELTA
                && mPosition.x <= position.x && position.x <= mPosition.x + ICON_WIDTH)
        {
            System.out.println("[DEBUG]: on click joystick up");
            mView.getModel().askForMove(PlayerM.Event.UP);
            mIsAnimated[2] = true;
            return true;
        }
        else if (mPosition.y - TOUCH_DELTA <= position.y
                && position.y <= mPosition.y + ICON_HEIGHT / 3f
                && mPosition.x <= position.x && position.x <= mPosition.x + ICON_WIDTH)
        {
            System.out.println("[DEBUG]: on click joystick down");
            mView.getModel().askForMove(PlayerM.Event.DOWN);
            mIsAnimated[3] = true;
            return true;
        }

        return false;
    }
}
