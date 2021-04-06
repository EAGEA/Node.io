package eagea.nodeio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public static Viewport mViewPortGUI;
    public static SpriteBatch mBatch;
    private GameScreen mGameScreen;

    @Override
    public void create ()
    {
        DEBUG();

        mBatch = new SpriteBatch();
        mCamera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        mViewPortGame = new FillViewport(mCamera.viewportWidth, mCamera.viewportHeight, mCamera) ;
        //mViewPortGUI = new ExtendViewport(mCamera.viewportWidth, mCamera.viewportHeight, mCamera) ;

        mGameScreen = new GameScreen();
        setScreen(mGameScreen);

        // World units when drawing.
        Main.mBatch.setProjectionMatrix(Main.mCamera.combined);
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
        // Resize the viewport
        mViewPortGame.update(width, height, true) ;
        //mViewPortGUI.update(width, height, true) ;
        // Center the camera
        //resetCameraPosition() ;
        // And resize the current screen
    }

    public void DEBUG()
    {
        if (! DEBUG)
        {
            // Disable logs.
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