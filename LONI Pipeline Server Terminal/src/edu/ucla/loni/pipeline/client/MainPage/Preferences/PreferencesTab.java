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

	public static Tab setTab(VLayout padding) {
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

		tabSet.addTab(GeneralTab.setTab());
		tabSet.addTab(GridTab.setTab());
		tabSet.addTab(AccessTab.setTab());
		tabSet.addTab(PackagesTab.setTab());
		tabSet.addTab(ExecutablesTab.setTab());
		tabSet.addTab(AdvancedTab.setTab());

		tabSet.draw();
		prefLayout.addMember(tabSet);
		prefLayout.draw();

		tabPreferences.setPane(prefLayout);
		return tabPreferences;
	}
}
