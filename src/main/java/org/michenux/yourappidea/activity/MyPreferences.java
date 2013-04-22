package org.michenux.yourappidea.activity;

import org.michenux.yourappidea.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MyPreferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}
