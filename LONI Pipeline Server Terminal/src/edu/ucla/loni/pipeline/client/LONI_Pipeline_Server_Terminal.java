package edu.ucla.loni.pipeline.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {
	
	/**
	 * This is the entry point method. This is generated and managed by the
	 * visual designer.
	 */
	public void onModuleLoad() {
		
		LONI_Pipeline_ST_Tabset_Display wbst = new LONI_Pipeline_ST_Tabset_Display();
		wbst.buildMainPage(null, false);
	
	}
}