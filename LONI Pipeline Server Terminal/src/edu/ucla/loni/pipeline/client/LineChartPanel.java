package edu.ucla.loni.pipeline.client;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

// TODO: add error messages to all 'fail' cases
public class LineChartPanel extends Layout {
	
	private LineChart chart;
	private String monitorType;
	private String color = "D9F6FA";
	private int maxEntries = 10;
	private int start = 0;
	private int end = maxEntries;
	private LineChartOptions options = LineChartOptions.create();
	
	private ArrayList<String> type = new ArrayList<String>();
	private ArrayList<Integer> times = new ArrayList<Integer>();
	
	private ArrayList<Integer> initMem = new ArrayList<Integer>();
	private ArrayList<Integer> usedMem = new ArrayList<Integer>();
	private ArrayList<Integer> commMem = new ArrayList<Integer>();
	private ArrayList<Integer> maxMem = new ArrayList<Integer>();
	private ArrayList<Integer> threadCnt = new ArrayList<Integer>();
	private ArrayList<Integer> threadPk = new ArrayList<Integer>();
	
	private boolean initUsed = false;
	private boolean usedUsed = false;
	private boolean commUsed = false;
	private boolean maxUsed = false;
	
	private Timer timer = new Timer() {
		public void run() {
			// TODO: move this timer to LONI_Chart
			times.remove(times.indexOf(start));
			times.add(end);
			if(initUsed) {
				initMem.remove(0);
				initMem.add(Random.nextInt(7281));
			}
			if(usedUsed) {
				usedMem.remove(0);
				usedMem.add(Random.nextInt(7281));
			}
			if(commUsed) {
				commMem.remove(0);
				commMem.add(Random.nextInt(7281));
			}
			if(maxUsed) {
				maxMem.remove(0);
				maxMem.add(Random.nextInt(7281));
			}
			if(monitorType == "Thread") {
				threadCnt.remove(0);
				threadCnt.add(Random.nextInt(440));
				threadPk.remove(0);
				threadPk.add(Random.nextInt(440));
			}
			start++;
			end++;
			redraw();
		}
	};
	
	public LineChartPanel(String mt) {
		//super(Unit.EM);
		initialize(mt);
	}
	
	public void redraw() {
		draw();
	}
	
	public void updateValues() {
		// TODO: add diff start/end for all plots (?)
		// TODO: call updateValues from timer in LONI_Chart
		// TODO: calculate percentages and send them back to LONI_Chart
		// TODO: change draw to populate chart from start to end values
		if(end < initMem.size()) {
			start++;
			end++;
			redraw();
		}
	}
	
	public void updateType(String typeChange, boolean checked) {
		if(checked) {
			if(typeChange == "Initial Memory" && !initUsed) {
				initUsed = true;
			}
			else if(typeChange == "Used Memory" && !usedUsed) {
				usedUsed = true;
			}
			else if(typeChange == "Committed Memory" && !commUsed) {
				commUsed = true;
			}
			else if(typeChange == "Max Memory" && !maxUsed) {
				maxUsed = true;
			}
			else
				// fail
				return;
			redraw();
		}
		else if(!checked && type.indexOf(typeChange) != -1) {
			if(typeChange == "Initial Memory" && initUsed) {
				initUsed = false;
			}
			else if(typeChange == "Used Memory" && usedUsed) {
				usedUsed = false;
			}
			else if(typeChange == "Committed Memory" && commUsed) {
				commUsed = false;
			}
			else if(typeChange == "Max Memory" && maxUsed) {
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
			
		/*values.add(initMem);
		values.add(usedMem);
		values.add(commMem);
		values.add(maxMem);*/
		
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
		//ArrayList<Integer> threadCnt = new ArrayList<Integer>();
		//ArrayList<Integer> threadPk = new ArrayList<Integer>();
		
		for(int val = 0; val < maxEntries; val++) {
			threadCnt.add(211);
			threadPk.add(440);
		}
		
		/*values.add(threadCnt);
		values.add(threadPk);*/
		
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
				//add(chart);
				//add(chart, CENTER);
				chart.setSize("100%", "640px");
				//setSize("100%", "100%");
				//addMember(chart);
				addChild(chart);
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
		if(monitorType == "Memory") {
			if(initUsed) {
				// TODO: for(int row = 0, next = start; row < initMem.size(), next < end; row++, next++) {
				//			dataTable.setValue(row, 1, initMem.get(begin);
				// }
				// same for the rest
				for(int row = 0; row < initMem.size(); row++) {
					dataTable.setValue(row, 1, initMem.get(row));
				}
			}
			if(usedUsed) {
				for(int row = 0; row < usedMem.size(); row++) {
					dataTable.setValue(row, 2, usedMem.get(row));
				}
			}
			if(commUsed) {
				for(int row = 0; row < commMem.size(); row++) {
					dataTable.setValue(row, 3, commMem.get(row));
				}
			}
			if(maxUsed) {
				for(int row = 0; row < maxMem.size(); row++) {
					dataTable.setValue(row, 4, maxMem.get(row));
				}
			}
		}
		else if(monitorType == "Thread") {
			for(int row = 0; row < threadPk.size(); row++) {
				dataTable.setValue(row, 1, threadPk.get(row));
			}
			for(int row = 0; row < threadCnt.size(); row++) {
				dataTable.setValue(row, 2, threadCnt.get(row));
			}
		}
		else
			// fail
			return;
			
		// Draw the chart
		chart.draw(dataTable, options);
	}
}