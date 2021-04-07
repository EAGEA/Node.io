package eagea.nodeio.view.object.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.view.View;

/**
 * Buttons to speak.
 */
public class Speak
{
    private final float SPACE_HORIZONTAL_ICONS = 0.75f;

    private final float ICON_WIDTH = 2.1f;
    private final float ICON_HEIGHT = 1.5f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icons.
    private final TextureRegion[] mTexture;

    public Speak(View view)
    {
        mView = view;
        mPosition = new Vector2((-3 * ICON_WIDTH -2 * SPACE_HORIZONTAL_ICONS) / 2f, 0f);
        // Load textures
        mTexture = new TextureRegion[3];
        mTexture[0] = GameScreen.mHUDAtlas.findRegion("hello");
        mTexture[1] = GameScreen.mHUDAtlas.findRegion("looser");
        mTexture[2] = GameScreen.mHUDAtlas.findRegion("bye");
    }

    public void render(float delta)
    {
        updatePosition();
        // Draw each icon.
        for (int i = 0; i < 3; i ++)
        {
            Main.mBatch.draw(mTexture[i],
                    mPosition.x + i * (ICON_WIDTH + SPACE_HORIZONTAL_ICONS),
                    mPosition.y,
                    ICON_WIDTH, ICON_HEIGHT);

        }
    }

    private void updatePosition()
    {
        mPosition.y = -mView.getScore().getY() - 1f;
    }
}