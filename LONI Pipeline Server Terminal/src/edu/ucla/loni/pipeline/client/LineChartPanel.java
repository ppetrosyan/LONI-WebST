package edu.ucla.loni.pipeline.client;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;


public class LineChartPanel extends DockLayoutPanel {
	
	private LineChart chart;
	private String monitorType;
	private String color = "D9F6FA";
	private int maxEntries = 10;
	private int start = 0;
	private int end = maxEntries;
	private LineChartOptions options = LineChartOptions.create();
	
	private ArrayList<String> type = new ArrayList<String>();
	private ArrayList<Integer> times = new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>();
	
	private ArrayList<Integer> initMem = new ArrayList<Integer>();
	private ArrayList<Integer> usedMem = new ArrayList<Integer>();
	private ArrayList<Integer> commMem = new ArrayList<Integer>();
	private ArrayList<Integer> maxMem = new ArrayList<Integer>();
	
	private boolean initUsed = false;
	private boolean usedUsed = false;
	private boolean commUsed = false;
	private boolean maxUsed = false;
	
	private Timer timer = new Timer() {
		public void run() {
			times.remove(times.indexOf(start));
			times.add(end);
			if(initUsed) {
				values.get(0).remove(0);
				values.get(0).add(Random.nextInt(7281));
			}
			if(usedUsed) {
				values.get(1).remove(0);
				values.get(1).add(Random.nextInt(7281));
			}
			if(commUsed) {
				values.get(2).remove(0);
				values.get(2).add(Random.nextInt(7281));
			}
			if(maxUsed) {
				values.get(3).remove(0);
				values.get(3).add(Random.nextInt(7281));
			}
			if(monitorType == "Thread") {
				values.get(0).remove(0);
				values.get(0).add(Random.nextInt(440));
				values.get(1).remove(0);
				values.get(1).add(Random.nextInt(440));
			}
			start++;
			end++;
			redraw();
		}
	};
	
	public LineChartPanel(String mt) {
		super(Unit.EM);
		initialize(mt);
	}
	
	public void redraw() {
		if(initUsed) {
			type.remove("Initial Memory");
			values.remove(initMem);
		}
		if(usedUsed) {
			type.remove("Used Memory");
			values.remove(usedMem);
		}			
		if(commUsed) {
			type.remove("Committed Memory");
			values.remove(commMem);
		}
		if(maxUsed) {
			type.remove("Max Memory");
			values.remove(maxMem);
		}
		
		if(initUsed) {
			type.add("Initial Memory");
			values.add(initMem);
		}
		if(usedUsed) {
			type.add("Used Memory");
			values.add(usedMem);
		}
		if(commUsed) {
			type.add("Committed Memory");
			values.add(commMem);
		}
		if(maxUsed) {
			type.add("Max Memory");
			values.add(maxMem);
		}

		draw();
	}
	
	public void updateValues() {
		// TODO: stuff
	}
	
	public void updateType(String typeChange, boolean checked) {
		if(checked && type.indexOf(typeChange) == -1) {
			if(typeChange == "Initial Memory") {
				type.add("Initial Memory");
				values.add(initMem);
				initUsed = true;
			}
			else if(typeChange == "Used Memory") {
				type.add("Used Memory");
				values.add(usedMem);
				usedUsed = true;
			}
			else if(typeChange == "Committed Memory") {
				type.add("Committed Memory");
				values.add(commMem);
				commUsed = true;
			}
			else if(typeChange == "Max Memory") {
				type.add("Max Memory");
				values.add(maxMem);
				maxUsed = true;
			}
			else
				// fail
				return;
			redraw();
		}
		else if(!checked && type.indexOf(typeChange) != -1) {
			if(typeChange == "Initial Memory") {
				type.remove("Initial Memory");
				values.remove(initMem);
				initUsed = false;
			}
			else if(typeChange == "Used Memory") {
				type.remove("Used Memory");
				values.remove(usedMem);
				usedUsed = false;
			}
			else if(typeChange == "Committed Memory") {
				type.remove("Committed Memory");
				values.remove(commMem);
				commUsed = false;
			}
			else if(typeChange == "Max Memory") {
				type.remove("Max Memory");
				values.remove(maxMem);
				maxUsed = false;
			}
			else
				// fail
				return;
			redraw();
		}
	}
	
	private void initializeMemory() {
		// add chart types
		type.add("Initial Memory");
		type.add("Used Memory");
		type.add("Committed Memory");
		type.add("Max Memory");
			
		// add initial times
		for(int time = 0; time < maxEntries; time++) {
			times.add(time);
		}
			
		// add initial values
		for(int val = 0; val < maxEntries; val++) {
			initMem.add(0);
			usedMem.add(417);
			commMem.add(495);
			maxMem.add(7281);
		}
			
		values.add(initMem);
		values.add(usedMem);
		values.add(commMem);
		values.add(maxMem);
		
		initUsed = true;
		usedUsed = true;
		commUsed = true;
		maxUsed = true;
			
		// set chart options
		options.setBackgroundColor(color);
		options.setFontName("Tahoma");
		options.setTitle("Memory Usage");
		options.setHAxis(HAxis.create("Time"));
		options.setVAxis(VAxis.create("Memory Usage (MB)"));
	}
	
	private void initializeThread() {
		// add chart types
		type.add("Thread Count");
		type.add("Thread Peak");
		
		// add initial times
		for(int time = 0; time < maxEntries; time++) {
			times.add(time);
		}
		
		// add initial values
		ArrayList<Integer> threadCnt = new ArrayList<Integer>();
		ArrayList<Integer> threadPk = new ArrayList<Integer>();
		
		for(int val = 0; val < maxEntries; val++) {
			threadCnt.add(211);
			threadPk.add(440);
		}
		
		values.add(threadCnt);
		values.add(threadPk);
		
		// set chart options
		options.setBackgroundColor(color);
		options.setFontName("Tahoma");
		options.setTitle("Thread Usage");
		options.setHAxis(HAxis.create("Time"));
		options.setVAxis(VAxis.create("Number of Threads"));	
	}

	private void initialize(String mt) {
		monitorType = mt;
		
		if(monitorType == "Memory") {
			timer.scheduleRepeating(5000);
			initializeMemory();
		}
		else if(monitorType == "Thread") {
			timer.scheduleRepeating(5000);
			initializeThread();
		}
		else {
			// error
			return;
		}
		
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

	public void draw() {
		// prepare the data
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.NUMBER, "Time");
		for(String mem : type) {
			dataTable.addColumn(ColumnType.NUMBER, mem);
		}
		dataTable.addRows(times.size());
		for(int t = 0; t < maxEntries; t++) {
			dataTable.setValue(t, 0, times.get(t));
		}
		for (int col = 0; col < values.size(); col++) {
			for (int row = 0; row < values.get(col).size(); row++) {
				dataTable.setValue(row, col + 1, values.get(col).get(row));
			}
		}
			
		// Draw the chart
		chart.draw(dataTable, options);
	}
}