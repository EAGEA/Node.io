package eagea.nodeio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import eagea.nodeio.controller.Controller;
import eagea.nodeio.model.Model;
import eagea.nodeio.view.View;

/**
 * Render the game environment i.e. map, players and more.
 */
public class GameScreen extends ScreenAdapter
{
    // Textures.
    public static TextureAtlas mEnvironmentAtlas;
    public static TextureAtlas mCharactersAtlas;
    public static TextureAtlas mHUDAtlas;
    public static TextureAtlas mGUIAtlas;
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
        mEnvironmentAtlas = new TextureAtlas(Gdx.files.internal("environment.atlas")) ;
        mCharactersAtlas = new TextureAtlas(Gdx.files.internal("characters.atlas")) ;
        mHUDAtlas = new TextureAtlas(Gdx.files.internal("hud.atlas")) ;
        mGUIAtlas = new TextureAtlas(Gdx.files.internal("gui.atlas")) ;
        // Game objects.
        mModel = new Model(this);
        mView = new View(mModel);
        mController = new Controller(mModel, mView);
    }

    @Override
    public void dispose()
    {
        mEnvironmentAtlas.dispose();
        mCharactersAtlas.dispose();
    }

    @Override
    public void render(float delta)
    {
        super.render(delta) ;
        // Clear the screen.
        Gdx.gl.glEnable(GL30.GL_BLEND) ;
        Gdx.gl.glClearColor(0, 0, 0, 1) ;
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT) ;
        // Render objects.
        if (mView != null)
        {
            Main.mBatch.begin();
            mView.render(delta);
            Main.mBatch.end();
        }
    }

    public void onStartGame()
    {
        mView.onStartGame();
    }
}