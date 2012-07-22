package org.michenux.yourapp.fragment;

import org.michenux.yourapp.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import roboguice.fragment.RoboFragment;

public class SplashScreenFragment extends RoboFragment {

	/**
	 * Logger
	 */
	private static final Logger log = LoggerFactory
			.getLogger(SplashScreenFragment.class);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.splashscreen, container,
				false);
		return view;
	}
}
