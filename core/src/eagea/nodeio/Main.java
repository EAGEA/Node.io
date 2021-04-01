package eagea.nodeio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends ApplicationAdapter
{
	private OrthographicCamera mCamera;
	private Viewport mViewPort;

	private SpriteBatch mBatch;

	;
	Texture img;
	
	@Override
	public void create ()
	{
		mBatch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		loadCamera();
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mBatch.begin();
		mBatch.draw(img, 0, 0);
		mBatch.end();
	}
	
	@Override
	public void dispose ()
	{
		mBatch.dispose();
		img.dispose();
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

	private void loadCamera()
	{
		mCamera = new OrthographicCamera(100, 250) ;
		mViewPort = new ExtendViewport(mCamera.viewportWidth, mCamera.viewportHeight, mCamera) ;
	}
}