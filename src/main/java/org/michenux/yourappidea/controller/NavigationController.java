package org.michenux.yourappidea.controller;

import org.michenux.android.eula.Eula;
import org.michenux.yourappidea.R;
import org.michenux.yourappidea.activity.AirportActivity;
import org.michenux.yourappidea.activity.FriendsActivity;
import org.michenux.yourappidea.activity.MyPreferences;
import org.michenux.yourappidea.activity.RequestCodes;
import org.michenux.yourappidea.activity.YourAppMainActivity.ConfirmQuitDialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class NavigationController {

	public static NavigationController instance = new NavigationController();

	public static NavigationController getInstance() {
		return instance;
	}
	
	private NavigationController() {
		
	}

	public void startAirportActivity(Context context) {
		Intent intent = new Intent(context, AirportActivity.class);
		context.startActivity(intent);
	}

	public void startFriendsActivity(Context context) {
		Intent intent = new Intent(context, FriendsActivity.class);
		context.startActivity(intent);
	}
	
	public void startAppRating(Context context) {
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
	}
	
	public void confirmEula(FragmentActivity activity) {
		Eula.show(activity, R.string.eula_title, R.string.eula_accept, R.string.eula_refuse);
	}
	
	public void showEula(FragmentActivity activity) {
		Eula.show(activity, R.string.eula_title, R.string.eula_close);
	}
	
	public void showExitDialog( FragmentActivity activity ) {
		ConfirmQuitDialog newFragment = ConfirmQuitDialog.newInstance();
		newFragment.show(activity.getSupportFragmentManager(), "dialog");
	}
	
	public void exitApplication() {
		
	}

	public void showSettings(FragmentActivity activity) {
		activity.startActivityForResult(new Intent(activity, MyPreferences.class),
				RequestCodes.PREFERENCES_RESULTCODE);
	}
}
