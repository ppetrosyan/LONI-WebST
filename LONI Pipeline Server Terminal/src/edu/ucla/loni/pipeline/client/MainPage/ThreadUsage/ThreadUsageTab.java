package edu.ucla.loni.pipeline.client.MainPage.ThreadUsage;

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

public class ThreadUsageTab {
	
	private LONI_Chart thrdChart; 
	private AsyncClientServices asyncClientServices;
	
	public ThreadUsageTab(AsyncClientServices asyncClientServices) {
		thrdChart = new LONI_Chart("Thread");
		this.asyncClientServices = asyncClientServices;
	}
	
	public Tab setTab() {
		Tab tabThreadUsage = new Tab("Thread Usage");

		VLayout layoutThrdUsage = new VLayout();
		
		tabThreadUsage.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				thrdChart.getChart().redraw();
			}
		});

		layoutThrdUsage.addMember(thrdChart);
		
		Button memChartRefreshButton = new Button("Refresh");
		memChartRefreshButton.setAlign(Alignment.CENTER);
		layoutThrdUsage.addMember(memChartRefreshButton);
		memChartRefreshButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fetchThreadData(true);
			}
		});
		
		tabThreadUsage.setPane(layoutThrdUsage);
		
		// TODO: figure out why the xml isn't fetched sometimes
		fetchThreadData(false);
		
		return tabThreadUsage;
	}
	
	public LONI_Chart getChart() {
		return thrdChart;
	}
	
	private void fetchThreadData(final Boolean clicked) {
		asyncClientServices.reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
			@Override
			public void onSuccess(final String xmlData) {
				/** Thread Usage Tab */
				thrdChart.getChart().refreshChart(xmlData);
				
				if(clicked) {
					Window.alert("Thread Usage Tab refreshed successfully.");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				if(clicked) {
					Window.alert("Thread Usage Tab did not refresh successfully.");
				}
			}
		});
	}
}
