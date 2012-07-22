package org.michenux.yourapp.activity;

import org.michenux.android.init.AppInit;
import org.michenux.yourapp.R;

import roboguice.activity.RoboFragmentActivity;
import roboguice.activity.event.OnResumeEvent;
import roboguice.event.Observes;
import roboguice.inject.ContentView;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.google.inject.Inject;

/**
 * @author Michenux
 *
 */
@ContentView(R.layout.splashscreen_fragment)
public class SplashScreenActivity extends RoboFragmentActivity {

	/**
	 * 
	 */
	protected MyStateSaver data;

	/**
	 * 
	 */
	@Inject private AppInit appInit ;
	
	/**
	 * {@inheritDoc}
	 * @see roboguice.activity.RoboFragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.data = (MyStateSaver) getLastCustomNonConfigurationInstance();
		if (this.data == null) {
			this.data = new MyStateSaver();
		}
		if (this.data.doInit) {
			doInit();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see android.support.v4.app.FragmentActivity#onRetainCustomNonConfigurationInstance()
	 */
	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		return this.data;
	}

	/**
	 * 
	 */
	protected void startNextActivity() {
		Intent intent = new Intent(this, YourAppMainActivity.class);
		this.startActivity(intent);
		this.finish();
	}

	/**
	 * 
	 */
	protected void doInit() {
		this.data.doInit = false;
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				try {
					appInit.init(getApplicationContext());
					startNextActivity();
				} catch( NameNotFoundException e ) {
					throw new RuntimeException(e);
				}
			}
		}, 2000);
	}

	/**
	 * @author Michenux
	 * 
	 */
	private class MyStateSaver {
		public boolean doInit = true;
	}
}
