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
    public static final float WIDTH_CHAR = 2f;
    public static final float HEIGHT_CHAR = 2f;
    public static final float WIDTH_HELLO = 2f;
    public static final float HEIGHT_HELLO = 1.39f;

    // Animation number of frames.
    private final int FRAMES_PER_ANIMATION = 4;
    // Animation seconds per frame.
    private final float TIME_PER_FRAME = 0.04f;
    // Hello seconds per frame.
    private final float TIME_SPEAK = 1f;

    // Model.
    private final PlayerM mPlayer;
    // Frames.
    private TextureRegion[] mLeftAnimation;
    private TextureRegion[] mRightAnimation;
    private TextureRegion[] mUpAnimation;
    private TextureRegion[] mDownAnimation;
    private int mFrame;
    private float mDeltaAnimation;
    private boolean mIsAnimated;
    // Hello frame.
    private TextureRegion mHelloTexture;
    private float mDeltaHello;
    private boolean mIsSpeaking;
    // Position in the world.
    private final Vector2 mCoordinatesChar;
    private final Vector2 mCoordinatesHello;
    // Current orientation.
    private PlayerM.Event mOrientation;

    public PlayerV(PlayerM player, String color)
    {
        loadTextures(color);
        // Get the model and observe it.
        mPlayer = player;
        mPlayer.addObserver(this);
        // Walking animation
        mFrame = 0;
        mDeltaAnimation = 0f;
        mOrientation = PlayerM.Event.LEFT;
        // "Hello!" animation
        mDeltaHello = 0f;
        mIsSpeaking = false;
        // Always the same positions:
        mCoordinatesChar = new Vector2(-WIDTH_CHAR / 2f,
                -HEIGHT_CHAR / 2f + CellV.TILE_SIZE / 2f);
        mCoordinatesHello = new Vector2(-WIDTH_HELLO / 2f,
                mCoordinatesChar.y + HEIGHT_HELLO);
    }

    public void render(float delta)
    {
        renderCharacter(delta);
        renderHello(delta);
    }

    private void renderCharacter(float delta)
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
        Main.mBatch.draw(toDraw, mCoordinatesChar.x, mCoordinatesChar.y,
                WIDTH_CHAR, HEIGHT_CHAR);
        // Change frame?
        if (mIsAnimated)
        {
            mDeltaAnimation += delta;

            if (mDeltaAnimation >= TIME_PER_FRAME)
            {
                mDeltaAnimation = 0f;

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

    private void renderHello(float delta)
    {
        if (mIsSpeaking)
        {
            Main.mBatch.draw(mHelloTexture, mCoordinatesHello.x, mCoordinatesHello.y,
                    WIDTH_HELLO, HEIGHT_HELLO);
            mDeltaHello += delta;

            // Stop animation?
            if (mDeltaHello >= TIME_SPEAK)
            {
                mDeltaHello = 0f;
                mIsSpeaking = false;
            }
        }
    }

    @Override
    public void update(Observable observable, Object o)
    {
        if (observable == mPlayer)
        {
            if (o == null)
            {
                // Player wants to say hello.
                mDeltaHello = 0f;
                mIsSpeaking = true;
            }
            else
            {
                // Player has moved; start the move animation.
                mOrientation = (PlayerM.Event) o;
                mFrame = 1;
                mIsAnimated = true;
            }
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

        mHelloTexture = GameScreen.mCharactersAtlas.findRegion("hello");
    }
}