package eagea.nodeio.view.object.hud;

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
    private final float ICON_WIDTH = 4.5f;
    private final float ICON_HEIGHT = 4.5f;

    // Delta error when clicking on button, on the x and y axis.
    private final float TOUCH_DELTA = 0.5f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icon.
    private final TextureRegion mTexture;

    public Joystick(View view)
    {
        mView = view;
        mPosition = new Vector2(- ICON_WIDTH / 2f, 0f);
        mTexture = GameScreen.mHUDAtlas.findRegion("joystick");
    }

    public void render(float delta)
    {
        updatePosition();
        // Draw.
        Main.mBatch.draw(mTexture,
                mPosition.x,
                mPosition.y,
                ICON_WIDTH, ICON_HEIGHT);
    }

    private void updatePosition()
    {
        Vector2 world = Main.mViewPortGame.unproject(
                new Vector2(0, Gdx.app.getGraphics().getHeight()));
        mPosition.y = world.y + 0.75f * ICON_HEIGHT;
    }

    public boolean isTouched(Vector2 position)
    {
        if (mPosition.x - TOUCH_DELTA <= position.x
                && position.x <= mPosition.x + ICON_WIDTH / 3f)
        {
            System.out.println("[DEBUG]: on click joystick left");
            mView.getModel().askForMove(PlayerM.Event.LEFT);
            return true;
        }
        else if (mPosition.x + 2 * ICON_WIDTH / 3f <= position.x
                && position.x <= mPosition.x + ICON_WIDTH + TOUCH_DELTA)
        {
            System.out.println("[DEBUG]: on click joystick right");
            mView.getModel().askForMove(PlayerM.Event.RIGHT);
            return true;
        }
        else if (mPosition.y - TOUCH_DELTA <= position.y
                && position.y <= mPosition.y + ICON_HEIGHT / 3f)
        {
            System.out.println("[DEBUG]: on click joystick down");
            mView.getModel().askForMove(PlayerM.Event.DOWN);
            return true;
        }
        else if (mPosition.y + 2 * ICON_HEIGHT / 3f <= position.y
                && position.y <= mPosition.y + ICON_HEIGHT + TOUCH_DELTA)
        {
            System.out.println("[DEBUG]: on click joystick up");
            mView.getModel().askForMove(PlayerM.Event.UP);
            return true;
        }
        else if (mPosition.x + ICON_WIDTH / 3f <= position.x
                && position.x <= mPosition.x + 2 * ICON_WIDTH / 3f
                && mPosition.y + ICON_HEIGHT / 3f <= position.y
                && position.y <= mPosition.y + 2 * ICON_HEIGHT/ 3f)
        {
            System.out.println("[DEBUG]: on click joystick catch");
            mView.getModel().askForCatch();
            return true;
        }

        return false;
    }
}
