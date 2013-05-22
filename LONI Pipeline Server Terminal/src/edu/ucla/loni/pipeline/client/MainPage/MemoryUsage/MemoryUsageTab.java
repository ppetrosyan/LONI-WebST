package edu.ucla.loni.pipeline.client.MainPage.MemoryUsage;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;

public class MemoryUsageTab {
	
	public static Tab setTab(final LONI_Chart memChart) {
		Tab tabMemoryUsage = new Tab("Memory Usage");

		tabMemoryUsage.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				memChart.getChart().redraw();
			}
		});

		tabMemoryUsage.setPane(memChart);
		return tabMemoryUsage;
	}
}
