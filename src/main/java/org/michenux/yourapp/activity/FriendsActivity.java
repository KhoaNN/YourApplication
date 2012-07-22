package org.michenux.yourapp.activity;

import org.michenux.android.ui.fragment.MasterDetailFragmentHelper;
import org.michenux.android.ui.fragment.MasterDetailFragments;
import org.michenux.yourapp.R;
import org.michenux.yourapp.fragment.FriendDetailFragment;
import org.michenux.yourapp.fragment.FriendListFragment;

import roboguice.activity.RoboFragmentActivity;
import roboguice.activity.event.OnCreateEvent;
import roboguice.activity.event.OnResumeEvent;
import roboguice.event.Observes;
import roboguice.inject.ContentView;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

/**
 * @author Michenux
 *
 */
@ContentView(R.layout.friends_fragment)
public class FriendsActivity extends RoboFragmentActivity {

	/**
	 * @param event
	 */
	public void onCreate(@Observes OnCreateEvent event) {

		FragmentManager fm = getSupportFragmentManager();

		MasterDetailFragments currentFragments = MasterDetailFragmentHelper
				.getCurrentFragments(R.id.friendmain_fragment,
						R.id.frienddetail_fragment, FriendDetailFragment.class,
						fm);
		if ( currentFragments.master == null ) {
			currentFragments.master = FriendListFragment.newInstance();
		}

		MasterDetailFragmentHelper.initFragments(currentFragments, R.id.friendmain_fragment, 
				R.id.frienddetail_fragment, getResources().getConfiguration(), fm);
	}
}
