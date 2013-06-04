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

package edu.ucla.loni.pipeline.client.MainPage.MemoryUsage;

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

	private final LONI_Chart memChart;
	private final AsyncClientServices asyncClientServices;
	private final LONINotifications notifications;

	public MemoryUsageTab(AsyncClientServices asyncClientServices,
			LONINotifications notifications) {
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
			@Override
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
		asyncClientServices.reqResourceXMLService
				.getXMLData(new AsyncCallback<String>() {
					@Override
					public void onSuccess(final String xmlData) {
						/** Memory Usage Tab */
						memChart.getChart().refreshChart(xmlData);

						notifications
								.showMessage("Memory Usage updated successfully.");
					}

					@Override
					public void onFailure(Throwable caught) {
						if (clicked) {
							notifications
									.showMessage("ERROR: Memory Usage did not update successfully.");
						}
					}
				});
	}
}
