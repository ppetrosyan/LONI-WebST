package edu.ucla.loni.pipeline.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
/*import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;*/
import edu.ucla.loni.pipeline.client.LONI_Chart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {

	/*private LineChartPanel chart = new LineChartPanel("Memory");
	private VerticalPanel topLeftPanel = new VerticalPanel();
	private VerticalPanel topRightPanel = new VerticalPanel();
	private VerticalPanel bottomLeftPanel = new VerticalPanel();
	private VerticalPanel bottomRightPanel = new VerticalPanel();
	private HorizontalPanel browse = new HorizontalPanel();
	private LayoutPanel topPanel = new LayoutPanel();
	private LayoutPanel bottomPanel = new LayoutPanel();
	private FlexTable statistics = new FlexTable();
    private FlexTable checkBoxes = new FlexTable();
    private FlexTable download = new FlexTable();*/
	private RootLayoutPanel root = RootLayoutPanel.get();
	private TabLayoutPanel tab = new TabLayoutPanel(1.5, Unit.EM);
	
    /**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		// instantiate memory chart
		//LONI_Chart chart = new LONI_Chart(root, "Memory");
		//LONI_Chart chart = new LONI_Chart(root, "Thread");
		LONI_Chart memoryChart = new LONI_Chart("Memory");
		LONI_Chart threadChart = new LONI_Chart("Thread");
		tab.add(memoryChart.getChart(), "Memory");
		tab.add(threadChart.getChart(), "Thread");
		tab.selectTab(0);
		root.add(tab);
		
		// and then do nothing :p
		return;

	}
}
