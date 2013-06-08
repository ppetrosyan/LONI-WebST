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

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.smartgwt.client.widgets.layout.Layout;

public class LineChartPanel extends Layout {

	private final LONI_Chart loniChart;
	private LineChart chart;
	private String monitorType;
	private String color;
	private final int maxEntries;
	private int start;
	private int end;
	private final LineChartOptions options;

	private final ArrayList<String> type;
	// private final ArrayList<Integer> times;

	private final ArrayList<Integer> initMem;
	private final ArrayList<Integer> usedMem;
	private final ArrayList<Integer> commMem;
	private final ArrayList<Integer> maxMem;
	private final ArrayList<Integer> threadCnt;
	private final ArrayList<Integer> threadPk;

	private MemoryStatistics memStats;
	private final ArrayList<Integer> thrdStats;

	private boolean timeUsed;
	private boolean initUsed;
	private boolean usedUsed;
	private boolean commUsed;
	private boolean maxUsed;
	private boolean threadUsed;

	public LineChartPanel(String mt, LONI_Chart loniChart) {
		this.loniChart = loniChart;
		color = "D9F6FA";
		maxEntries = 10;
		start = 0;
		end = maxEntries;

		options = LineChartOptions.create();
		type = new ArrayList<String>();
		// times = new ArrayList<Integer>();

		initMem = new ArrayList<Integer>();
		usedMem = new ArrayList<Integer>();
		commMem = new ArrayList<Integer>();
		maxMem = new ArrayList<Integer>();
		threadCnt = new ArrayList<Integer>();
		threadPk = new ArrayList<Integer>();

		memStats = new MemoryStatistics();
		thrdStats = new ArrayList<Integer>();

		timeUsed = false;
		initUsed = false;
		usedUsed = false;
		commUsed = false;
		maxUsed = false;
		threadUsed = false;

		initialize(mt);
	}

	@Override
	public void redraw() {
		draw();
	}

	private void parseXML(String xml) {
		// remove whitespace
		String cleanXml = xml.replaceAll("\t", "");
		cleanXml.replaceAll("\n", "");

		try {
			Document doc = XMLParser.parse(cleanXml);

			if (monitorType.equals("Memory")) {
				// parse MemoryUsage tree
				Node memRoot = doc.getElementsByTagName("MemoryUsage").item(0);
				if (memRoot == null) {
					System.out.println("couldn't find MemoryUsage tag");
					return;
				}
				NodeList memPoints = memRoot.getChildNodes();

				for (int i = 0; i < memPoints.getLength(); i++) {
					Node stepNode = memPoints.item(i);
					/*
					 * NamedNodeMap memPointAttr = stepNode.getAttributes();
					 * Node stepAttrNode = memPointAttr.getNamedItem("ID");
					 * times.add(Integer.parseInt(stepAttrNode.getNodeValue()));
					 */

					Node initNode = stepNode.getFirstChild();
					initMem.add(Integer.parseInt(initNode.getFirstChild()
							.getNodeValue()));

					Node usedNode = initNode.getNextSibling();
					usedMem.add(Integer.parseInt(usedNode.getFirstChild()
							.getNodeValue()));

					Node commNode = usedNode.getNextSibling();
					commMem.add(Integer.parseInt(commNode.getFirstChild()
							.getNodeValue()));

					Node maxNode = commNode.getNextSibling();
					maxMem.add(Integer.parseInt(maxNode.getFirstChild()
							.getNodeValue()));
				}

				timeUsed = true;
				initUsed = true;
				usedUsed = true;
				commUsed = true;
				maxUsed = true;

				calculateStatistics();
			} else if (monitorType.equals("Thread")) {
				// parse ThreadUsage tree
				Node threadRoot = doc.getElementsByTagName("ThreadUsage").item(
						0);
				if (threadRoot == null) {
					System.out.println("couldn't find ThreadUsage tag");
					return;
				}
				NodeList threadPoints = threadRoot.getChildNodes();

				for (int i = 0; i < threadPoints.getLength(); i++) {
					Node stepNode = threadPoints.item(i);
					/*
					 * NamedNodeMap threadPointAttr = stepNode.getAttributes();
					 * Node stepAttrNode = threadPointAttr.getNamedItem("ID");
					 * times.add(Integer.parseInt(stepAttrNode.getNodeValue()));
					 */

					Node cntNode = stepNode.getFirstChild();
					threadCnt.add(Integer.parseInt(cntNode.getFirstChild()
							.getNodeValue()));

					Node pkNode = cntNode.getNextSibling();
					threadPk.add(Integer.parseInt(pkNode.getFirstChild()
							.getNodeValue()));
				}

				timeUsed = true;
				threadUsed = true;

				calculateStatistics();
			} else {
				System.err
						.println("Incorrect monitorType provided. Check parseXML() in LineChartPanel.java for errors.");
				return;
			}
		} catch (DOMParseException e) {
			System.err
					.println("Could not parse XML file. Check XML file format.");
			return;
		}
	}

