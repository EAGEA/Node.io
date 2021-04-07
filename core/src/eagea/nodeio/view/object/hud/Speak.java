package eagea.nodeio.view.object.hud;

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
        mPosition = new Vector2(
                (-3 * ICON_WIDTH -2 * SPACE_HORIZONTAL_ICONS) / 2f, 0f);
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
        Vector2 world = Main.mViewPortGame.unproject(new Vector2(
                0, Gdx.app.getGraphics().getHeight()));
        mPosition.y = world.y + ICON_HEIGHT / 2f;
    }

    public boolean isTouched(Vector2 position)
    {
        if (mPosition.x <= position.x && position.x <= mPosition.x + ICON_WIDTH
                && mPosition.y <= position.y && position.y <= mPosition.y + ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click speak hello");
            mView.getModel().askForSpeak(PlayerM.Speak.HELLO);
            return true;
        }
        else if (mPosition.x + ICON_WIDTH + SPACE_HORIZONTAL_ICONS <= position.x
                && position.x <= mPosition.x + ICON_WIDTH + SPACE_HORIZONTAL_ICONS + ICON_WIDTH
                && mPosition.y <= position.y && position.y <= mPosition.y + ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click speak looser");
            mView.getModel().askForSpeak(PlayerM.Speak.LOOSER);
            return true;
        }
        else if (mPosition.x + 2f * (ICON_WIDTH + SPACE_HORIZONTAL_ICONS) <= position.x
                && position.x <= mPosition.x + 2f * (ICON_WIDTH + SPACE_HORIZONTAL_ICONS) + ICON_WIDTH
                && mPosition.y <= position.y && position.y <= mPosition.y + ICON_HEIGHT)
        {
            System.out.println("[DEBUG]: on click speak bye");
            mView.getModel().askForSpeak(PlayerM.Speak.BYE);
            return true;
        }

        return false;
    }
}