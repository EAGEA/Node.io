package eagea.nodeio.view.object.game.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.model.Model;
import eagea.nodeio.view.View;

/**
 * Message shown when user got caught.
 */
public class CaughtBox
{
    private final float ICON_WIDTH = 6f;
    private final float ICON_HEIGHT = 7.1f;

    private final float SENTENCE_WIDTH = 8.2f;
    private final float SENTENCE_HEIGHT = 2f;
    // Sentence blink animation.
    private final float ANIMATION_TIME = 0.5f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icon.
    private final TextureRegion[] mTextures;
    // Animation.
    private boolean mIsShown;
    private float mTimeSinceLastRender;

    public CaughtBox(View view)
    {
        mView = view;
        mPosition = new Vector2(- ICON_WIDTH / 2f, - ICON_HEIGHT / 2f);
        // Load textures.
        mTextures = new TextureRegion[3];
        mTextures[0] = GameScreen.mHUDAtlas.findRegion("caught_bg");
        mTextures[1] = GameScreen.mHUDAtlas.findRegion("caught");
        mTextures[2] = GameScreen.mHUDAtlas.findRegion("caught_menu");
        // Animation.
        mIsShown = true;
        mTimeSinceLastRender = 0f;
    }

    public void render(float delta)
    {
        // Check if player got caught.
        if (mView.getModel().getState() == Model.State.CAUGHT)
        {
            updateAnimation(delta);
            // Draw.
            Main.mBatch.draw(mTextures[0],
                    -Main.WORLD_WIDTH / 2f, -Main.WORLD_HEIGHT / 2f,
                    Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
            Main.mBatch.draw(mTextures[1],
                    -ICON_WIDTH / 2f, -ICON_HEIGHT / 2f,
                    ICON_WIDTH, ICON_HEIGHT);
            // Blink animation:
            if (mIsShown)
            {
                Main.mBatch.draw(mTextures[2],
                        -SENTENCE_WIDTH / 2f, -SENTENCE_HEIGHT - ICON_HEIGHT,
                        SENTENCE_WIDTH, SENTENCE_HEIGHT);
            }
        }
    }

    private void updateAnimation(float delta)
    {
        mTimeSinceLastRender += delta;

        if (mTimeSinceLastRender >= ANIMATION_TIME)
        {
            mTimeSinceLastRender = 0f;
            mIsShown = ! mIsShown;
        }
    }

    public boolean isTouched(Vector2 position)
    {
        // Check if player got caught.
        if (mView.getModel().getState() == Model.State.CAUGHT)
        {
            // Whole screen touchable in this state.
            System.out.println("[DEBUG]: on click caught");
            GameScreen.playButtonSound();
            GameScreen.stopGameMusic();
            GameScreen.startMenuMusic();
            // Go to menu.
            mView.getModel().goToMenu();
            return true;
        }

        return false;
    }
}