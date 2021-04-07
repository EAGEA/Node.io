package eagea.nodeio.view.object.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.view.View;

/**
 * Buttons to speak.
 */
public class Exit
{
    private final float ICON_WIDTH = 1f;
    private final float ICON_HEIGHT = 1f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icon.
    private final TextureRegion mTexture;

    public Exit(View view)
    {
        mView = view;
        mPosition = new Vector2();
        mTexture = GameScreen.mHUDAtlas.findRegion("exit");
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
                new Vector2(Gdx.app.getGraphics().getWidth(), 0));
        mPosition.x = world.x / 2f - 5.25f;
        mPosition.y = world.y / 2f - ICON_HEIGHT;

        if (Gdx.app.getGraphics().getWidth() < Gdx.app.getGraphics().getHeight())
        {
            // Vertical orientation.
            mPosition.y -= 1f;
        }
        else
        {
            // Horizontal orientation.
            mPosition.y -= 4f;
        }
    }

    public boolean isTouched(Vector2 position)
    {
        Vector2 screenPosition = Main.mViewPortGame.toScreenCoordinates(
                new Vector2(1, 2),
                Main.mCamera.invProjectionView);

        System.out.println(screenPosition.x + " " + screenPosition.y);
        System.out.println(position.x + " " + position.y);
        if (screenPosition.x <= position.x && position.x <= screenPosition.x
                && screenPosition.y <= position.y && position.y <= screenPosition.y)
        {
            mView.getModel().askForDisconnection();
            return true;
        }

        return false;
    }
}
