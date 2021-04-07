package eagea.nodeio.view.object.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.view.View;

/**
 * Buttons to speak.
 */
public class SpeakButtons
{
    private final float SPACE_HORIZONTAL_ICONS = 0.75f;

    private final float ICON_WIDTH = 2f;
    private final float ICON_HEIGHT = 1.3f;
    // Delta error when clicking on button, on the y axis.
    private final float TOUCH_DELTA = 0.5f;
    // On touch animation.
    private final float ANIMATION_TIME = 0.2f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icons.
    private final TextureRegion[] mHelloTextures;
    private final TextureRegion[] mLooserTextures;
    private final TextureRegion[] mByeTextures;
    // Animations.
    private boolean mHelloAnimated;
    private float mHelloDeltaTime;
    private boolean mLooserAnimated;
    private float mLooserDeltaTime;
    private boolean mByeAnimated;
    private float mByeDeltaTime;

    public SpeakButtons(View view)
    {
        mView = view;
        mPosition = new Vector2(
                (-3 * ICON_WIDTH -2 * SPACE_HORIZONTAL_ICONS) / 2f, 0f);
        // Load textures
        mHelloTextures = new TextureRegion[2];
        mHelloTextures[0] = GameScreen.mHUDAtlas.findRegion("hello");
        mHelloTextures[1] = GameScreen.mHUDAtlas.findRegion("hello_on_touch");
        mLooserTextures = new TextureRegion[2];
        mLooserTextures[0] = GameScreen.mHUDAtlas.findRegion("looser");
        mLooserTextures[1] = GameScreen.mHUDAtlas.findRegion("looser_on_touch");
        mByeTextures = new TextureRegion[2];
        mByeTextures[0] = GameScreen.mHUDAtlas.findRegion("bye");
        mByeTextures[1] = GameScreen.mHUDAtlas.findRegion("bye_on_touch");
        // Animations.
        mHelloAnimated = false;
        mHelloDeltaTime = 0f;
        mLooserAnimated = false;
        mLooserDeltaTime = 0f;
        mByeAnimated = false;
        mByeDeltaTime = 0f;
    }

    public void render(float delta)
    {
        updatePosition();
        updateAnimation(delta);
        // Draw each icon.
        Main.mBatch.draw(mHelloAnimated ? mHelloTextures[1] : mHelloTextures[0],
                mPosition.x + 0 * (ICON_WIDTH + SPACE_HORIZONTAL_ICONS),
                mPosition.y,
                ICON_WIDTH, ICON_HEIGHT);
        Main.mBatch.draw(mLooserAnimated ? mLooserTextures[1] : mLooserTextures[0],
                mPosition.x + 1 * (ICON_WIDTH + SPACE_HORIZONTAL_ICONS),
                mPosition.y,
                ICON_WIDTH, ICON_HEIGHT);
        Main.mBatch.draw(mByeAnimated ? mByeTextures[1] : mByeTextures[0],
                mPosition.x + 2 * (ICON_WIDTH + SPACE_HORIZONTAL_ICONS),
                mPosition.y,
                ICON_WIDTH, ICON_HEIGHT);
    }

    private void updatePosition()
    {
        Vector2 world = Main.mViewPortGame.unproject(new Vector2(
                0, Gdx.app.getGraphics().getHeight()));
        mPosition.y = world.y + ICON_HEIGHT / 2f;
    }

    private void updateAnimation(float delta)
    {
        if (mHelloAnimated)
        {
            mHelloDeltaTime += delta;

            if (mHelloDeltaTime >= ANIMATION_TIME)
            {
                mHelloDeltaTime = 0f;
                mHelloAnimated = false;
            }
        }
        if (mLooserAnimated)
        {
            mLooserDeltaTime += delta;

            if (mLooserDeltaTime >= ANIMATION_TIME)
            {
                mLooserDeltaTime = 0f;
                mLooserAnimated = false;
            }
        }
        if (mByeAnimated)
        {
            mByeDeltaTime += delta;

            if (mByeDeltaTime >= ANIMATION_TIME)
            {
                mByeDeltaTime = 0f;
                mByeAnimated = false;
            }
        }
    }

    public boolean isTouched(Vector2 position)
    {
        if (mPosition.x <= position.x && position.x <= mPosition.x + ICON_WIDTH
                && mPosition.y - TOUCH_DELTA <= position.y
                && position.y <= mPosition.y + TOUCH_DELTA + ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click speak hello");
            mView.getModel().askForSpeak(PlayerM.Speak.HELLO);
            mHelloAnimated = true;
            return true;
        }
        else if (mPosition.x + ICON_WIDTH + SPACE_HORIZONTAL_ICONS <= position.x
                && position.x <= mPosition.x + ICON_WIDTH + SPACE_HORIZONTAL_ICONS + ICON_WIDTH
                && mPosition.y - TOUCH_DELTA <= position.y
                && position.y <= mPosition.y + TOUCH_DELTA + ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click speak looser");
            mView.getModel().askForSpeak(PlayerM.Speak.LOOSER);
            mLooserAnimated = true;
            return true;
        }
        else if (mPosition.x + 2f * (ICON_WIDTH + SPACE_HORIZONTAL_ICONS) <= position.x
                && position.x <= mPosition.x + 2f * (ICON_WIDTH + SPACE_HORIZONTAL_ICONS) + ICON_WIDTH
                && mPosition.y - TOUCH_DELTA <= position.y
                && position.y <= mPosition.y + TOUCH_DELTA+ ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click speak bye");
            mView.getModel().askForSpeak(PlayerM.Speak.BYE);
            mByeAnimated = true;
            return true;
        }

        return false;
    }
}