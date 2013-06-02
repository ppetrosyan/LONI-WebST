package edu.ucla.loni.pipeline.client.MainPage;

import com.smartgwt.client.widgets.tab.TabSet;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Img;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.types.Alignment;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;

import edu.ucla.loni.pipeline.client.MainPage.MemoryUsage.MemoryUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.PreferencesTab;
import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;
import edu.ucla.loni.pipeline.client.MainPage.ThreadUsage.ThreadUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.Upload.UploadTab;
import edu.ucla.loni.pipeline.client.MainPage.UserUsage.UserUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.UsersOnline.UsersOnlineTab;
import edu.ucla.loni.pipeline.client.MainPage.WorkFlows.WorkFlowsTab;
import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;
import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.NotificationMole;

public class LONI_Pipeline_ST_Tabset_Display {

	private String userID;
	private VLayout appLayout;
	private AsyncClientServices asyncClientServices;
	private PreferencesTab preferencesTab;
	private LONINotifications notifications;
	

	public LONI_Pipeline_ST_Tabset_Display(String userID) {
		appLayout = new VLayout();
		
		asyncClientServices = new AsyncClientServices();
		notifications = new LONINotifications();
		this.userID = userID;
	}

	public void buildMainPage() {		
		appLayout.setMembersMargin(0);
		appLayout.setLayoutAlign(Alignment.CENTER);
		appLayout.setAlign(Alignment.CENTER);
		appLayout.setAlign(VerticalAlignment.CENTER);
		appLayout.setHeight100();
		appLayout.setWidth100();
		
		// Header with Heading and logout button
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(10);
		headLayout.setSize("100%", "80px");
		
		// Notification Layout
		HLayout notificationLayout = new HLayout();
		notificationLayout.setSize("100%", "20px");
		notificationLayout.setAlign(Alignment.CENTER);
		notificationLayout.setAlign(VerticalAlignment.CENTER);
		notificationLayout.setLayoutAlign(Alignment.CENTER);
		
		notificationLayout.addMember(notifications.getNotificationMole());

		headLayout.addMember(notificationLayout);
		
		// Logo Layout
		HLayout logoLayout = new HLayout();
		logoLayout.setSize("100%", "*");
		logoLayout.setAlign(Alignment.CENTER);
		logoLayout.setAlign(VerticalAlignment.CENTER);
		logoLayout.setLayoutAlign(Alignment.CENTER);
		
		Img logo = new Img(GWT.getModuleBaseURL() + "../images/pipeline.jpg");
		logo.setHeight("80px");
		logo.setWidth("384px");
		logoLayout.addMember(logo);		

		headLayout.addMember(logoLayout);
			
		// Logout Layout
		HLayout logoutLayout = new HLayout();
		logoutLayout.setSize("100%", "20px");
		logoutLayout.setAlign(Alignment.CENTER);
		logoutLayout.setAlign(VerticalAlignment.CENTER);
		logoutLayout.setLayoutAlign(Alignment.CENTER);
		
		Anchor logoutBtn = new Anchor("LogOut");
		logoutBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				// TODO: Implement Logout Logic
				
			}
			
		});
		
		logoutLayout.addMember(logoutBtn);
		
		headLayout.addMember(logoutLayout);
		
		/*padding.setMembersMargin(0);
		padding.setSize("100%", "100%");
		headLayout.addMember(padding);*/

		

		/*padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);*/

		appLayout.addMember(headLayout);

		

		// Body with tabs
		HLayout tabLayout = new HLayout();
		tabLayout.setMembersMargin(5);
		tabLayout.setSize("100%", "*");
		
		VLayout padding = new VLayout();
		TabSet tabset = buildtabset(padding);
		tabLayout.addMember(tabset);
		
		appLayout.addMember(tabset);
		appLayout.draw();
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
		WorkFlowsTab workFlowsTab = new WorkFlowsTab(asyncClientServices, notifications);
		tabset.addTab(workFlowsTab.setTab());

		// Users Online tab
		UsersOnlineTab usersOnlineTab = new UsersOnlineTab(asyncClientServices, notifications);
		tabset.addTab(usersOnlineTab.setTab());

		// Users Usage tab
		UserUsageTab userUsageTab = new UserUsageTab(asyncClientServices, notifications);
		tabset.addTab(userUsageTab.setTab());

		// Memory Usage tab
		MemoryUsageTab memoryUsageTab = new MemoryUsageTab(asyncClientServices, notifications);
		tabset.addTab(memoryUsageTab.setTab());

		// Thread Usage tab
		ThreadUsageTab threadUsageTab = new ThreadUsageTab(asyncClientServices, notifications);
		tabset.addTab(threadUsageTab.setTab());

		// Preferences tab
		preferencesTab = new PreferencesTab(padding, asyncClientServices, notifications);
		tabset.addTab(preferencesTab.setTab());

		// Refresh All Tabs
		LONIDataRequester dataRequester = new LONIDataRequester(
				asyncClientServices, memoryUsageTab.getChart(),
				threadUsageTab.getChart(), preferencesTab, workFlowsTab, usersOnlineTab, userUsageTab, notifications);

		// Uploads tab
		UploadTab uploadTab = new UploadTab(dataRequester);
		tabset.addTab(uploadTab.setTab());

		return tabset;
	}
}
