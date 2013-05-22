package edu.ucla.loni.pipeline.client.MainPage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;

import edu.ucla.loni.pipeline.client.MainPage.MemoryUsage.MemoryUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.Notifications.Notification;
import edu.ucla.loni.pipeline.client.MainPage.Notifications.OldNotification;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.PreferencesTab;
import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;
import edu.ucla.loni.pipeline.client.MainPage.ThreadUsage.ThreadUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.Upload.UploadTab;
import edu.ucla.loni.pipeline.client.MainPage.UserUsage.UserUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.UsersOnline.UsersOnlineTab;
import edu.ucla.loni.pipeline.client.MainPage.WorkFlows.WorkFlowsTab;
import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;

public class LONI_Pipeline_ST_Tabset_Display {

	VLayout mainLayout = new VLayout();
	AsyncClientServices asyncClientServices;

	public LONI_Pipeline_ST_Tabset_Display() {
		mainLayout.setMembersMargin(20);
		mainLayout.setSize("100%", "100%");
		
		asyncClientServices = new AsyncClientServices();
	}

	public void buildMainPage(String userID, boolean status) {
		// if (!status)
		// return;

		// Header with Heading and logout button
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(10);
		headLayout.setSize("100%", "3%");

		VLayout padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);		

		com.smartgwt.client.widgets.Label LONILabel = new com.smartgwt.client.widgets.Label(
				"<b><font size='6'>LONI Pipeline</font></b>");
		LONILabel.setSize("48%", "100%");
		LONILabel.setAlign(Alignment.LEFT);
		LONILabel.setValign(VerticalAlignment.CENTER);
		headLayout.addMember(LONILabel);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);

		Button logoutBtn = new Button("LogOut");
		logoutBtn.setAlign(Alignment.CENTER);
		logoutBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Logout logic
			}
		});
		headLayout.addMember(logoutBtn);
		
		buildNotifications(headLayout);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);

		headLayout.draw();
		mainLayout.addMember(headLayout);

		// Body with tabs
		HLayout tabLayout = new HLayout();
		tabLayout.setMembersMargin(5);
		tabLayout.setSize("100%", "97%");
		TabSet tabset = buildtabset(padding);
		tabset.draw();
		tabLayout.addMember(tabset);
		tabLayout.draw();
		mainLayout.addMember(tabset);
		
		mainLayout.draw();
		

	}
	
	private void buildNotifications(HLayout headLayout) {
		HTML notif = new HTML();
        if (Notification.isSupported()) {
                notif.setText("Supported "+ Notification.checkPermission());
                Notification.requestPermission();
        } else {
                notif.setText("Unsupported");
        }
        
        Notification notification = Notification.createIfSupported("http://www.gstatic.com/codesite/ph/images/defaultlogo.png");
        
       	headLayout.addMember(notif);
	}

	private TabSet buildtabset(VLayout padding) {
		TabSet tabset = new TabSet();
		tabset.setSize("100%", "100%");
		tabset.setPaneMargin(30);

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				e.printStackTrace();
			}
		});

		// Workflows tab
		WorkFlowsTab workFlowsTab = new WorkFlowsTab();
		tabset.addTab(workFlowsTab.setTab());

		// Users Online tab
		UsersOnlineTab usersOnlineTab = new UsersOnlineTab();
		tabset.addTab(usersOnlineTab.setTab());

		// Users Usage tab
		UserUsageTab userUsageTab = new UserUsageTab();
		tabset.addTab(userUsageTab.setTab());

		// Memory Usage tab
		MemoryUsageTab memoryUsageTab = new MemoryUsageTab(asyncClientServices);
		tabset.addTab(memoryUsageTab.setTab());

		// Thread Usage tab
		ThreadUsageTab threadUsageTab = new ThreadUsageTab(asyncClientServices);
		tabset.addTab(threadUsageTab.setTab());
		
		// Preferences tab
		PreferencesTab preferencesTab = new PreferencesTab(padding);
		tabset.addTab(preferencesTab.setTab());
		
		// Refresh All Tabs
		LONIDataRequester dataRequester = new LONIDataRequester(
				asyncClientServices, memoryUsageTab.getChart(),
				threadUsageTab.getChart());

		// Uploads tab
		UploadTab uploadTab = new UploadTab(dataRequester);
		tabset.addTab(uploadTab.setTab());

		return tabset;
	}
}
