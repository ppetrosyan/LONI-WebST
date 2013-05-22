package edu.ucla.loni.pipeline.client.MainPage;

import java.util.LinkedHashMap;
import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;

import edu.ucla.loni.pipeline.client.Charts.LONI_Chart;
import edu.ucla.loni.pipeline.client.MainPage.MemoryUsage.MemoryUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.PreferencesTab;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Executables.ExecutablesData;
import edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages.PackagesData;
import edu.ucla.loni.pipeline.client.MainPage.ThreadUsage.ThreadUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.Upload.UploadTab;
import edu.ucla.loni.pipeline.client.MainPage.UserUsage.UserUsageCountXmlDS;
import edu.ucla.loni.pipeline.client.MainPage.UserUsage.UserUsageTab;
import edu.ucla.loni.pipeline.client.MainPage.UserUsage.UserUsageXmlDS;
import edu.ucla.loni.pipeline.client.MainPage.UsersOnline.UsersOnlineTab;
import edu.ucla.loni.pipeline.client.MainPage.UsersOnline.UsersOnlineXmlDS;
import edu.ucla.loni.pipeline.client.MainPage.WorkFlows.WorkFlowsTab;
import edu.ucla.loni.pipeline.client.MainPage.WorkFlows.WorkFlowsXmlDS;
import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLService;
import edu.ucla.loni.pipeline.client.Requesters.Configuration.RequestConfigurationXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLService;
import edu.ucla.loni.pipeline.client.Requesters.ResourceUsage.RequestResourceXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLService;
import edu.ucla.loni.pipeline.client.Savers.Configuration.SaveConfigurationXMLServiceAsync;
import edu.ucla.loni.pipeline.client.Upload.Features.LONIDragandDropLabel;
import edu.ucla.loni.pipeline.client.Upload.Uploaders.LONIFileUploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LONI_Pipeline_ST_Tabset_Display {

	VLayout mainLayout = new VLayout();
	VLayout padding = new VLayout();

	// String userID;
	private ListGrid listUsersOnline, listUserUsage, listUserUsageCount;

	// Client-side Services
	String reqResourceXML = "RequestResourceXMLServlet";
	String reqConfigurationXML = "RequestConfigurationXMLServlet";
	String saveConfigurationXML = "SaveConfigurationXMLServlet";

	final RequestResourceXMLServiceAsync reqResourceXMLService = GWT
			.create(RequestResourceXMLService.class);
	final RequestConfigurationXMLServiceAsync reqConfigurationXMLService = GWT
			.create(RequestConfigurationXMLService.class);
	final SaveConfigurationXMLServiceAsync saveConfigurationXMLService = GWT
			.create(SaveConfigurationXMLService.class);

	final LONI_Chart memChart = new LONI_Chart("Memory");
	final LONI_Chart thrdChart = new LONI_Chart("Thread");

	public LONI_Pipeline_ST_Tabset_Display() {
		mainLayout.setMembersMargin(20);
		mainLayout.setSize("100%", "100%");

		((ServiceDefTarget) reqResourceXMLService)
				.setServiceEntryPoint(reqResourceXML);
		((ServiceDefTarget) reqConfigurationXMLService)
				.setServiceEntryPoint(reqConfigurationXML);
		((ServiceDefTarget) saveConfigurationXMLService)
				.setServiceEntryPoint(saveConfigurationXML);
		
		listUsersOnline = new ListGrid();
		listUserUsage = new ListGrid();
		listUserUsageCount = new ListGrid();
	}

	

	public void buildMainPage(String userID, boolean status) {
		// if (!status)
		// return;
	
		// Header with Heading and logout button
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(10);
		headLayout.setSize("100%", "3%");

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("1%", "100%");
		headLayout.addMember(padding);

		com.smartgwt.client.widgets.Label LONILabel = new com.smartgwt.client.widgets.Label(
				"<b><font size='6'>LONI Pipeline</font></b>");
		LONILabel.setSize("48%", "100%");
		LONILabel.setAlign(Alignment.LEFT);
		LONILabel.setValign(VerticalAlignment.CENTER);
		headLayout.addMember(LONILabel);

		// Refresh Button for all Tabs
		final LONIDataRequester dataRequester = new LONIDataRequester(
				reqResourceXMLService, reqConfigurationXMLService, memChart,
				thrdChart);

		Button appRefreshButton = new Button("Refresh All Tabs");
		appRefreshButton.setAlign(Alignment.CENTER);
		appRefreshButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dataRequester.refreshTabs();
			}
		});
		
		headLayout.addMember(appRefreshButton);

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
		TabSet tabset = buildtabset(dataRequester);
		tabset.draw();
		tabLayout.addMember(tabset);
		tabLayout.draw();
		mainLayout.addMember(tabset);

		mainLayout.draw();
	}

	private TabSet buildtabset(LONIDataRequester dataRequester) {
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
		tabset.addTab(WorkFlowsTab.setTab());

		// Users Online tab
		tabset.addTab(UsersOnlineTab.setTab(listUsersOnline));

		// Users Usage tab
		tabset.addTab(UserUsageTab.setTab(listUserUsage, listUserUsageCount));

		// Memory Usage tab
		tabset.addTab(MemoryUsageTab.setTab(memChart));

		// Thread Usage tab
		tabset.addTab(ThreadUsageTab.setTab(thrdChart));

		// Preferences tab
		tabset.addTab(PreferencesTab.setTab(padding));

		// Uploads tab
		tabset.addTab(UploadTab.setTab(dataRequester));

		return tabset;
	}
}
