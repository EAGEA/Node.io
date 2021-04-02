package eagea.nodeio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import eagea.nodeio.Main;
import eagea.nodeio.controller.Controller;
import eagea.nodeio.model.Model;
import eagea.nodeio.view.View;
import eagea.nodeio.view.object.map.MapV;

/**
 * Render the game environment i.e. map, players and more.
 */
public class GameScreen extends ScreenAdapter
{
    // Textures.
    public static TextureAtlas mMapAtlas;
    public static TextureAtlas mPlayerAtlas;
    // Model:
    private Model mModel;
    // View:
    private View mView;
    // Controller:
    private Controller mController;

    @Override
    public void show()
    {
        // Textures.
        mMapAtlas = new TextureAtlas(Gdx.files.internal("map.atlas")) ;
        mPlayerAtlas = new TextureAtlas(Gdx.files.internal("player.atlas")) ;
        // Game objects.
        mModel = new Model();
        mView = new View(mModel);
        mController = new Controller(mModel);
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
        // Update objects.
        update(delta) ;
        // Clear the screen.
        Gdx.gl.glEnable(GL30.GL_BLEND) ;
        Gdx.gl.glClearColor(0, 0, 0, 1) ;
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT) ;
        // Render objects.
        Main.mBatch.begin();
        mView.render();
        Main.mBatch.end();
    }

    private void update(float delta)
    {

    }
}