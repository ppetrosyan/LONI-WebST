package edu.ucla.loni.pipeline.client.MainPage.Preferences;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import edu.ucla.loni.pipeline.client.MainPage.Preferences.Access.AccessTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Advanced.AdvancedTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Executables.ExecutablesTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.General.GeneralTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Grid.GridTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages.PackagesTab;

public class PreferencesTab {

	private VLayout padding;
	
	public PreferencesTab(VLayout padding) {
		this.padding = padding;
	}
	
	public Tab setTab() {
		Tab tabPreferences = new Tab("Preferences");

		VLayout prefLayout = new VLayout();

		// Header with Heading and logout button
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(20);
		headLayout.setSize("100%", "2%");

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("50%", "100%");
		headLayout.addMember(padding);

		Button refreshConfig = new Button("Refresh Config");
		refreshConfig.setAlign(Alignment.CENTER);
		refreshConfig.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Refresh config logic
			}
		});
		headLayout.addMember(refreshConfig);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);

		Button saveConfig = new Button("Save Config");
		saveConfig.setAlign(Alignment.CENTER);
		saveConfig.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Save Config logic
			}
		});
		headLayout.addMember(saveConfig);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);

		headLayout.draw();
		prefLayout.addMember(headLayout);

		// Body with tabs
		TabSet tabSet = new TabSet();
		tabSet.setSize("100%", "100%");
		tabSet.setPaneMargin(10);

		GeneralTab generalTab = new GeneralTab();
		tabSet.addTab(generalTab.setTab());
		
		GridTab gridTab = new GridTab();
		tabSet.addTab(gridTab.setTab());
		
		AccessTab accessTab = new AccessTab();
		tabSet.addTab(accessTab.setTab());
		
		PackagesTab packagesTab = new PackagesTab();
		tabSet.addTab(packagesTab.setTab());
		
		ExecutablesTab executablesTab = new ExecutablesTab();
		tabSet.addTab(executablesTab.setTab());
		
		AdvancedTab advancedTab = new AdvancedTab();
		tabSet.addTab(advancedTab.setTab());

		tabSet.draw();
		prefLayout.addMember(tabSet);
		prefLayout.draw();

		tabPreferences.setPane(prefLayout);
		return tabPreferences;
	}
}