	public void refreshChart(String xml) {
		// clear existing chart variables first
		if (monitorType.equals("Memory")) {
			color = "D9F6FA";
			start = 0;
			end = maxEntries;

			// times.clear();
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
		} else if (monitorType.equals("Thread")) {
			color = "D9F6FA";
			start = 0;
			end = maxEntries;

			// times.clear();
			threadCnt.clear();
			threadPk.clear();
			thrdStats.clear();
			timeUsed = false;
			threadUsed = false;
		} else {
			System.err
					.println("Incorrect monitorType provided. Check refreshChart() in LineChartPanel.java for errors.");
			return;
		}
		parseXML(xml);
		redraw();
	}

	public MemoryStatistics getMemStatistics() {
		return memStats;
	}

	public ArrayList<Integer> getThrdStatistics() {
		return thrdStats;
	}

	private void setColor() {
		// memory color
		if (usedUsed) {
			int tmp = 0;
			if ((end % initMem.size()) != 0) {
				tmp = (end % initMem.size()) - 1;
			} else {
				tmp = initMem.size() - 1;
			}

			// yellow
			if (usedMem.get(tmp) > .3 * maxMem.get(tmp)
					&& usedMem.get(tmp) <= .8 * maxMem.get(tmp)) {
				color = "FFFF66";
			} else if (usedMem.get(tmp) > .8 * maxMem.get(tmp)) {
				color = "EDDAE2";
				// blue
			} else {
				color = "D9F6FA";
			}
		}
		// thread color
		else if (threadUsed) {
			int tmp = 0;
			if ((end % threadCnt.size()) != 0) {
				tmp = (end % threadCnt.size()) - 1;
			} else {
				tmp = threadCnt.size() - 1;
			}

			// yellow
			if (threadCnt.get(tmp) > .3 * threadPk.get(tmp)
					&& threadCnt.get(tmp) <= .8 * threadPk.get(tmp)) {
				color = "FFFF66";
			} else if (threadCnt.get(tmp) > .8 * threadPk.get(tmp)) {
				color = "EDDAE2";
				// blue
			} else {
				color = "D9F6FA";
			}
		}
		// default to blue
		else {
			color = "D9F6FA";
		}
	}

	private void calculateStatistics() {
		// calculate memory statistics
		if (monitorType.equals("Memory") && !initMem.isEmpty()
				&& !usedMem.isEmpty() && !commMem.isEmpty()
				&& !maxMem.isEmpty()) {

			int tmp = 0;
			if ((end % initMem.size()) != 0) {
				tmp = (end % initMem.size()) - 1;
			} else {
				tmp = initMem.size() - 1;
			}

			int currInitMem = initMem.get(tmp);
			int currUsedMem = usedMem.get(tmp);
			int currCommMem = commMem.get(tmp);
			int currMaxMem = maxMem.get(tmp);

			memStats.setInitMemMB(currInitMem);
			memStats.setUsedMemMB(currUsedMem);
			memStats.setCommMemMB(currCommMem);
			memStats.setMaxMemMB(currMaxMem);

			int usedCommPercent = (int) ((double) currUsedMem
					/ (double) currCommMem * 100.0);
			int usedMaxPercent = (int) ((double) currUsedMem
					/ (double) currMaxMem * 100.0);
			int commMaxPercent = (int) ((double) currCommMem
					/ (double) currMaxMem * 100.0);

			memStats.setUsedCommMemPercent(usedCommPercent);
			memStats.setUsedMaxMemPercent(usedMaxPercent);
			memStats.setCommMaxMemPercent(commMaxPercent);
		}
		// calculate thread statistics
		else if (monitorType.equals("Thread") && !threadCnt.isEmpty()
				&& !threadPk.isEmpty()) {
			int tmp = 0;
			if ((end % threadCnt.size()) != 0) {
				tmp = (end % threadCnt.size()) - 1;
			} else {
				tmp = threadCnt.size() - 1;
			}
			thrdStats.clear();
			thrdStats.add(threadCnt.get(tmp));
			thrdStats.add(threadPk.get(tmp));
		} else if (monitorType != "Thread" && monitorType != "Memory") {
			System.err
					.println("monitorType invalid. Check calculateStatistics() in LineChartPanel.java for errors");
			return;
		} else {
			// System.err.println("One or more of the chart arrays are empty. Try populating fields with data first via the upload tab.");
			return;
		}
	}

	public void updateValues() {
		try {
			// increment start and end if there are more values in arrays
			/*
			 * if ((monitorType.equals("Memory") && end < initMem.size() && end
			 * < usedMem.size() && end < commMem.size() && end < maxMem.size()
			 * && end < times.size()) || ((monitorType.equals("Thread") && end <
			 * threadCnt.size() && end < threadPk.size() && end <
			 * times.size()))) { start++; end++; }
			 */
			/*
			 * start = (start + 1) % times.size(); end = (end + 1) %
			 * times.size();
			 */
			start++;
			end++;
			calculateStatistics();
			redraw();
			loniChart.refreshStats();
		} catch (Exception e) {
			System.err.println("Could not update values.");
			return;
		}
	}

