package eagea.nodeio;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication
{
	private Main mMain;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mMain = new Main();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(mMain, config);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		mMain.onStop();
	}
}