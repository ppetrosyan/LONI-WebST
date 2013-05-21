package edu.ucla.loni.pipeline.client.Charts;

import java.util.ArrayList;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;


public class LONI_Chart extends VLayout {

	private LineChartPanel chart;
	private String monitorType;
	private FlexTable statistics;

	// timer redraws charts and updates statistics
	private Timer timer = new Timer() {
		public void run() {
			try {
				chart.updateValues();

				if(monitorType.equals("Memory")) {
					MemoryStatistics stats = chart.getMemStatistics();
					statistics.setText(0, 0,  "Initial memory:  ");
					statistics.setText(0, 1, stats.getInitMemMB() + " MB");
					statistics.setText(1, 0, "Used memory:  ");
					statistics.setText(1, 1, stats.getUsedMemMB() + " MB");
					statistics.setText(1, 2, stats.getUsedCommMemPercent() + "% of committed");
					statistics.setText(1, 3, stats.getUsedMaxMemPercent() + "% of the max");
					statistics.setText(2, 0, "Committed memory:  ");
					statistics.setText(2, 1, stats.getCommMemMB() + " MB");
					statistics.setText(2, 2, stats.getCommMaxMemPercent() + "% of the max");
					statistics.setText(3, 0, "Maximum memory:  ");
					statistics.setText(3, 1, stats.getMaxMemMB() + " MB");
				}
				else if(monitorType.equals("Thread")) {
					ArrayList<Integer> thrdStats = chart.getThrdStatistics();
					statistics.setText(0, 0, "Thread Count:  ");
					statistics.setText(0, 1, thrdStats.get(0) + "");
					statistics.setText(1, 0, "Thread Peak");
					statistics.setText(1, 1, thrdStats.get(1) + "");
				}
				else {
					System.err.println("Incorrect monitorType provided. Check timer.run() in LONI_Chart.java for errors.");
					return;
				}
			} catch (IndexOutOfBoundsException e) {
				System.err.println("Index out of bounds. Simulated data is probably not loaded yet.");
			}
		}
	};

	public LONI_Chart(String mt)
	{
		this.monitorType = mt;
		initialize();
	}

	public LineChartPanel getChart()
	{
		return this.chart;
	}

	private void initialize()
	{
		// set up memory chart panels
		if(this.monitorType.equals("Memory"))
		{
			setHeight100();
			setWidth100();

			// create memory chart
			chart = new LineChartPanel("Memory");

			MemoryStatistics memStats = chart.getMemStatistics();

			// create statistics table
			statistics = new FlexTable();
			statistics.setText(0, 0,  "Initial memory:  ");
			statistics.setText(0, 1, memStats.getInitMemMB() + " MB");
			statistics.setText(1, 0, "Used memory:  ");
			statistics.setText(1, 1, memStats.getUsedMemMB() + " MB");
			statistics.setText(1, 2, memStats.getUsedCommMemPercent() + "% of committed");
			statistics.setText(1, 3, memStats.getUsedMaxMemPercent() + "% of the max");
			statistics.setText(2, 0, "Committed memory:  ");
			statistics.setText(2, 1, memStats.getCommMemMB() + " MB");
			statistics.setText(2, 2, memStats.getCommMaxMemPercent() + "% of the max");
			statistics.setText(3, 0, "Maximum memory:  ");
			statistics.setText(3, 1, memStats.getMaxMemMB() + " MB");
			statistics.setWidth("350px");

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
			checkBoxes.setFields(new FormItem[] { initMem, usedMem, commMem, maxMem });
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

			// set timer for 5 seconds
			timer.scheduleRepeating(5000);
		}
		// set up thread panels
		else if(this.monitorType.equals("Thread"))
		{
			setHeight100();
			setWidth100();

			// create thread chart
			chart = new LineChartPanel("Thread");

			ArrayList<Integer> thrdStats = chart.getThrdStatistics();

			// create statistics table
			statistics = new FlexTable();
			statistics.setText(0, 0, "Thread Count:  ");
			statistics.setText(0, 1, thrdStats.get(0) + "");
			statistics.setText(1, 0, "Thread Peak");
			statistics.setText(1, 1, thrdStats.get(1) + "");

			// add panels to layout
			VerticalPanel topPanel = new VerticalPanel();
			topPanel.setHeight("100px");
			topPanel.add(statistics);

			addMember(topPanel);
			addMember(chart);

			// set timer for 5 seconds
			timer.scheduleRepeating(5000);
		}
		else
		{
			System.err.println("Incorrect monitorType provided. Check initialize() in LONI_Chart.java for errors.");
			return;
		}
	}
}