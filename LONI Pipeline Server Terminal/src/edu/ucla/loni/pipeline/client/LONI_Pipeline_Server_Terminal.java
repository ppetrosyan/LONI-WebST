package edu.ucla.loni.pipeline.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import edu.ucla.loni.pipeline.client.LONI_Chart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {
	
    /**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		// instantiate memory and thread charts
		LONI_Chart memoryChart = new LONI_Chart("Memory");
		LONI_Chart threadChart = new LONI_Chart("Thread");
		
		final TabLayoutPanel tab = new TabLayoutPanel(1.5, Unit.EM);
		tab.add(memoryChart.getChart(), "Memory");
		tab.add(threadChart.getChart(), "Thread");
		tab.addSelectionHandler(new SelectionHandler<Integer>(){
			public void onSelection(SelectionEvent<Integer> event){
				int tabId = event.getSelectedItem();
				LineChartPanel tabChart = (LineChartPanel) tab.getWidget(tabId);
				tabChart.redraw();
			}
		});
		RootLayoutPanel.get().add(tab);
		
		// and then do nothing :p
		return;

	}
}
