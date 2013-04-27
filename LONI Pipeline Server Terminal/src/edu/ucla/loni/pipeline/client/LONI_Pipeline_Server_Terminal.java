package edu.ucla.loni.pipeline.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import edu.ucla.loni.pipeline.client.LineChartExample;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel subPanel = new HorizontalPanel();
	private LineChartExample chart = new LineChartExample();
	private RootLayoutPanel root = RootLayoutPanel.get();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		subPanel.getElement().setAttribute("align", "center");
		subPanel.add(chart);
		mainPanel.getElement().setAttribute("align", "center");
		mainPanel.add(subPanel);
		//RootPanel.get("linechartexample").add(mainPanel);
		root.add(chart);
		
	}
}
