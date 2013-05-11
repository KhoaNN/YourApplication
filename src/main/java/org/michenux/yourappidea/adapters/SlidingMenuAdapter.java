package org.michenux.yourappidea.adapters;

import org.michenux.android.resources.ResourceUtils;
import org.michenux.android.ui.sections.SectionListItem;
import org.michenux.yourappidea.R;
import org.michenux.yourappidea.model.SlidingMenuItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SlidingMenuAdapter extends ArrayAdapter<SectionListItem> {

	private final SectionListItem[] items;

	public SlidingMenuAdapter(Context context, int textViewResourceId,
			SectionListItem[] items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.slidingmenu_listitem, parent,
					false);
		}
		final SectionListItem currentItem = items[position];
		if (currentItem != null) {
			SlidingMenuItem oSlidingMenuItem = (SlidingMenuItem) currentItem
					.getItem();

			final TextView textView = (TextView) view
					.findViewById(R.id.slidingmenu_itemlabel);
			textView.setText(oSlidingMenuItem.getLabel());

			final ImageView itemIcon = (ImageView) view
					.findViewById(R.id.slidingmenu_itemicon);
			itemIcon.setImageDrawable(ResourceUtils.getDrawableByName(
					oSlidingMenuItem.getIcon(), getContext()));
		}
		return view;
	}
}
