package edu.ucla.loni.pipeline.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final RootPanel rootPanel = RootPanel.get("rootPanel");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);

		TabSet tabSet = new TabSet();
		tabSet.setSize("100%", "100%");
		
		Tab tab_workflows = new Tab("Workflows");
		tabSet.addTab(tab_workflows);
		
		Tab tab_users_online = new Tab("Users Online");
		tabSet.addTab(tab_users_online);
		
		Tab tab_users_usage = new Tab("User Usage");
		tabSet.addTab(tab_users_usage);
		
		Tab tab_memory_usage = new Tab("Memory Usage");
		tabSet.addTab(tab_memory_usage);
		
		Tab tab_thread_usage = new Tab("Thread Usage");
		tabSet.addTab(tab_thread_usage);
		
		Tab tab_preferences = new Tab("Preferences");
		tabSet.addTab(tab_preferences);
		rootPanel.add(tabSet, 0, 0);
	}
}
