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

package edu.ucla.loni.pipeline.client.Charts;

import java.util.ArrayList;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LONI_Chart extends VLayout {

	static int refreshTime = 3000;
	
	private LineChartPanel chart;
	private final String monitorType;
	private Label initStats;
	private Label usedStats1;
	private Label usedStats2;
	private Label usedStats3;
	private Label commStats1;
	private Label commStats2;
	private Label maxStats;
	private Label thrdCntStats;
	private Label thrdPkStats;
	private VLayout statistics;

	// timer redraws charts and updates statistics
	private final Timer timer = new Timer() {
		@Override
		public void run() {
			try {
				chart.updateValues();
			} catch (Exception e) {
				System.err
						.println("Exception thrown in timer.run() in LONI_Chart.java: "
								+ e.getMessage());
			}
		}
	};

	public LONI_Chart(String mt) {
		monitorType = mt;
		initialize();
	}

	public LineChartPanel getChart() {
		return chart;
	}

	public void refreshStats() {
		if (monitorType.equals("Memory")) {
			MemoryStatistics memStats = chart.getMemStatistics();
			initStats.setContents(memStats.getInitMemMB() + " MB");
			usedStats1.setContents(memStats.getUsedMemMB() + " MB");
			usedStats2.setContents(memStats.getUsedCommMemPercent()
					+ "% of committed");
			usedStats3.setContents(memStats.getUsedMaxMemPercent()
					+ "% of the max");
			commStats1.setContents(memStats.getCommMemMB() + " MB");
			commStats2.setContents(memStats.getCommMaxMemPercent()
					+ "% of the max");
			maxStats.setContents(memStats.getMaxMemMB() + " MB");
		} else if (monitorType.equals("Thread")) {
			ArrayList<Integer> thrdStats = chart.getThrdStatistics();
			thrdCntStats.setContents(thrdStats.get(0) + "");
			thrdPkStats.setContents(thrdStats.get(1) + "");
		} else {
			System.err
					.println("Incorrect monitorType provided. Check timer.run() in LONI_Chart.java for errors.");
			return;
		}
	}

	private void initialize() {
		// set up memory chart panels
		if (monitorType.equals("Memory")) {
			setHeight100();
			setWidth100();

			// create memory chart
			chart = new LineChartPanel("Memory", this);

			MemoryStatistics memStats = chart.getMemStatistics();

			// create statistics table
			Label initTitle = new Label("Initial memory: ");
			initTitle.setWidth(150);
			initStats = new Label(memStats.getInitMemMB() + " MB");
			initStats.setWidth(75);

			HLayout initStatLayout = new HLayout();
			initStatLayout.addMember(initTitle);
			initStatLayout.addMember(initStats);

			Label usedTitle = new Label("Used memory: ");
			usedTitle.setWidth(150);
			usedStats1 = new Label(memStats.getUsedMemMB() + " MB");
			usedStats1.setWidth(75);
			usedStats2 = new Label(memStats.getUsedCommMemPercent()
					+ "% of committed");
			usedStats2.setWidth(150);
			usedStats3 = new Label(memStats.getUsedMaxMemPercent()
					+ "% of the max");
			usedStats3.setWidth(150);

			HLayout usedStatLayout = new HLayout();
			usedStatLayout.addMember(usedTitle);
			usedStatLayout.addMember(usedStats1);
			usedStatLayout.addMember(usedStats2);
			usedStatLayout.addMember(usedStats3);

			Label commTitle = new Label("Committed memory: ");
			commTitle.setWidth(150);
			commStats1 = new Label(memStats.getCommMemMB() + " MB");
			commStats1.setWidth(75);
			commStats2 = new Label(memStats.getCommMaxMemPercent()
					+ "% of the max");
			commStats2.setWidth(150);

			HLayout commStatLayout = new HLayout();
			commStatLayout.addMember(commTitle);
			commStatLayout.addMember(commStats1);
			commStatLayout.addMember(commStats2);

			Label maxTitle = new Label("Maximum memory: ");
			maxTitle.setWidth(150);
			maxStats = new Label(memStats.getMaxMemMB() + " MB");
			maxStats.setWidth(75);

			HLayout maxStatLayout = new HLayout();
			maxStatLayout.addMember(maxTitle);
			maxStatLayout.addMember(maxStats);

			statistics = new VLayout();
			statistics.addMember(initStatLayout);
			statistics.addMember(usedStatLayout);
			statistics.addMember(commStatLayout);
			statistics.addMember(maxStatLayout);

			Canvas topLeft = new Canvas();
			topLeft.setWidth("50%");
			topLeft.addChild(statistics);

			// create graph selection check boxes
			DynamicForm checkBoxes = new DynamicForm();
			final CheckboxItem initMem = new CheckboxItem();
			initMem.setTitle("Initial Memory");
			initMem.setValue(true);
			initMem.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					Boolean checked = (Boolean) event.getValue();
					chart.updateType("Initial Memory", checked);
				}

			});
			final CheckboxItem usedMem = new CheckboxItem();
			usedMem.setTitle("Used Memory");
			usedMem.setValue(true);
			usedMem.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					Boolean checked = (Boolean) event.getValue();
					chart.updateType("Used Memory", checked);
				}

			});
			final CheckboxItem commMem = new CheckboxItem();
			commMem.setTitle("Committed Memory");
			commMem.setValue(true);
			commMem.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					Boolean checked = (Boolean) event.getValue();
					chart.updateType("Committed Memory", checked);
				}

			});
			final CheckboxItem maxMem = new CheckboxItem();
			maxMem.setTitle("Max Memory");
			maxMem.setValue(true);
			maxMem.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					Boolean checked = (Boolean) event.getValue();
					chart.updateType("Max Memory", checked);
				}

			});
			checkBoxes.setFields(new FormItem[] { initMem, usedMem, commMem,
					maxMem });
			checkBoxes.setAlign(Alignment.LEFT);
			checkBoxes.setWidth("250px");

			// add panels to layout
			Canvas topRight = new Canvas();
			topRight.setNoDoubleClicks(true);
			topRight.setWidth("50%");
			topRight.addChild(checkBoxes);

			HLayout top = new HLayout();
			top.setHeight("100px");
			top.setMembers(topLeft, topRight);

			addMember(top);
			addMember(chart);

			// set timer for 3 seconds
			timer.scheduleRepeating(refreshTime);
		}
		// set up thread panels
		else if (monitorType.equals("Thread")) {
			setHeight100();
			setWidth100();

			// create thread chart
			chart = new LineChartPanel("Thread", this);

			ArrayList<Integer> thrdStats = chart.getThrdStatistics();

			// create statistics table
			Label thrdCntTitle = new Label("Thread Count: ");
			thrdCntStats = new Label(thrdStats.get(0) + "");

			HLayout thrdCntStatsLayout = new HLayout();
			thrdCntStatsLayout.addMember(thrdCntTitle);
			thrdCntStatsLayout.addMember(thrdCntStats);

			Label thrdPkTitle = new Label("Thread Peak: ");
			thrdPkStats = new Label(thrdStats.get(1) + "");

			HLayout thrdPkStatsLayout = new HLayout();
			thrdPkStatsLayout.addMember(thrdPkTitle);
			thrdPkStatsLayout.addMember(thrdPkStats);

			statistics = new VLayout();
			statistics.addMember(thrdCntStatsLayout);
			statistics.addMember(thrdPkStatsLayout);

			// add panels to layout
			VerticalPanel topPanel = new VerticalPanel();
			topPanel.setHeight("100px");
			topPanel.add(statistics);

			addMember(topPanel);
			addMember(chart);

			// set timer for 3 seconds
			timer.scheduleRepeating(refreshTime);
		} else {
			System.err
					.println("Incorrect monitorType provided. Check initialize() in LONI_Chart.java for errors.");
			return;
		}
	}
}