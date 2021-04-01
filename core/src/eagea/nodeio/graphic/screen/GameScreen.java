package eagea.nodeio.graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;

public class GameScreen extends ScreenAdapter
{
    @Override
    public void show()
    {
        //Gdx.input.setInputProcessor(mGameTouchListener) ;
    }

    @Override
    public void render(float delta)
    {
        super.render(delta) ;
        // Update all the objects
        update(delta) ;
        // Clear the screen
        Gdx.gl.glEnable(GL30.GL_BLEND) ;
        Gdx.gl.glClearColor(0, 0, 0, 1) ;
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT) ;
        // Check camera position, rumbling.. etc
        //renderCamera() ;
        // Render object
        /*
        mSpriteBatch.setProjectionMatrix(mCamera.combined) ;
        mSpriteBatch.begin() ;
        renderManager() ;
        renderHUD() ;
        mSpriteBatch.end() ;
         */
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
    }

    private void update(float delta)
    {

    }
}