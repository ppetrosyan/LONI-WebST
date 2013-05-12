package edu.ucla.loni.pipeline.client.Charts;

import java.util.ArrayList;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.smartgwt.client.widgets.layout.Layout;

import edu.ucla.loni.pipeline.client.MemoryStatistics;

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
	
	private MemoryStatistics memStats = new MemoryStatistics();
	private ArrayList<Integer> thrdStats = new ArrayList<Integer>();

	private boolean initUsed = false;
	private boolean usedUsed = false;
	private boolean commUsed = false;
	private boolean maxUsed = false;


	// TODO: change constructor to populate ArrayLists with config data
	public LineChartPanel(String mt) {
		//super(Unit.EM);
		initialize(mt);
	}

	public void redraw() {
		draw();
	}
	
	// use random data for testing purposes
	public void testUpdate() {
		times.remove(times.indexOf(start));
		times.add(end);
		if(initUsed) {
			//initMem.remove(0);
			//initMem.add(Random.nextInt(7281));
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
			//maxMem.remove(0);
			//maxMem.add(Random.nextInt(7281));
		}
		if(monitorType == "Thread") {
			threadCnt.remove(0);
			threadCnt.add(Random.nextInt(440));
			threadPk.remove(0);
			threadPk.add(Random.nextInt(440));
		}
		start++;
		end++;
		calculateStatistics();
		redraw();
	}
	
	public MemoryStatistics getMemStatistics() {
		return this.memStats;
	}
	
	public ArrayList<Integer> getThrdStatistics() {
		return this.thrdStats;
	}
	
	private void calculateStatistics() {
		// calculate memory statistics
		if(monitorType == "Memory" && !initMem.isEmpty() && !usedMem.isEmpty() 
				&& !commMem.isEmpty() && !maxMem.isEmpty()) {
			
			int currInitMem = initMem.get(initMem.size() - 1);
			int currUsedMem = usedMem.get(usedMem.size() -1);
			int currCommMem = commMem.get(commMem.size() - 1);
			int currMaxMem = maxMem.get(maxMem.size() - 1);
			
			memStats.setInitMemMB(currInitMem);
			memStats.setUsedMemMB(currUsedMem);
			memStats.setCommMemMB(currCommMem);
			memStats.setMaxMemMB(currMaxMem);
			
			int usedCommPercent = (int) ((double) currUsedMem / (double) currCommMem * 100.0);
			int usedMaxPercent = (int) ((double) currUsedMem / (double) currMaxMem * 100.0);
			int commMaxPercent = (int) ((double) currCommMem / (double) currMaxMem * 100.0);
			
			memStats.setUsedCommMemPercent(usedCommPercent);
			memStats.setUsedMaxMemPercent(usedMaxPercent);
			memStats.setCommMaxMemPercent(commMaxPercent);
		}
		// calculate thread statistics
		else if(monitorType == "Thread" && !threadCnt.isEmpty() && !threadPk.isEmpty()) {
			thrdStats.clear();
			thrdStats.add(threadCnt.get(threadCnt.size() - 1));
			thrdStats.add(threadPk.get(threadPk.size() - 1));
		}
	}

	public void updateValues() {
		// TODO: add diff start/end for all plots (?)
		// TODO: call updateValues from timer in LONI_Chart
		// TODO: change draw to populate chart from start to end values
		if(end < initMem.size()) {
			start++;
			end++;
			redraw();
		}
	}

	public void updateType(String typeChange, boolean checked) {
		// add checked graphs
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
		}
		// remove unchecked graphs
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
		}
		
		redraw();
	}

	// TODO: change initializers to use actual data from config
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

		// set used flags
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
		
		calculateStatistics();
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
		for(int val = 0; val < maxEntries; val++) {
			threadCnt.add(211);
			threadPk.add(440);
		}

		// set chart options
		options.setBackgroundColor(color);
		options.setFontName("Tahoma");
		options.setTitle("Thread Usage");
		options.setHAxis(HAxis.create("Time"));
		options.setVAxis(VAxis.create("Number of Threads"));
		calculateStatistics();
	}

	private void initialize(String mt) {	
		monitorType = mt;

		if(monitorType == "Memory") {
			initializeMemory();
		}
		else if(monitorType == "Thread") {
			initializeThread();
		}
		else {
			// error
			return;
		}

		// set up chart
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				// Create and attach the chart
				chart = new LineChart();
				int height = Window.getClientHeight() - 200;
				int width = Window.getClientWidth() - 65;
				chart.setSize(width + "px", height + "px");
				
				// listen to resize events
				Window.addResizeHandler(new ResizeHandler() {
					public void onResize(ResizeEvent event) {
						int height = event.getHeight() - 200;
						int width = event.getWidth() - 65;
						chart.setHeight(height + "px");
						chart.setWidth(width + "px");
						draw();
					}
				});
				
				addChild(chart);
				draw();
			}
		});
	}

	public void draw() {
		// prepare the data
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.NUMBER, "Time");
		
		for(String t : type) {
			dataTable.addColumn(ColumnType.NUMBER, t);
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
			for(int row = 0; row < threadCnt.size(); row++) {
				dataTable.setValue(row, 1, threadCnt.get(row));
			}
			for(int row = 0; row < threadPk.size(); row++) {
				dataTable.setValue(row, 2, threadPk.get(row));
			}
		}
		else
			// fail
			return;

		// Draw the chart
		chart.draw(dataTable, options);
	}
}