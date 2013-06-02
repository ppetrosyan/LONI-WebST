/*
 * This file is part of LONI Pipeline Web-based Server Terminal.
 * 
 * LONI Pipeline Web-based Server Terminal is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * LONI Pipeline Web-based Server Terminal is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with LONI Pipeline Web-based Server Terminal.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package edu.ucla.loni.pipeline.client.MainPage.ThreadUsage;

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

public class ThreadUsageTab {

	private final LONI_Chart thrdChart;
	private final AsyncClientServices asyncClientServices;
	private final LONINotifications notifications;

	public ThreadUsageTab(AsyncClientServices asyncClientServices,
			LONINotifications notifications) {
		thrdChart = new LONI_Chart("Thread");
		this.asyncClientServices = asyncClientServices;
		this.notifications = notifications;
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
			@Override
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
		asyncClientServices.reqResourceXMLService
				.getXMLData(new AsyncCallback<String>() {
					@Override
					public void onSuccess(final String xmlData) {
						/** Thread Usage Tab */
						thrdChart.getChart().refreshChart(xmlData);

						notifications.showMessage(
								"Thread Usage Tab refreshed successfully.",
								true);
					}

					@Override
					public void onFailure(Throwable caught) {
						if (clicked) {
							notifications
									.showMessage(
											"Thread Usage Tab did not refresh successfully.",
											true);
						}
					}
				});
	}
}
