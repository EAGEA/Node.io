package eagea.nodeio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Main extends Game
{
    public final boolean DEBUG = true;

    public static final int WORLD_WIDTH = 30;
    public static final int WORLD_HEIGHT = 30;

    // Basic GDX rendering object.
    public static OrthographicCamera mCamera;
    public static Viewport mViewPortGame;
    public static SpriteBatch mBatch;
    private GameScreen mGameScreen;

    @Override
    public void create ()
    {
        DEBUG();
        // GDX objects.
        mBatch = new SpriteBatch();
        mCamera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        mViewPortGame = new FillViewport(mCamera.viewportWidth, mCamera.viewportHeight, mCamera) ;
        // Screens.
        mGameScreen = new GameScreen();
        setScreen(mGameScreen);
    }

    @Override
    public void dispose ()
    {
        mBatch.dispose();
        mGameScreen.dispose();
    }

    @Override
    public void resize(int width, int height)
    {
        // Resize the viewport.
        mViewPortGame.update(width, height, false) ;
        // World units when drawing.
        mBatch.setProjectionMatrix(mCamera.combined);
    }

    public void DEBUG()
    {
        if (! DEBUG)
        {
            // Disable all logs.
            System.setOut(new PrintStream(new OutputStream()
                    {
                        @Override public void write(int b) { }
                    }
                )
            );
            System.setErr(new PrintStream(new OutputStream()
                    {
                        @Override public void write(int b) { }
                    }
                )
            );
        }
    }
}