	public void updateType(String typeChange, boolean checked) {
		// add checked graphs
		if (checked) {
			if (typeChange == "Initial Memory" && !initUsed) {
				initUsed = true;
			} else if (typeChange == "Used Memory" && !usedUsed) {
				usedUsed = true;
			} else if (typeChange == "Committed Memory" && !commUsed) {
				commUsed = true;
			} else if (typeChange == "Max Memory" && !maxUsed) {
				maxUsed = true;
			} else {
				System.out.println("typeChange invalid or type already used");
				return;
			}
		}
		// remove unchecked graphs
		else if (!checked && type.indexOf(typeChange) != -1) {
			if (typeChange == "Initial Memory" && initUsed) {
				initUsed = false;
			} else if (typeChange == "Used Memory" && usedUsed) {
				usedUsed = false;
			} else if (typeChange == "Committed Memory" && commUsed) {
				commUsed = false;
			} else if (typeChange == "Max Memory" && maxUsed) {
				maxUsed = false;
			} else {
				System.out.println("typeChange invalid or type already unused");
				return;
			}
		} else {
			System.err
					.println("Could not find type to update. Check updateType() in LineChartPanel.java for errors.");
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

		if (monitorType.equals("Memory")) {
			initializeMemory();
		} else if (monitorType.equals("Thread")) {
			initializeThread();
		} else {
			System.err
					.println("Incorrect monitorType provided. Check initialize() in LineChartPanel.java for errors.");
			return;
		}

		// set up chart
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				// Create and attach the chart
				chart = new LineChart();
				int height = Window.getClientHeight() - 330;
				int width = Window.getClientWidth() - 75;
				chart.setSize(width + "px", height + "px");

				// listen to resize events
				Window.addResizeHandler(new ResizeHandler() {
					@Override
					public void onResize(ResizeEvent event) {
						int height = event.getHeight() - 330;
						int width = event.getWidth() - 75;
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

	@Override
	public void draw() {
		try {
			// prepare the data
			DataTable dataTable = DataTable.create();
			dataTable.addColumn(ColumnType.NUMBER, "Time");

			for (String t : type) {
				dataTable.addColumn(ColumnType.NUMBER, t);
			}

			// draw next points, if any
			if (timeUsed) {
				dataTable.addRows(maxEntries);
				int next = start;
				for (int t = 0; t < maxEntries; t++) {
					if (next >= end) {
						break;
					}
					// dataTable.setValue(t, 0, times.get(next));
					// next = (next + 1) % times.size();
					dataTable.setValue(t, 0, next);
					next++;
				}
			}

			if (monitorType.equals("Memory")) {
				if (initUsed) {
					int next = start;
					for (int row = 0; row < maxEntries; row++) {
						if (next >= end) {
							break;
						}
						// dataTable.setValue(row, 1, initMem.get(next));
						// next = (next + 1) % times.size();
						dataTable.setValue(row, 1,
								initMem.get(next % initMem.size()));
						next++;
					}
				}
				if (usedUsed) {
					int next = start;
					for (int row = 0; row < maxEntries; row++) {
						if (next >= end) {
							break;
						}
						// dataTable.setValue(row, 2, usedMem.get(next));
						// next = (next + 1) % times.size();
						dataTable.setValue(row, 2,
								usedMem.get(next % usedMem.size()));
						next++;
					}
				}
				if (commUsed) {
					int next = start;
					for (int row = 0; row < maxEntries; row++) {
						if (next >= end) {
							break;
						}
						// dataTable.setValue(row, 3, commMem.get(next));
						// next = (next + 1) % times.size();
						dataTable.setValue(row, 3,
								commMem.get(next % commMem.size()));
						next++;
					}
				}
				if (maxUsed) {
					int next = start;
					for (int row = 0; row < maxEntries; row++) {
						if (next >= end) {
							break;
						}
						// dataTable.setValue(row, 4, maxMem.get(next));
						// next = (next + 1) % times.size();
						dataTable.setValue(row, 4,
								maxMem.get(next % maxMem.size()));
						next++;
					}
				}
			} else if (monitorType.equals("Thread")) {
				if (threadUsed) {
					int next = start;
					for (int row = 0; row < maxEntries; row++) {
						if (next >= end) {
							break;
						}
						// dataTable.setValue(row, 1, threadCnt.get(next));
						// next = (next + 1) % times.size();
						dataTable.setValue(row, 1,
								threadCnt.get(next % threadCnt.size()));
						next++;
					}
					next = start;
					for (int row = 0; row < maxEntries; row++) {
						if (next >= end) {
							break;
						}
						// dataTable.setValue(row, 2, threadPk.get(next));
						// next = (next + 1) % times.size();
						dataTable.setValue(row, 2,
								threadPk.get(next % threadPk.size()));
						next++;
					}
				}
			} else {
				System.err
						.println("Incorrect monitorType provided. Check draw() in LineChartPanel.java for errors.");
				return;
			}

			// Draw the chart
			setColor();
			options.setBackgroundColor(color);
			chart.draw(dataTable, options);
		} catch (Exception e) {
			// System.err.println("Could not draw chart. Try populating fields with data first via the upload tab.");
			return;
		}
	}
}