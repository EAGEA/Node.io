package eagea.nodeio.view.object.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.Screen;
import eagea.nodeio.Main;
import eagea.nodeio.model.Model;
import eagea.nodeio.view.View;

/**
 * Buttons in main menu to start game (the blinking one).
 */
public class StartButton
{
    private final float ICON_WIDTH = 10f;
    private final float ICON_HEIGHT = 1f;
    // Sentence blink animation.
    private final float ANIMATION_TIME = 0.5f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icon.
    private final TextureRegion mTexture;
    // Animation.
    private boolean mIsShown;
    private float mTimeSinceLastRender;

    public StartButton(View view)
    {
        mView = view;
        mPosition = new Vector2(-ICON_WIDTH / 2f, 0f);
        mTexture = Screen.mGUIAtlas.findRegion("start");
        // Animation.
        mIsShown = true;
        mTimeSinceLastRender = 0f;
    }

    public void render(float delta)
    {
        updateAnimation(delta);
        // Blink animation:
        if (mIsShown)
        {
            updatePosition();
            // Draw.
            Main.mBatch.draw(mTexture,
                    mPosition.x, mPosition.y,
                    ICON_WIDTH, ICON_HEIGHT);
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

    private void updatePosition()
    {
        Vector2 world = Main.mViewPortGame.unproject(new Vector2(0f,
                Gdx.app.getGraphics().getHeight()));
        mPosition.y = world.y + 1f;
    }

    public boolean isTouched(Vector2 position)
    {
        if (mView.getModel().getState() == Model.State.MENU)
        {
            // Whole screen touchable in this state.
            System.out.println("[DEBUG]: on click start");
            Screen.playButtonSound();
            Screen.stopMenuMusic();
            Screen.startGameMusic();
            // Start the game
            mView.getModel().goToGame();
            return true;
        }

        return false;
    }
}