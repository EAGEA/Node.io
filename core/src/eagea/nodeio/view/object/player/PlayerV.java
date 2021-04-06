package eagea.nodeio.view.object.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Observable;
import java.util.Observer;

import eagea.nodeio.GameScreen;
import eagea.nodeio.Main;
import eagea.nodeio.model.logic.map.MapM;
import eagea.nodeio.model.logic.map.ZoneM;
import eagea.nodeio.model.logic.player.PlayerM;
import eagea.nodeio.view.View;
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
    private final PlayerM mRealPlayer;
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
    // Current orientation.
    private PlayerM.Event mOrientation;

    public PlayerV(PlayerM realPlayer, PlayerM player, String color)
    {
        loadTextures(color);
        // Get the model and observe it.
        mRealPlayer = realPlayer;
        mPlayer = player;
        mPlayer.addObserver(this);
        // Walking animation.
        mFrame = 0;
        mDeltaAnimation = 0f;
        mOrientation = PlayerM.Event.LEFT;
        // "Hello!" animation.
        mDeltaHello = 0f;
        mIsSpeaking = false;
    }

    public void render(float delta)
    {
        Vector2 coord = View.getCoordinates(
                new Vector3(mRealPlayer.getI(), mRealPlayer.getJ(), mRealPlayer.getZone()),
                new Vector3(mPlayer.getI(), mPlayer.getJ(), mPlayer.getZone()));

        renderCharacter(delta, coord);
        renderHello(delta, coord);
    }

    private void renderCharacter(float delta, Vector2 coord)
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
        Main.mBatch.draw(toDraw,
                coord.x,
                coord.y + HEIGHT_CHAR / 2f,
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

    private void renderHello(float delta, Vector2 coord)
    {
        if (mIsSpeaking)
        {
            Main.mBatch.draw(mHelloTexture,
                    coord.x,
                    coord.y + HEIGHT_CHAR / 2f + HEIGHT_HELLO,
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