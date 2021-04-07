package eagea.nodeio.view.object.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.view.View;

/**
 * Buttons to exit game.
 */
public class ExitButton
{
    private final float ICON_WIDTH = 1f;
    private final float ICON_HEIGHT = 1f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icon.
    private final TextureRegion mTexture;

    public ExitButton(View view)
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
        mPosition.x = world.x - 2f * ICON_WIDTH;
        mPosition.y = world.y - 2f * ICON_HEIGHT;
    }

    public boolean isTouched(Vector2 position)
    {
        if (mPosition.x <= position.x && position.x <= mPosition.x + ICON_WIDTH
                && mPosition.y <= position.y && position.y <= mPosition.y + ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click exit");
            mView.getModel().askForDisconnection();
            return true;
        }

        return false;
    }
}