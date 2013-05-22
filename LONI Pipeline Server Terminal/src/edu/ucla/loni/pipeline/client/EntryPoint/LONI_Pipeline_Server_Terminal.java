package edu.ucla.loni.pipeline.client.EntryPoint;

import com.google.gwt.core.client.EntryPoint;

import edu.ucla.loni.pipeline.client.Login.LONI_Pipeline_ST_Login_Display;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {
	
	/**
	 * This is the entry point method. This is generated and managed by the
	 * visual designer.
	 */
	public void onModuleLoad() {
		
		// LONI_Pipeline_ST_Tabset_Display wbst = new LONI_Pipeline_ST_Tabset_Display();
		// wbst.buildMainPage(null, false);

		LONI_Pipeline_ST_Login_Display wbsl = new 
			LONI_Pipeline_ST_Login_Display();
		wbsl.buildMainPage();
	
	}
}