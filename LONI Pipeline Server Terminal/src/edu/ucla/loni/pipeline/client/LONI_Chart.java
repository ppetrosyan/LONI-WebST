package edu.ucla.loni.pipeline.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.layout.VLayout;

public class LONI_Chart extends VLayout {

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
    		// TODO: use smartgwt stuffs
    		chart = new LineChartPanel("Memory");
    		VerticalPanel topLeftPanel = new VerticalPanel();
    		VerticalPanel topRightPanel = new VerticalPanel();
    		LayoutPanel topPanel = new LayoutPanel();
    		new LayoutPanel();
    		FlexTable statistics = new FlexTable();
    	    FlexTable checkBoxes = new FlexTable();
    	    
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
			setter.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					boolean checked = ((CheckBox) event.getSource()).isChecked();
					chart.updateType("Initial Memory", checked);
				}
			});
			checkBoxes.setWidget(1, 1, new CheckBox("Used Memory"));
			setter = (CheckBox) checkBoxes.getWidget(1, 1);
			setter.setChecked(true);
			setter.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					boolean checked = ((CheckBox) event.getSource()).isChecked();
					chart.updateType("Used Memory", checked);
				}
			});
			checkBoxes.setWidget(2, 1, new CheckBox("Committed Memory"));
			setter = (CheckBox) checkBoxes.getWidget(2, 1);
			setter.setChecked(true);
			setter.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					boolean checked = ((CheckBox) event.getSource()).isChecked();
					chart.updateType("Committed Memory", checked);
				}
			});
			checkBoxes.setWidget(3, 1, new CheckBox("Max Memory"));
			setter = (CheckBox) checkBoxes.getWidget(3, 1);
			setter.setChecked(true);
			setter.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					boolean checked = ((CheckBox) event.getSource()).isChecked();
					chart.updateType("Max Memory", checked);
				}
			});
			topRightPanel.add(checkBoxes);
	
			topPanel.add(topLeftPanel);
			topPanel.add(topRightPanel);
			topPanel.setWidgetLeftWidth(topLeftPanel, 0, Unit.PCT, 50, Unit.PCT);
			topPanel.setWidgetRightWidth(topRightPanel, 0, Unit.PCT, 50, Unit.PCT);
			//chart.addNorth(topPanel, 10);
			addMember(topPanel);
    		addMember(chart);
    	}
    	else if(this.monitorType == "Thread")
    	{
    		// TODO: use smartgwt stuffs
    		chart = new LineChartPanel("Thread");
    		VerticalPanel topPanel = new VerticalPanel();
    		FlexTable statistics = new FlexTable();
    		
    		statistics.setText(0, 0, "Thread Count:  ");
    		statistics.setText(0, 1, "211");
    		statistics.setText(1, 0, "Thread Peak");
    		statistics.setText(1, 1, "440");
    		
    		topPanel.add(statistics);
    		
    		//chart.addNorth(topPanel, 10);
    		addMember(topPanel);
    		addMember(chart);
    	}
    	else
    	{
    		// fail
    		return;
    	}
    }
}
