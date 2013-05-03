package edu.ucla.loni.pipeline.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class LONI_Pipeline_Server_Terminal implements EntryPoint {
	
	public void onModuleLoad() {

	    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				// TODO Auto-generated method stub
				
			}
	    });
	    
		// TODO: add click handler
        TabSet tabSet = new TabSet();  
        tabSet.setWidth("100%");  
        tabSet.setHeight("100%");  
  
        Tab smartTab1 = new Tab("Memory Usage");
        LONI_Chart mem = new LONI_Chart("Memory");
        smartTab1.setPane(mem);  
  
        Tab smartTab2 = new Tab("Thread Usage");
        final LONI_Chart thrd = new LONI_Chart("Thread");
        smartTab2.setPane(thrd);
        tabSet.setTabs(smartTab1, smartTab2);
        tabSet.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				thrd.getChart().redraw();
				
			}
        	
        });
  
        tabSet.draw();  
    }
	
}