package edu.ucla.loni.pipeline.client.EntryPoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import edu.ucla.loni.pipeline.client.Login.LONI_Pipeline_ST_Login_Display;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {

	private LONI_Pipeline_ST_Login_Display wbsl;

	public LONI_Pipeline_Server_Terminal() {
		wbsl = new LONI_Pipeline_ST_Login_Display();
	}

	/**
	 * This is the entry point method. This is generated and managed by the
	 * visual designer.
	 */
	public void onModuleLoad() {
		// Build Login Page
		wbsl.buildMainPage();
	}
}