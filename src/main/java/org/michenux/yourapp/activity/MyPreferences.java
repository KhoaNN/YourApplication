package org.michenux.yourapp.activity;

import org.michenux.yourapp.R;

import roboguice.activity.RoboPreferenceActivity;
import android.os.Bundle;

public class MyPreferences extends RoboPreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}
