package edu.ucla.loni.pipeline.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import edu.ucla.loni.pipeline.client.LineChartExample;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {

	private LineChartExample chart = new LineChartExample();
	private VerticalPanel topLeftPanel = new VerticalPanel();
	private VerticalPanel topRightPanel = new VerticalPanel();
	private VerticalPanel bottomLeftPanel = new VerticalPanel();
	private VerticalPanel bottomRightPanel = new VerticalPanel();
	private HorizontalPanel browse = new HorizontalPanel();
	private LayoutPanel topPanel = new LayoutPanel();
	private LayoutPanel bottomPanel = new LayoutPanel();
	private FlexTable statistics = new FlexTable();
    private FlexTable checkBoxes = new FlexTable();
    private FlexTable download = new FlexTable();
	private RootLayoutPanel root = RootLayoutPanel.get();
	
    /**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		statistics.setText(0, 0,  "Initial memory:  ");
		statistics.setText(0, 1, "0 MB  ");
		statistics.setText(1, 0, "Used memory:  ");
		statistics.setText(1, 1, "417 MB  ");
		statistics.setText(1, 2, "84% of committed  ");
		statistics.setText(1, 3, "5% of the max  ");
		statistics.setText(2, 0, "Committed memory:  ");
		statistics.setText(2, 1, "495 MB  ");
		statistics.setText(2, 2, "6% of the max  ");
		statistics.setText(3, 0, "Maximum memory:  ");
		statistics.setText(3, 1, "7281 MB  ");
		topLeftPanel.add(statistics);
		
		checkBoxes.setText(0, 0, "Select Visible Graphs:");
		checkBoxes.setWidget(0, 1, new CheckBox("Initial Memory"));
		checkBoxes.setWidget(1, 1, new CheckBox("Used Memory"));
		checkBoxes.setWidget(2, 1, new CheckBox("Committed Memory"));
		checkBoxes.setWidget(3, 1, new CheckBox("Max Memory"));
		topRightPanel.add(checkBoxes);

		topPanel.add(topLeftPanel);
		topPanel.add(topRightPanel);
		topPanel.setWidgetLeftWidth(topLeftPanel, 0, Unit.PCT, 50, Unit.PCT);
		topPanel.setWidgetRightWidth(topRightPanel, 0, Unit.PCT, 50, Unit.PCT);
		chart.addNorth(topPanel, 10);
		
		download.setText(0, 0, "Download a log of the memory statistics:");
		browse.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		browse.add(new Label("Download Location:  "));
		browse.add(new TextBox());
		browse.add(new Button("Browse"));
		download.setWidget(1, 0, browse);
		download.setWidget(2, 0, new Button("Download"));
		download.setText(3, 0, "Connected to medulla.loni.ucla.edu (Server version 5.6 (105.7.0), running on Firefox, HOME SKILLET BISCUIT!!)");
		bottomLeftPanel.add(download);
		
		bottomRightPanel.add(new Button("Perform Garbage Collection"));
		bottomPanel.add(bottomLeftPanel);
		bottomPanel.add(bottomRightPanel);
		bottomPanel.setWidgetLeftWidth(bottomLeftPanel, 0, Unit.PCT, 50, Unit.PCT);
		bottomPanel.setWidgetRightWidth(bottomRightPanel, 0, Unit.PCT, 50, Unit.PCT);
		chart.addSouth(bottomPanel, 10);
		
		root.add(chart);
		
	}
}
