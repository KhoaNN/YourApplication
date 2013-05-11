package org.michenux.yourappidea.fragment;

import org.michenux.android.ui.sections.SectionListAdapter;
import org.michenux.android.ui.sections.SectionListItem;
import org.michenux.android.ui.sections.SectionListView;
import org.michenux.yourappidea.R;
import org.michenux.yourappidea.adapters.SlidingMenuAdapter;
import org.michenux.yourappidea.controller.NavigationController;
import org.michenux.yourappidea.model.SlidingMenuItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SlidingMenuFragment extends Fragment implements
		OnItemClickListener {

	private SlidingMenuAdapter slidingMenuAdapter;

	private SectionListAdapter sectionAdapter;

	private SectionListView listView;

	private SectionListItem[] slidingMenuItems = { //
			new SectionListItem(new SlidingMenuItem("List/Detail (Fragment)",
					"slidingmenu_friends"), "Demos"), //
			new SectionListItem(new SlidingMenuItem("Airport (AsyncTask)",
					"slidingmenu_airport"), "Demos"), //
			new SectionListItem(new SlidingMenuItem("Settings",
					"slidingmenu_settings"), "General"),				
			new SectionListItem(new SlidingMenuItem("Rate this app",
					"slidingmenu_rating"), "General"), //
			new SectionListItem(
					new SlidingMenuItem("Eula", "slidingmenu_eula"), "General"), //
			new SectionListItem(
					new SlidingMenuItem("Quit", "slidingmenu_quit"), "General"), //

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.slidingmenu_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		slidingMenuAdapter = new SlidingMenuAdapter(this.getActivity(),
				R.id.slidingmenu_itemlabel, slidingMenuItems);
		sectionAdapter = new SectionListAdapter(this.getActivity()
				.getLayoutInflater(), slidingMenuAdapter);
		listView = (SectionListView) this.getView().findViewById(
				R.id.section_list_view);
		listView.setAdapter(sectionAdapter);
		listView.setOnItemClickListener(sectionAdapter);
		sectionAdapter.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			NavigationController.getInstance().startFriendsActivity(
					getActivity());
			break;
		case 1:
			NavigationController.getInstance().startAirportActivity(
					getActivity());
			break;
		case 2:
			NavigationController.getInstance().showSettings(getActivity());
			break;
		case 3:
			NavigationController.getInstance().startAppRating(getActivity());
			break;
		case 4:
			NavigationController.getInstance().showEula(getActivity());
			break;
		case 5:
			NavigationController.getInstance().showExitDialog(getActivity());
			break;
		}
	}
}
