package eagea.nodeio.view.object.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.view.View;

/**
 * Buttons to exit game.
 */
public class Logo
{
    private final float ICON_WIDTH = 12f;
    private final float ICON_HEIGHT = 14f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icon.
    private final TextureRegion mTexture;

    public Logo(View view)
    {
        mView = view;
        mPosition = new Vector2(-ICON_WIDTH / 2f, 0f);
        mTexture = GameScreen.mGUIAtlas.findRegion("logo");
    }

    public void render(float delta)
    {
        updatePosition();
        // Draw.
        Main.mBatch.draw(mTexture,
                mPosition.x, mPosition.y,
                ICON_WIDTH, ICON_HEIGHT);
    }

    private void updatePosition()
    {
        Vector2 world = Main.mViewPortGame.unproject(new Vector2());
        mPosition.y = world.y - ICON_HEIGHT - 2f;
    }
}