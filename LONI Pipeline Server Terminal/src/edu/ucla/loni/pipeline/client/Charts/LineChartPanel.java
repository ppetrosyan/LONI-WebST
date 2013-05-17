package edu.ucla.loni.pipeline.client.Charts;

import java.util.ArrayList;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.smartgwt.client.widgets.layout.Layout;

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

	private boolean timeUsed = false;
	private boolean initUsed = false;
	private boolean usedUsed = false;
	private boolean commUsed = false;
	private boolean maxUsed = false;
	private boolean threadUsed = false;


	public LineChartPanel(String mt) {
		initialize(mt);
	}

	public void redraw() {
		draw();
	}
	
	private void parseXML(String xml) {
		// remove whitespace
		String cleanXml = xml.replaceAll("\t", "");
		cleanXml.replaceAll("\n", "");
		
		Document doc = XMLParser.parse(cleanXml);
		
		if(monitorType == "Memory") {
			// parse MemoryUsage tree
			Node memRoot = (Node) doc.getElementsByTagName("MemoryUsage").item(0);
			if(memRoot == null) {
				System.out.println("couldn't find MemoryUsage tag");
				return;
			}
			NodeList memPoints = memRoot.getChildNodes();

			for(int i = 0; i < memPoints.getLength(); i++) {
				Node stepNode = memPoints.item(i);
				NamedNodeMap memPointAttr = stepNode.getAttributes();
				Node stepAttrNode = memPointAttr.getNamedItem("ID");
				times.add(Integer.parseInt(stepAttrNode.getNodeValue()));
				
				Node initNode = stepNode.getFirstChild();
				initMem.add(Integer.parseInt(initNode.getFirstChild().getNodeValue()));
				
				Node usedNode = initNode.getNextSibling();
				usedMem.add(Integer.parseInt(usedNode.getFirstChild().getNodeValue()));
				
				Node commNode = usedNode.getNextSibling();
				commMem.add(Integer.parseInt(commNode.getFirstChild().getNodeValue()));
				
				Node maxNode = commNode.getNextSibling();
				maxMem.add(Integer.parseInt(maxNode.getFirstChild().getNodeValue()));
			}
			
			timeUsed = true;
			initUsed = true;
			usedUsed = true;
			commUsed = true;
			maxUsed = true;
			
			calculateStatistics();
		}
		else if(monitorType == "Thread") {
			// parse ThreadUsage tree
			Node threadRoot = (Node) doc.getElementsByTagName("ThreadUsage").item(0);
			if(threadRoot == null) {
				System.out.println("couldn't find ThreadUsage tag");
				return;
			}
			NodeList threadPoints = threadRoot.getChildNodes();

			for(int i = 0; i < threadPoints.getLength(); i++) {
				Node stepNode = threadPoints.item(i);
				NamedNodeMap threadPointAttr = stepNode.getAttributes();
				Node stepAttrNode = threadPointAttr.getNamedItem("ID");
				times.add(Integer.parseInt(stepAttrNode.getNodeValue()));
				
				Node cntNode = stepNode.getFirstChild();
				threadCnt.add(Integer.parseInt(cntNode.getFirstChild().getNodeValue()));
				
				Node pkNode = cntNode.getNextSibling();
				threadPk.add(Integer.parseInt(pkNode.getFirstChild().getNodeValue()));
			}
			
			timeUsed = true;
			threadUsed = true;
			
			calculateStatistics();
		}
	}
	
	public void refreshChart(String xml) {
		// clear existing chart variables first
		if(monitorType == "Memory") {
			color = "D9F6FA";
			start = 0;
			end = maxEntries;
	
			times.clear();
			initMem.clear();
			usedMem.clear();
			commMem.clear();
			maxMem.clear();
			memStats = new MemoryStatistics();
	
			timeUsed = false;
			initUsed = false;
			usedUsed = false;
			commUsed = false;
			maxUsed = false;
		}
		else if(monitorType == "Thread") {
			color = "D9F6FA";
			start = 0;
			end = maxEntries;

			times.clear();
			threadCnt.clear();
			threadPk.clear();
			thrdStats.clear();
			timeUsed = false;
			threadUsed = false;
		}
		parseXML(xml);
		redraw();
	}
	
	public MemoryStatistics getMemStatistics() {
		return this.memStats;
	}
	
	public ArrayList<Integer> getThrdStatistics() {
		return this.thrdStats;
	}
	
	private void setColor() {
		if(usedUsed) {
			// yellow
			if(usedMem.get(end - 1) > .3 * maxMem.get(end - 1)
					&& usedMem.get(end - 1) <= .8 * maxMem.get(end - 1))
				color = "FFFF66";
			// red
			else if(usedMem.get(end - 1) > .8 * maxMem.get(end - 1))
				color = "CC6666";
			// blue
			else
				color = "D9F6FA";
		}		
		else if(threadUsed) {
			// yellow
			if(threadCnt.get(end - 1) > .3 * threadPk.get(end - 1)
					&& threadCnt.get(end - 1) <= .8 * threadPk.get(end - 1))
				color = "FFFF66";
			// red
			else if(threadCnt.get(end - 1) > .8 * threadPk.get(end - 1))
				color = "CC6666";
			// blue
			else
				color = "D9F6FA";
		}
		else
			color = "D9F6FA";
	}
	
	private void calculateStatistics() {
		// calculate memory statistics
		if(monitorType == "Memory" && !initMem.isEmpty() && !usedMem.isEmpty() 
				&& !commMem.isEmpty() && !maxMem.isEmpty()) {
			
			int currInitMem = initMem.get(end - 1);
			int currUsedMem = usedMem.get(end -1);
			int currCommMem = commMem.get(end - 1);
			int currMaxMem = maxMem.get(end - 1);
			
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
			thrdStats.add(threadCnt.get(end - 1));
			thrdStats.add(threadPk.get(end - 1));
		}
	}

	public void updateValues() {
		// increment start and end if there are more values in arrays
		if((monitorType == "Memory" && end < initMem.size() && end < usedMem.size() && end < commMem.size() && end < maxMem.size() && end < times.size()) ||
		  ((monitorType == "Thread" && end < threadCnt.size() && end < threadPk.size()&& end < times.size()))) {
			start++;
			end++;
		}
		calculateStatistics();
		redraw();
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

	private void initializeMemory() {
		// add chart types
		type.add("Initial Memory");
		type.add("Used Memory");
		type.add("Committed Memory");
		type.add("Max Memory");

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

		// set chart options
		options.setBackgroundColor(color);
		options.setFontName("Tahoma");
		options.setTitle("Thread Usage");
		options.setHAxis(HAxis.create("Time"));
		options.setVAxis(VAxis.create("Number of Threads"));
		
		// add initial statistics
		thrdStats.add(0);
		thrdStats.add(0);
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
			// fail
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
		
		// draw next points, if any
		if(timeUsed) {
			dataTable.addRows(times.size());
			int next = start;
			for(int t = 0; t < maxEntries; t++) {
				if(next >= end)
					break;
				dataTable.setValue(t, 0, times.get(next));
				next++;
			}
		}
		
		if(monitorType == "Memory") {
			if(initUsed) {
				int next = start;
				for(int row = 0; row < maxEntries; row++) {
					if(next >= end)
						break;
					dataTable.setValue(row, 1, initMem.get(next));
					next++;
				}
			}
			if(usedUsed) {
				int next = start;
				for(int row = 0; row < maxEntries; row++) {
					if(next >= end)
						break;
					dataTable.setValue(row, 2, usedMem.get(next));
					next++;
				}
			}
			if(commUsed) {
				int next = start;
				for(int row = 0; row < maxEntries; row++) {
					if(next >= end)
						break;
					dataTable.setValue(row, 3, commMem.get(next));
					next++;
				}
			}
			if(maxUsed) {
				int next = start;
				for(int row = 0; row < maxEntries; row++) {
					if(next >= end)
						break;
					dataTable.setValue(row, 4, maxMem.get(next));
					next++;
				}
			}
		}
		else if(monitorType == "Thread") {
			if(threadUsed) {
				int next = start;
				for(int row = 0; row < maxEntries; row++) {
					if(next >= end)
						break;
					dataTable.setValue(row, 1, threadCnt.get(next));
					next++;
				}
				next = start;
				for(int row = 0; row < maxEntries; row++) {
					if(next >= end)
						break;
					dataTable.setValue(row, 2, threadPk.get(next));
					next++;
				}
			}
		}
		else
			// fail
			return;

		// Draw the chart
		setColor();
		options.setBackgroundColor(color);
		chart.draw(dataTable, options);
	}
}