package eagea.nodeio;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main(), config);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		/*
		// Trigger the shutdown hook.
		Runtime.getRuntime().exit(0);
		 */
	}
}
