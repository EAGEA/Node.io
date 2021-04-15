package eagea.nodeio.view.object.game.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import eagea.nodeio.Screen;
import eagea.nodeio.Main;
import eagea.nodeio.view.View;

/**
 * The score i.e. the number of player left.
 */
public class Score
{
    private final float SPACE_ICON_DIGIT = 0.25f;

    private final float ICON_WIDTH = 1f;
    private final float ICON_HEIGHT = 1f;

    private final float DIGIT_WIDTH = 0.5f;
    private final float DIGIT_HEIGHT = 1f;

    // Parent.
    private final View mView;
    // Position.
    private final Vector2 mPosition;
    // Icon.
    private final TextureRegion mTexture;

    public Score(View view)
    {
        mView = view;
        mPosition = new Vector2();
        mTexture = Screen.mHUDAtlas.findRegion("head");
    }

    public void render(float delta)
    {
        int score = mView.getPlayers().getNbPlayers();
        int nbDigits = String.valueOf(score).length() ;

        updatePosition(nbDigits);
        // Draw the "head" icon.
        Main.mBatch.draw(mTexture,
                mPosition.x, mPosition.y,
                ICON_WIDTH, ICON_HEIGHT);
        // Draw each digit.
        for (int i = nbDigits - 1 ; i >= 0 ; i --)
        {
            // Get the current digit.
            float coef = (float) Math.pow(10, i) ;
            int digit = (int) ((float) score / coef) ;
            score -= digit * coef ;
            // Its texture.
            TextureRegion digitTexture = Screen.mHUDAtlas.findRegion(
                    "digit_wendy_outlined_white", digit) ;
            // Draw.
            Main.mBatch.draw(digitTexture,
                    mPosition.x + ICON_WIDTH + SPACE_ICON_DIGIT + DIGIT_WIDTH * (nbDigits - i - 1),
                    mPosition.y,
                    DIGIT_WIDTH, DIGIT_HEIGHT);
        }

    }

    private void updatePosition(int nbDigits)
    {
        Vector2 world = Main.mViewPortGame.unproject(new Vector2(0, 0));
        mPosition.x = (-nbDigits * DIGIT_WIDTH - ICON_WIDTH - SPACE_ICON_DIGIT) / 2f;
        mPosition.y = world.y - 2 * ICON_HEIGHT;
    }
}
