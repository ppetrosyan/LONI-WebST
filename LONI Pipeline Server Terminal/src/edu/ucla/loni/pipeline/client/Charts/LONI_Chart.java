package edu.ucla.loni.pipeline.client.Charts;

import java.util.ArrayList;

//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;


public class LONI_Chart extends VLayout {

	private LineChartPanel chart;
    private String monitorType;
    private FlexTable statistics;
    
    // timer redraws charts and updates statistics
	private Timer timer = new Timer() {
		public void run() {
			chart.updateValues();
			
			if(monitorType == "Memory") {
				MemoryStatistics stats = chart.getMemStatistics();
				statistics.setText(0, 0,  "Initial memory:  ");
				statistics.setText(0, 1, stats.getInitMemMB() + " MB");
				statistics.setText(1, 0, "Used memory:  ");
				statistics.setText(1, 1, stats.getUsedMemMB() + " MB");
				statistics.setText(1, 2, stats.getUsedCommMemPercent() + "% of committed");
				statistics.setText(1, 3, stats.getUsedMaxMemPercent() + "% of the max");
				statistics.setText(2, 0, "Committed memory:  ");
				statistics.setText(2, 1, stats.getCommMemMB() + " MB");
				statistics.setText(2, 2, stats.getCommMaxMemPercent() + "% of the max");
				statistics.setText(3, 0, "Maximum memory:  ");
				statistics.setText(3, 1, stats.getMaxMemMB() + " MB");
			}
			else if(monitorType == "Thread") {
	    		ArrayList<Integer> thrdStats = chart.getThrdStatistics();
	    		statistics.setText(0, 0, "Thread Count:  ");
	    		statistics.setText(0, 1, thrdStats.get(0) + "");
	    		statistics.setText(1, 0, "Thread Peak");
	    		statistics.setText(1, 1, thrdStats.get(1) + "");
			}
			else {
				// fail
				return;
			}
		}
	};
    
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
    	// set up memory chart panels
    	if(this.monitorType == "Memory")
    	{
    		setHeight100();
    		setWidth100();
    		
    		// create memory chart
    		chart = new LineChartPanel("Memory");
    		
    		MemoryStatistics memStats = chart.getMemStatistics();
    		
    		// create statistics table
			statistics = new FlexTable();
    		statistics.setText(0, 0,  "Initial memory:  ");
			statistics.setText(0, 1, memStats.getInitMemMB() + " MB");
			statistics.setText(1, 0, "Used memory:  ");
			statistics.setText(1, 1, memStats.getUsedMemMB() + " MB");
			statistics.setText(1, 2, memStats.getUsedCommMemPercent() + "% of committed");
			statistics.setText(1, 3, memStats.getUsedMaxMemPercent() + "% of the max");
			statistics.setText(2, 0, "Committed memory:  ");
			statistics.setText(2, 1, memStats.getCommMemMB() + " MB");
			statistics.setText(2, 2, memStats.getCommMaxMemPercent() + "% of the max");
			statistics.setText(3, 0, "Maximum memory:  ");
			statistics.setText(3, 1, memStats.getMaxMemMB() + " MB");
			statistics.setWidth("350px");
			
			Canvas topLeft = new Canvas();
			topLeft.setWidth("50%");
			topLeft.addChild(statistics);
			
			// create graph selection check boxes
			DynamicForm checkBoxes = new DynamicForm();
			final NativeCheckboxItem initMem = new NativeCheckboxItem();
			initMem.setTitle("Initial Memory");
			initMem.setValue(true);
			initMem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					boolean checked = initMem.getValueAsBoolean();
					chart.updateType("Initial Memory", !checked);
				}
				
			});
			final NativeCheckboxItem usedMem = new NativeCheckboxItem();
			usedMem.setTitle("Used Memory");
			usedMem.setValue(true);
			usedMem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					boolean checked = usedMem.getValueAsBoolean();
					chart.updateType("Used Memory", !checked);
				}
				
			});
			final NativeCheckboxItem commMem = new NativeCheckboxItem();
			commMem.setTitle("Committed Memory");
			commMem.setValue(true);
			commMem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					boolean checked = commMem.getValueAsBoolean();
					chart.updateType("Committed Memory", !checked);
				}
				
			});
			final NativeCheckboxItem maxMem = new NativeCheckboxItem();
			maxMem.setTitle("Max Memory");
			maxMem.setValue(true);
			maxMem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					boolean checked = maxMem.getValueAsBoolean();
					chart.updateType("Max Memory", !checked);
				}
				
			});
			checkBoxes.setFields(new FormItem[] { initMem, usedMem, commMem, maxMem });
			checkBoxes.setAlign(Alignment.LEFT);
			checkBoxes.setWidth("250px");
			
			// add panels to layout
			Canvas topRight = new Canvas();
			topRight.setNoDoubleClicks(true);
			topRight.setWidth("50%");
			topRight.addChild(checkBoxes);
			
			HLayout top = new HLayout();
			top.setHeight("100px");
			top.setMembers(topLeft, topRight);
    		
			addMember(top);
    		addMember(chart);
    		
    		// set timer for 5 seconds
    		timer.scheduleRepeating(5000);
    	}
    	// set up thread panels
    	else if(this.monitorType == "Thread")
    	{
    		setHeight100();
    		setWidth100();
    		
    		// create thread chart
    		chart = new LineChartPanel("Thread");
    		
    		ArrayList<Integer> thrdStats = chart.getThrdStatistics();
    		
    		// create statistics table
    		statistics = new FlexTable();
    		statistics.setText(0, 0, "Thread Count:  ");
    		statistics.setText(0, 1, thrdStats.get(0) + "");
    		statistics.setText(1, 0, "Thread Peak");
    		statistics.setText(1, 1, thrdStats.get(1) + "");
    		
    		// add panels to layout
    		VerticalPanel topPanel = new VerticalPanel();
    		topPanel.setHeight("100px");
    		topPanel.add(statistics);
    		
    		addMember(topPanel);
    		addMember(chart);
    		
    		// set timer for 5 seconds
    		timer.scheduleRepeating(5000);
    	}
    	else
    	{
    		// fail
    		return;
    	}
    }
}