package edu.ucla.loni.pipeline.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LONI_Chart {

	private LineChartPanel chart;
    private String monitorType;
    
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
    	if(this.monitorType == "Memory")
    	{
    		chart = new LineChartPanel("Memory");
    		VerticalPanel topLeftPanel = new VerticalPanel();
    		VerticalPanel topRightPanel = new VerticalPanel();
    		VerticalPanel bottomLeftPanel = new VerticalPanel();
    		VerticalPanel bottomRightPanel = new VerticalPanel();
    		HorizontalPanel browse = new HorizontalPanel();
    		LayoutPanel topPanel = new LayoutPanel();
    		LayoutPanel bottomPanel = new LayoutPanel();
    		FlexTable statistics = new FlexTable();
    	    FlexTable checkBoxes = new FlexTable();
    	    FlexTable download = new FlexTable();
    	    
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
			
			CheckBox setter;
			checkBoxes.setText(0, 0, "Select Visible Graphs:");
			checkBoxes.setWidget(0, 1, new CheckBox("Initial Memory"));
			setter = (CheckBox) checkBoxes.getWidget(0, 1);
			setter.setChecked(true);
			checkBoxes.setWidget(1, 1, new CheckBox("Used Memory"));
			setter = (CheckBox) checkBoxes.getWidget(1, 1);
			setter.setChecked(true);
			checkBoxes.setWidget(2, 1, new CheckBox("Committed Memory"));
			setter = (CheckBox) checkBoxes.getWidget(2, 1);
			setter.setChecked(true);
			checkBoxes.setWidget(3, 1, new CheckBox("Max Memory"));
			setter = (CheckBox) checkBoxes.getWidget(3, 1);
			setter.setChecked(true);
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
    	}
    	else if(this.monitorType == "Thread")
    	{
    		chart = new LineChartPanel("Thread");
    		VerticalPanel topPanel = new VerticalPanel();
    		FlexTable statistics = new FlexTable();
    		
    		statistics.setText(0, 0, "Thread Count:  ");
    		statistics.setText(0, 1, "211");
    		statistics.setText(1, 0, "Thread Peak");
    		statistics.setText(1, 1, "440");
    		
    		topPanel.add(statistics);
    		
    		chart.addNorth(topPanel, 10);
    	}
    	else
    	{
    		// fail
    		return;
    	}
    }
}
