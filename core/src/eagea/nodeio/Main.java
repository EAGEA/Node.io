package eagea.nodeio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game
{
    public static final int WORLD_WIDTH = 30;
    public static final int WORLD_HEIGHT = 30;

    // Basic GDX rendering object.
    public static OrthographicCamera mCamera;
    public static Viewport mViewPort;
    public static SpriteBatch mBatch;
    private GameScreen mGameScreen;

    @Override
    public void create ()
    {
        mBatch = new SpriteBatch();
        mCamera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        mViewPort = new FillViewport(mCamera.viewportWidth, mCamera.viewportHeight, mCamera) ;


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
        mViewPort.update(width, height, true) ;
        // Center the camera
        //resetCameraPosition() ;
        // And resize the current screen
    }
}