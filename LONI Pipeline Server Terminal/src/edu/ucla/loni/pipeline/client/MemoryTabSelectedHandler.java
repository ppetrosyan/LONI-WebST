package edu.ucla.loni.pipeline.client;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class MemoryTabSelectedHandler implements TabSelectedHandler {

	private Tab tabMemoryUsage;
	
	public MemoryTabSelectedHandler(Tab tabMemory) {
		this.tabMemoryUsage = tabMemoryUsage;
	}
	
	@Override
	public void onTabSelected(TabSelectedEvent event) {

	}

}
