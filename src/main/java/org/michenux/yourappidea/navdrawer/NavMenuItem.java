package org.michenux.yourappidea.navdrawer;

import org.michenux.android.resources.ResourceUtils;

import android.content.Context;

public class NavMenuItem implements NavDrawerItem {

	public static final int ITEM_TYPE = 1 ;
	
	private int id ;
	
	private String label ;
	
	private int icon ;

	private NavMenuItem() {
	}

	public static NavMenuItem create( int id, String label, String icon, Context context ) {
		NavMenuItem item = new NavMenuItem();
		item.setId(id);
		item.setLabel(label);
		item.setIcon(ResourceUtils.getDrawableIdByName(icon, context));
		return item;
	}
	
	@Override
	public int getType() {
		return ITEM_TYPE;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean updateTitle() {
		return false;
	}
}
