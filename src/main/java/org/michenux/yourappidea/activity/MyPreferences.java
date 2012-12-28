package org.michenux.yourappidea.activity;

import org.michenux.yourappidea.R;

import roboguice.activity.RoboPreferenceActivity;
import android.os.Bundle;

public class MyPreferences extends RoboPreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}
