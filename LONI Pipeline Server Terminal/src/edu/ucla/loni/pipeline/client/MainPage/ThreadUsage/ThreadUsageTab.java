package edu.ucla.loni.pipeline.client.MainPage.ThreadUsage;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;

public class ThreadUsageTab {
	
	public static Tab setTab(final LONI_Chart thrdChart) {
		Tab tabThreadUsage = new Tab("Thread Usage");

		tabThreadUsage.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				thrdChart.getChart().redraw();
			}
		});

		tabThreadUsage.setPane(thrdChart);
		return tabThreadUsage;
	}
}
