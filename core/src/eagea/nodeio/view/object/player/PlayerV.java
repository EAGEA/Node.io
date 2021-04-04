package eagea.nodeio.view.object.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Observable;
import java.util.Observer;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.view.object.map.CellV;

/**
 * The player representation.
 */
public class PlayerV implements Observer
{
    public static final float WIDTH = 2f;
    public static final float HEIGHT = 2f;

    private final int FRAMES_PER_ANIMATION = 4;
    private final float TIME_PER_FRAME = 0.04f;

    // Model.
    private final PlayerM mPlayer;
    // Frames.
    private TextureRegion[] mLeftAnimation;
    private TextureRegion[] mRightAnimation;
    private TextureRegion[] mUpAnimation;
    private TextureRegion[] mDownAnimation;
    private int mFrame;
    private float mTimeSinceLastRender;
    private boolean mIsAnimated;
    // Position in the world.
    private final Vector2 mCoordinates;
    // Current orientation.
    private PlayerM.Orientation mOrientation;

    public PlayerV(PlayerM player, String color)
    {
        // Get the model and observe it.
        mPlayer = player;
        mPlayer.addObserver(this);

        loadTextures(color);

        mFrame = 0;
        mTimeSinceLastRender = 0f;
        mOrientation = PlayerM.Orientation.LEFT;
        // Always the same position.
        mCoordinates = new Vector2(-WIDTH / 2f, -HEIGHT / 2f + CellV.TILE_SIZE / 2f);
    }

    public void render(float delta)
    {
        TextureRegion toDraw = null;
        // Get the frame to draw.
        switch (mOrientation)
        {
            case LEFT: toDraw = mLeftAnimation[mFrame]; break;
            case RIGHT: toDraw = mRightAnimation[mFrame]; break;
            case UP: toDraw = mUpAnimation[mFrame]; break;
            case DOWN: toDraw = mDownAnimation[mFrame]; break;

        }
        // Draw.
        Main.mBatch.draw(toDraw, mCoordinates.x, mCoordinates.y, WIDTH, HEIGHT);
        // Change frame?
        if (mIsAnimated)
        {
            mTimeSinceLastRender += delta;

            if (mTimeSinceLastRender >= TIME_PER_FRAME)
            {
                mTimeSinceLastRender = 0f;

                if (mFrame == FRAMES_PER_ANIMATION -1)
                {
                    // Animation finished.
                    mIsAnimated = false;
                    mFrame = 0;
                }
                else
                {
                    mFrame ++;
                }
            }
        }
    }

    @Override
    public void update(Observable observable, Object o)
    {
        if (observable == mPlayer)
        {
            // Player has moved; start the move animation.
            mOrientation = (PlayerM.Orientation) o;
            mFrame = 1;
            mIsAnimated = true;
        }
    }

    private void loadTextures(String color)
    {
        mLeftAnimation = new TextureRegion[FRAMES_PER_ANIMATION];
        mRightAnimation = new TextureRegion[FRAMES_PER_ANIMATION];
        mUpAnimation = new TextureRegion[FRAMES_PER_ANIMATION];
        mDownAnimation = new TextureRegion[FRAMES_PER_ANIMATION];

        for (int i = 0; i < FRAMES_PER_ANIMATION; i ++)
        {
            mLeftAnimation[i] = GameScreen.mCharactersAtlas.findRegion(color + "_left", i);
            mRightAnimation[i] = GameScreen.mCharactersAtlas.findRegion(color + "_right", i);
            mUpAnimation[i] = GameScreen.mCharactersAtlas.findRegion(color + "_up", i);
            mDownAnimation[i] = GameScreen.mCharactersAtlas.findRegion(color + "_down", i);
        }
    }
}