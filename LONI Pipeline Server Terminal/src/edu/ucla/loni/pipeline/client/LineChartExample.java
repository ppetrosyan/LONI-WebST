package edu.ucla.loni.pipeline.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

public class LineChartExample extends DockLayoutPanel {
	private LineChart chart;

	public LineChartExample() {
		super(Unit.EM);
		initialize();
	}

	private void initialize() {
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				// Create and attach the chart
				chart = new LineChart();
				add(chart);
				draw();
			}
		});
	}

	private void draw() {
		String[] memory = new String[] { "Initial Memory", "Used Memory", "Committed Memory", "Max Memory" };
		String[] time = new String[] { "4:47:55 PM", "4:48:00 PM", "4:48:05 PM", "4:48:10 PM", "4:48:15 PM", "4:48:20 PM" };
		int[][] values = new int[][] { { 0, 0, 0, 0, 0, 0 },
				{ 417, 417, 417, 417, 417, 417 },
				{ 495, 495, 495, 495, 495, 495 },
				{ 7281, 7281, 7281, 7281, 7281, 7281 } };

		// Prepare the data
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Time");
		for (int i = 0; i < memory.length; i++) {
			dataTable.addColumn(ColumnType.NUMBER, memory[i]);
		}
		dataTable.addRows(time.length);
		for (int i = 0; i < time.length; i++) {
			dataTable.setValue(i, 0, time[i]);
		}
		for (int col = 0; col < values.length; col++) {
			for (int row = 0; row < values[col].length; row++) {
				dataTable.setValue(row, col + 1, values[col][row]);
			}
		}

		// Set options
		LineChartOptions options = LineChartOptions.create();
		options.setBackgroundColor("#f0f0f0");
		options.setFontName("Tahoma");
		options.setTitle("Memory Usage");
		options.setHAxis(HAxis.create("Time"));
		options.setVAxis(VAxis.create("Memory Usage (MB)"));

		// Draw the chart
		chart.draw(dataTable, options);
	}
}