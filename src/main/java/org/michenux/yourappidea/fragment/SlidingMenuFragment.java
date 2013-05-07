package org.michenux.yourappidea.fragment;

import org.michenux.android.resources.ResourceUtils;
import org.michenux.android.ui.sections.SectionListAdapter;
import org.michenux.android.ui.sections.SectionListItem;
import org.michenux.android.ui.sections.SectionListView;
import org.michenux.yourappidea.R;
import org.michenux.yourappidea.activity.AirportActivity;
import org.michenux.yourappidea.model.SlidingMenuItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SlidingMenuFragment extends Fragment implements OnItemClickListener {

	private StandardArrayAdapter arrayAdapter;

	private SectionListAdapter sectionAdapter;

	private SectionListView listView;

	private SectionListItem[] slidingMenuItems =  { // Comment to prevent re-format
			new SectionListItem( new SlidingMenuItem("Eula", "slidingmenu_eula"), "A"), //
			new SectionListItem( new SlidingMenuItem("ChangeLog", "slidingmenu_changelog"), "A"), //
			new SectionListItem( new SlidingMenuItem("Rate this app", "slidingmenu_rating"), "B"), //
			new SectionListItem( new SlidingMenuItem("Test 4 - A", "slidingmenu_eula"), "B") //
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return this.getStyledLayoutInflater(inflater).inflate(R.layout.slidingmenu_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		arrayAdapter = new StandardArrayAdapter(this.getActivity(), R.id.slidingmenu_itemlabel,
				slidingMenuItems);
		sectionAdapter = new SectionListAdapter(this.getStyledLayoutInflater(this.getActivity().getLayoutInflater()), arrayAdapter);
		listView = (SectionListView) this.getView().findViewById(
				R.id.section_list_view);
		listView.setAdapter(sectionAdapter);
		listView.setOnItemClickListener(this);
		//sectionAdapter.setOnItemClickListener(this);
	}
	
	private LayoutInflater getStyledLayoutInflater( LayoutInflater parent ) {
		// create ContextThemeWrapper from the original Activity Context with the custom theme
		Context context = new ContextThemeWrapper(getActivity(), R.style.SectionListViewTheme);
		// clone the inflater using the ContextThemeWrapper
		return this.getActivity().getLayoutInflater().cloneInContext(context);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d("LMI", "position: "+position);
		switch( position ) {
		case 1:
			Intent intent = new Intent(getActivity(), AirportActivity.class);
			this.startActivity(intent);
			break;
		case 2:
			break;
		}
	}
	
	private class StandardArrayAdapter extends ArrayAdapter<SectionListItem> {

		private final SectionListItem[] items;
		
		public StandardArrayAdapter(final Context context,
				final int textViewResourceId, final SectionListItem[] items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(final int position, final View convertView,
				final ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater localInflater = SlidingMenuFragment.this.getStyledLayoutInflater(getActivity().getLayoutInflater());
				view = localInflater.inflate(R.layout.slidingmenu_listitem, parent, false);
			}
			final SectionListItem currentItem = items[position];
			if (currentItem != null) {
				SlidingMenuItem oSlidingMenuItem = (SlidingMenuItem) currentItem.getItem();
				
				final TextView textView = (TextView) view
						.findViewById(R.id.slidingmenu_itemlabel);
				textView.setText(oSlidingMenuItem.getLabel());
				textView.setCompoundDrawablesWithIntrinsicBounds(
					ResourceUtils.getDrawableByName(oSlidingMenuItem.getIcon(), getContext()), null, null, null);
				
//				final ImageView itemIcon = (ImageView) view
//						.findViewById(R.id.slidingmenu_itemicon);
//				itemIcon.setImageDrawable();
			}
			return view;
		}
	}
}
