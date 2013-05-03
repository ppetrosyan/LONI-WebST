package edu.ucla.loni.pipeline.client;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class ThreadTabSelectedHandler implements TabSelectedHandler {

	private Tab tabThreadUsage;
	
	public ThreadTabSelectedHandler(Tab tabThreadUsage) {
		this.tabThreadUsage = tabThreadUsage;
	}
	
	@Override
	public void onTabSelected(TabSelectedEvent event) {

	}

}