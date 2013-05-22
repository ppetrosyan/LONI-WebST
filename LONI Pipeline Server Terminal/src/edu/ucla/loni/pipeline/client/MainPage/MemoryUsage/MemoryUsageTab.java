package edu.ucla.loni.pipeline.client.MainPage.MemoryUsage;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;

public class MemoryUsageTab {
	
	private LONI_Chart memChart;
	
	public MemoryUsageTab() {
		memChart = new LONI_Chart("Memory");
	}
	
	public Tab setTab() {
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
	
	public LONI_Chart getChart() {
		return memChart;
	}
}
