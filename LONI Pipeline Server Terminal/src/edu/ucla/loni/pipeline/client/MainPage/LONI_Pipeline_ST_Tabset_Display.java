/*
 * This file is part of LONI Pipeline Web-based Server Terminal.
 * 
 * LONI Pipeline Web-based Server Terminal is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * LONI Pipeline Web-based Server Terminal is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with LONI Pipeline Web-based Server Terminal.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package edu.ucla.loni.pipeline.client.MainPage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;

import edu.ucla.loni.pipeline.client.Login.LONI_Pipeline_ST_Login_Display;
import edu.ucla.loni.pipeline.client.Login.LoginService;
import edu.ucla.loni.pipeline.client.Login.LoginServiceAsync;
import edu.ucla.loni.pipeline.client.Login.SessionId;
import edu.ucla.loni.pipeline.client.Login.UserDTO;
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

public class LONI_Pipeline_ST_Tabset_Display {

	private final VLayout appLayout;
	private final AsyncClientServices asyncClientServices;
	private final LONINotifications notifications;
	private LONI_Pipeline_ST_Login_Display wbsl;

	public LONI_Pipeline_ST_Tabset_Display(String userID) {
		appLayout = new VLayout();

		asyncClientServices = new AsyncClientServices();
		notifications = new LONINotifications();
	}

	public void buildMainPage(final UserDTO user, final SessionId sessionId) {
		appLayout.setMembersMargin(2);
		appLayout.setLayoutAlign(Alignment.CENTER);
		appLayout.setAlign(Alignment.CENTER);
		appLayout.setAlign(VerticalAlignment.CENTER);
		appLayout.setHeight100();
		appLayout.setWidth100();
		appLayout.setBackgroundColor("#EFF4FA");

		// Header with Heading and logout button
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(10);
		headLayout.setSize("100%", "80px");

		Img logo = new Img(GWT.getModuleBaseURL() + "../images/pipeline.jpg");
		logo.setHeight("80px");
		logo.setWidth("384px");
		headLayout.addMember(logo);

		VLayout padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("100%", "100%");
		headLayout.addMember(padding);

		Button logoutBtn = new Button("LogOut");
		logoutBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO: Logout logic
				LoginServiceAsync loginServiceAsync = GWT
						.create(LoginService.class);

				AsyncCallback<String> asyncCallback = new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						System.out.println("logout: onClick(): onSuccess");

					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("logout: onClick(): onFailure");
						System.out.println(caught);
					}
				};
				loginServiceAsync.logout(asyncCallback);

				appLayout.clear();
				wbsl = new LONI_Pipeline_ST_Login_Display();
				wbsl.buildMainPage(user, sessionId);
			}
		});
		headLayout.addMember(logoutBtn);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);

		appLayout.addMember(headLayout);

		// Notification Layout
		VLayout notificationLayout = new VLayout();
		notificationLayout.setHeight("25px");
		notificationLayout.setWidth("100%");
		notificationLayout.setLayoutAlign(Alignment.CENTER);

		VLayout notificationContainer = new VLayout();
		notificationContainer.setHeight("20px");
		notificationContainer.setWidth("100%");
		notificationContainer.setLayoutAlign(Alignment.CENTER);

		notificationContainer.addMember(notifications.getNotificationMole());

		notificationLayout.addMember(notificationContainer);

		appLayout.addMember(notificationLayout);

		// Body with tabs
		HLayout tabLayout = new HLayout();
		tabLayout.setMembersMargin(5);
		tabLayout.setSize("100%", "94%");
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
		WorkFlowsTab workFlowsTab = new WorkFlowsTab(asyncClientServices,
				notifications);
		tabset.addTab(workFlowsTab.setTab());

		// Users Online tab
		UsersOnlineTab usersOnlineTab = new UsersOnlineTab(asyncClientServices,
				notifications);
		tabset.addTab(usersOnlineTab.setTab());

		// Users Usage tab
		UserUsageTab userUsageTab = new UserUsageTab(asyncClientServices,
				notifications);
		tabset.addTab(userUsageTab.setTab());

		// Memory Usage tab
		MemoryUsageTab memoryUsageTab = new MemoryUsageTab(asyncClientServices,
				notifications);
		tabset.addTab(memoryUsageTab.setTab());

		// Thread Usage tab
		ThreadUsageTab threadUsageTab = new ThreadUsageTab(asyncClientServices,
				notifications);
		tabset.addTab(threadUsageTab.setTab());

		// Preferences tab
		PreferencesTab preferencesTab = new PreferencesTab(padding,
				asyncClientServices, notifications);
		tabset.addTab(preferencesTab.setTab());

		// Refresh All Tabs
		LONIDataRequester dataRequester = new LONIDataRequester(
				asyncClientServices, memoryUsageTab.getChart(),
				threadUsageTab.getChart(), preferencesTab, workFlowsTab,
				usersOnlineTab, userUsageTab, notifications);

		// Uploads tab
		UploadTab uploadTab = new UploadTab(dataRequester, notifications);
		tabset.addTab(uploadTab.setTab());

		return tabset;
	}
}
