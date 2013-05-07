package org.michenux.android.ui.sections;

public class SectionListItem {
	
	private Object item;
	private String section;

	public SectionListItem(final Object item, final String section) {
		super();
		this.item = item;
		this.section = section;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
}