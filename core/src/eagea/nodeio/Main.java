package eagea.nodeio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import eagea.nodeio.view.screen.game.GameScreen;

public class Main extends Game
{
    private OrthographicCamera mCamera;
    private Viewport mViewPort;
    private GameScreen mGameScreen;

    private SpriteBatch mBatch;


    Texture img;

    @Override
    public void create ()
    {
        mBatch = new SpriteBatch();
        mCamera = new OrthographicCamera(100, 250) ;
        mViewPort = new ExtendViewport(mCamera.viewportWidth, mCamera.viewportHeight, mCamera) ;


        mGameScreen = new GameScreen(this);

        setScreen(mGameScreen);
    }

    @Override
    public void dispose ()
    {
        mBatch.dispose();
        img.dispose();



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
        //getScreen().resize(width, height) ;
    }

    public SpriteBatch getBatch()
    {
        return mBatch;
    }
}