package edu.ucla.loni.pipeline.client.MainPage.MemoryUsage;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;
import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;

public class MemoryUsageTab {
	
	private LONI_Chart memChart;
	private AsyncClientServices asyncClientServices;
	private LONINotifications notifications;
	
	public MemoryUsageTab(AsyncClientServices asyncClientServices, LONINotifications notifications) {
		memChart = new LONI_Chart("Memory");
		this.asyncClientServices = asyncClientServices;
		this.notifications = notifications;
	}
	
	public Tab setTab() {
		Tab tabMemoryUsage = new Tab("Memory Usage");

		VLayout layoutMemUsage = new VLayout();
		
		tabMemoryUsage.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				memChart.getChart().redraw();
			}
		});

		layoutMemUsage.addMember(memChart);
		
		Button memChartRefreshButton = new Button("Refresh");
		memChartRefreshButton.setAlign(Alignment.CENTER);
		layoutMemUsage.addMember(memChartRefreshButton);
		memChartRefreshButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fetchMemoryData(true);
			}
		});
		
		tabMemoryUsage.setPane(layoutMemUsage);
		
		// TODO: figure out why the xml isn't fetched sometimes
		fetchMemoryData(false);
	
		return tabMemoryUsage;
	}
	
	public LONI_Chart getChart() {
		return memChart;
	}
	
	private void fetchMemoryData(final Boolean clicked) {
		asyncClientServices.reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
			@Override
			public void onSuccess(final String xmlData) {
				/** Memory Usage Tab */
				memChart.getChart().refreshChart(xmlData);
				
				notifications.showMessage("Memory Usage Tab refreshed successfully.", true);
			}

			@Override
			public void onFailure(Throwable caught) {
				if(clicked) {
					notifications.showMessage("Memory Usage Tab did not refresh successfully.", true);
				}
			}
		});
	}
}
