package eagea.nodeio.view.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import eagea.nodeio.Main;
import eagea.nodeio.view.object.map.Cell;
import eagea.nodeio.view.object.map.Map;

/**
 * Render the game environment i.e. map, players and more.
 */
public class GameScreen extends ScreenAdapter
{
    private Main mMain;
    // Textures.
    private TextureAtlas mMapAtlas;
    private TextureAtlas mPlayerAtlas;
    // Game objects.
    private Map mMap;

    public GameScreen(Main main)
    {
        mMain = main;
    }

    @Override
    public void show()
    {
        // Textures.
        mMapAtlas = new TextureAtlas(Gdx.files.internal("map.atlas")) ;
        mPlayerAtlas = new TextureAtlas(Gdx.files.internal("player.atlas")) ;
        // Inputs listener.
        Gdx.input.setInputProcessor(new Listener());
        // Game objects.
        mMap = new Map(100, 100, 0, 0, mMapAtlas.findRegion("grass"));
    }

    @Override
    public void dispose()
    {
        mMapAtlas.dispose();
        mPlayerAtlas.dispose();
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

        mMain.getBatch().begin() ;
        mMap.render(mMain.getBatch());
        mMain.getBatch().end() ;


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

    private void update(float delta)
    {

    }
}