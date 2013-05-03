package edu.ucla.loni.pipeline.client;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.smartgwt.client.widgets.layout.VLayout;
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
		Chart chart = new Chart()
		.setType(Series.Type.SPLINE)
		.setChartTitleText("Lawn Tunnels")
		.setMarginRight(10);

		Series series = chart.createSeries()
				.setName("Moles per Yard")
				.setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
		chart.addSeries(series);

		VLayout uploadLayout = new VLayout();
		uploadLayout.addMember(chart);
		tabThreadUsage.setPane(uploadLayout);
	}

}