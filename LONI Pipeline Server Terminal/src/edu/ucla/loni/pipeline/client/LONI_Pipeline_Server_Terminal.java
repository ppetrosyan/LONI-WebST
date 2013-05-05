package edu.ucla.loni.pipeline.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteHandler;
import org.moxieapps.gwt.uploader.client.events.FileDialogStartEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogStartHandler;
import org.moxieapps.gwt.uploader.client.events.FileQueueErrorEvent;
import org.moxieapps.gwt.uploader.client.events.FileQueueErrorHandler;
import org.moxieapps.gwt.uploader.client.events.FileQueuedEvent;
import org.moxieapps.gwt.uploader.client.events.FileQueuedHandler;
import org.moxieapps.gwt.uploader.client.events.UploadCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.UploadCompleteHandler;
import org.moxieapps.gwt.uploader.client.events.UploadErrorEvent;
import org.moxieapps.gwt.uploader.client.events.UploadErrorHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.NativeCheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.types.Alignment; 


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {
	private ListGrid listWorkflows, listUsersOnline, listUsersUsage,
			listUsersUsageCount;

	/**
	 * This is the entry point method. This is generated and managed by the
	 * visual designer.
	 */
	public void onModuleLoad() {
		
		//The following function causes an error on gwt designer.
		//Please uncomment it when not using gwt designer.
		//And also comment out the following line(around line 125):
		//        listWorkflows = new ListGrid();
		
		/*
		//Start of function
		//this function displays the buttons in the ListGrids
		final ListGrid listWorkflows = new ListGrid() {  
            @Override  
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {  
  
                String fieldName = this.getFieldName(colNum);  
  
                if (fieldName.equals("stop")) {  
                    IButton button = new IButton();  
                    button.setHeight(18);  
                    button.setWidth(75);                        
                    button.setTitle("Stop/Reset");  
                    //insert a click handler here
                    //we are using gwt click handler here
                    return button;  
                } else if(fieldName.equals("pause")){  
                	IButton button = new IButton();  
                    button.setHeight(18);  
                    button.setWidth(65);                        
                    button.setTitle("Pause");  
                    //insert a click handler here
                    //we are using gwt click handler here
                    return button; 
                } else if(fieldName.equals("view")){  
                	IButton button = new IButton();  
                    button.setHeight(18);  
                    button.setWidth(65);                        
                    button.setTitle("View");  
                    //insert a click handler here
                    //we are using gwt click handler here
                    return button; 
                } else{
                	return null;
                }  
            }  
        };  //end of function
		*/
		
		TabSet tabset = new TabSet();
		tabset.setSize("100%", "100%");
		tabset.setPaneMargin(30);

		Tab tabWorkflows = new Tab("Workflows");

		VLayout layoutWorkflows = new VLayout();
		layoutWorkflows.setSize("100%", "100%");
		layoutWorkflows.setDefaultLayoutAlign(Alignment.CENTER);
		layoutWorkflows.setMembersMargin(20);

		
		//work flows tab
		listWorkflows = new ListGrid();
		listWorkflows.setShowRecordComponents(true); 
		listWorkflows.setShowRecordComponentsByCell(true);
		listWorkflows.setShowAllRecords(true); 
		listWorkflows.setSize("100%", "100%");
		listWorkflows.setCellPadding(2);
		listWorkflows.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		
		//Need to declare these fields here so we can edit their behavior
		ListGridField stopfield = new ListGridField("stop", "Stop/Reset");
		stopfield.setAlign(Alignment.CENTER); 
		
		ListGridField pausefield = new ListGridField("pause","Pause/Rsm");
		pausefield.setAlign(Alignment.CENTER); 
		
		ListGridField viewfield = new ListGridField("view", "View");
		viewfield.setAlign(Alignment.CENTER); 
		
		listWorkflows.setFields(new ListGridField("workflowID", "Workflow ID"),
								new ListGridField("username", "Username"), 
								new ListGridField("state", "State"), 
								new ListGridField("startTime", "Start Time"),
								new ListGridField("endTime", "End Time"),
								new ListGridField("duration", "Duration"),
								new ListGridField("numofnode","N"), 
								new ListGridField("numofinstances", "I"),
								new ListGridField("numBacklab", "B"),
								new ListGridField("numSubmitting", "S"), 
								new ListGridField("numQueued",	"Q"), 
								new ListGridField("numRunning", "R"),
								new ListGridField("numCompleted", "C"), 
								stopfield,
								pausefield, 
								viewfield);
		
		
		
		// input data into the list.
		listWorkflows.setData(WorkFlowsData.getRecords());
		
		layoutWorkflows.addMember(listWorkflows);		
		listWorkflows.moveTo(30, 0);

		tabWorkflows.setPane(layoutWorkflows);
		tabset.addTab(tabWorkflows);
		//end work flows tab

		//Users online tab
		Tab tabUsersOnline = new Tab("Users Online");

		VLayout layoutUsersOnline = new VLayout();
		layoutUsersOnline.setSize("100%", "100%");
		layoutUsersOnline.setMembersMargin(5);

		listUsersOnline = new ListGrid();
		listUsersOnline.setSize("100%", "100%");
		listUsersOnline.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersOnline.setFields(
				new ListGridField("username", "Username"),
				new ListGridField("ipAddress", "IP Address"),
				new ListGridField("pipelineInterface", "Pipeline Interface"),
				new ListGridField("pipelineVersion", "Pipeline Version"),
				new ListGridField("osVersion", "OS Version"),
				new ListGridField("connectTime", "Connect Time"),
				new ListGridField("lastActivity", "Last Activity"),
				new ListGridField("disconnect", "Disconnect"));
		
		//get data from OnlineData.java
		listUsersOnline.setData(OnlineData.getRecords());  
		
		layoutUsersOnline.addMember(listUsersOnline);

		Button exportUsersOnline = new Button("Export to CSV...");
		layoutUsersOnline.addMember(exportUsersOnline);
		tabUsersOnline.setPane(layoutUsersOnline);
		tabset.addTab(tabUsersOnline);
		//end Users online tab
		
		Tab tabUsersUsage = new Tab("User Usage");

		VLayout layoutUsersUsage = new VLayout();
		layoutUsersUsage.setSize("100%", "100%");
		layoutUsersUsage.setDefaultLayoutAlign(Alignment.CENTER); // Horizontal
																	// centering
		layoutUsersUsage.setMembersMargin(5);

		listUsersUsage = new ListGrid();
		listUsersUsage.setSize("100%", "50%");
		listUsersUsage.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersUsage.setFields(
				new ListGridField("username", "Username"),
				new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("nodeName", "NodeName"), 
				new ListGridField("instance", "Instance"));
		layoutUsersUsage.addMember(listUsersUsage);

		listUsersUsageCount = new ListGrid();
		listUsersUsageCount.setSize("50%", "50%");
		listUsersUsageCount.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersUsageCount.setFields(
				new ListGridField("username", "Username"), new ListGridField(
						"count", "Count"));
		layoutUsersUsage.addMember(listUsersUsageCount);

		tabUsersUsage.setPane(layoutUsersUsage);
		tabset.addTab(tabUsersUsage);

		Tab tabMemoryUsage = new Tab("Memory Usage");

		final LONI_Chart memChart = new LONI_Chart("Memory");
		tabMemoryUsage.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				memChart.getChart().redraw();
			}
		});

		tabMemoryUsage.setPane(memChart);

		tabset.addTab(tabMemoryUsage);

		Tab tabThreadUsage = new Tab("Thread Usage");

		final LONI_Chart thrdChart = new LONI_Chart("Thread");
		tabThreadUsage.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				thrdChart.getChart().redraw();
			}
		});

		tabThreadUsage.setPane(thrdChart);

		tabset.addTab(tabThreadUsage);

		//preferences tab
		Tab tabPreferences = new Tab("Preferences");
		
		TabSet tabSet = new TabSet();
		
		Tab tabGeneral = new Tab("General");
		
		VLayout layoutGeneral = new VLayout();
		
		com.smartgwt.client.widgets.Label lblNewLabel = new com.smartgwt.client.widgets.Label("Basic");
		lblNewLabel.setSize("69px", "17px");
		layoutGeneral.addMember(lblNewLabel);
		
		DynamicForm GeneralForm = new DynamicForm();
		NativeCheckboxItem nativeCheckboxItem = new NativeCheckboxItem();
		nativeCheckboxItem.setTitle("Use privilege escalation: Pipeline server will run commands as the user(sudo as user)");
		NativeCheckboxItem nativeCheckboxItem_1 = new NativeCheckboxItem();
		nativeCheckboxItem_1.setTitle("Enable guests");
		NativeCheckboxItem nativeCheckboxItem_2 = new NativeCheckboxItem();
		nativeCheckboxItem_2.setTitle("Secure");
		GeneralForm.setFields(new FormItem[] { new TextItem("newTextItem_1", "Server_hostname"), new TextItem("newTextItem_4", "Port"), new UploadItem("newUploadItem_5", "Temportary directory"), nativeCheckboxItem_2, new UploadItem("newUploadItem_7", "Scrath directroy"), new UploadItem("newUploadItem_4", "Log file location"), nativeCheckboxItem, nativeCheckboxItem_1});
		layoutGeneral.addMember(GeneralForm);
		GeneralForm.moveTo(100, 17);
		tabGeneral.setPane(layoutGeneral);
		tabSet.addTab(tabGeneral);
		
		Tab tabGrid = new Tab("Grid");
		tabSet.addTab(tabGrid);
		
		Tab tabAccess = new Tab("Access");
		tabSet.addTab(tabAccess);
		
		Tab tabPackages = new Tab("Packages");
		tabSet.addTab(tabPackages);
		
		Tab tabExecutables = new Tab("Executables");
		tabSet.addTab(tabExecutables);
		
		Tab tabAdvanced = new Tab("Advanced");
		tabSet.addTab(tabAdvanced);
		tabPreferences.setPane(tabSet);
		tabset.addTab(tabPreferences);
		//end preferences tab

		Tab tabUpload = new Tab("Upload");
		createUploadTab(tabUpload);
		tabset.addTab(tabUpload);

		tabset.draw();
	}

	public void createUploadTab(Tab tabUpload) {
		final VerticalPanel progressBarPanel = new VerticalPanel();
		final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();
		final Uploader uploader = new Uploader();
		uploader.setUploadURL("/FileUploadServlet")
				.setButtonImageURL(
						GWT.getModuleBaseURL()
								+ "resources/images/buttons/upload_new_version_button.png")
				.setButtonWidth(133)
				.setButtonHeight(22)
				.setFileSizeLimit("50 MB")
				.setButtonCursor(Uploader.Cursor.HAND)
				.setButtonAction(Uploader.ButtonAction.SELECT_FILES)
				.setFileQueuedHandler(new FileQueuedHandler() {
					public boolean onFileQueued(
							final FileQueuedEvent fileQueuedEvent) {
						// Add Cancel Button Image
						final Image cancelButton = new Image(GWT
								.getModuleBaseURL()
								+ "resources/images/icons/cancel.png");
						cancelButton.setStyleName("cancelButton");
						cancelButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								uploader.cancelUpload(fileQueuedEvent.getFile()
										.getId(), false);
								cancelButton.removeFromParent();
							}
						});
						cancelButtons.put(fileQueuedEvent.getFile().getId(),
								cancelButton);

						// Add the Bar and Button to the interface
						HorizontalPanel progressBarAndButtonPanel = new HorizontalPanel();
						progressBarAndButtonPanel.add(cancelButton);
						progressBarPanel.add(progressBarAndButtonPanel);

						return true;
					}
				})
				.setUploadCompleteHandler(new UploadCompleteHandler() {
					public boolean onUploadComplete(
							UploadCompleteEvent uploadCompleteEvent) {
						cancelButtons
								.get(uploadCompleteEvent.getFile().getId())
								.removeFromParent();
						uploader.startUpload();
						return true;
					}
				})
				.setFileDialogStartHandler(new FileDialogStartHandler() {
					public boolean onFileDialogStartEvent(
							FileDialogStartEvent fileDialogStartEvent) {
						if (uploader.getStats().getUploadsInProgress() <= 0) {
							// Clear the uploads that have completed, if none
							// are in process
							progressBarPanel.clear();
							cancelButtons.clear();
						}
						return true;
					}
				})
				.setFileDialogCompleteHandler(new FileDialogCompleteHandler() {
					public boolean onFileDialogComplete(
							FileDialogCompleteEvent fileDialogCompleteEvent) {
						if (fileDialogCompleteEvent.getTotalFilesInQueue() > 0) {
							if (uploader.getStats().getUploadsInProgress() <= 0) {
								uploader.startUpload();
							}
						}
						return true;
					}
				}).setFileQueueErrorHandler(new FileQueueErrorHandler() {
					public boolean onFileQueueError(
							FileQueueErrorEvent fileQueueErrorEvent) {
						Window.alert("Upload of file "
								+ fileQueueErrorEvent.getFile().getName()
								+ " failed due to ["
								+ fileQueueErrorEvent.getErrorCode().toString()
								+ "]: " + fileQueueErrorEvent.getMessage());
						return true;
					}
				}).setUploadErrorHandler(new UploadErrorHandler() {
					public boolean onUploadError(
							UploadErrorEvent uploadErrorEvent) {
						cancelButtons.get(uploadErrorEvent.getFile().getId())
								.removeFromParent();
						Window.alert("Upload of file "
								+ uploadErrorEvent.getFile().getName()
								+ " failed due to ["
								+ uploadErrorEvent.getErrorCode().toString()
								+ "]: " + uploadErrorEvent.getMessage());
						return true;
					}
				});

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(uploader);

		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final Label dropFilesLabel = new Label("Drop Files Here");
			dropFilesLabel.setStyleName("dropFilesLabel");
			dropFilesLabel.addDragOverHandler(new DragOverHandler() {
				public void onDragOver(DragOverEvent event) {
					if (!uploader.getButtonDisabled()) {
						dropFilesLabel.addStyleName("dropFilesLabelHover");
					}
				}
			});
			dropFilesLabel.addDragLeaveHandler(new DragLeaveHandler() {
				public void onDragLeave(DragLeaveEvent event) {
					dropFilesLabel.removeStyleName("dropFilesLabelHover");
				}
			});
			dropFilesLabel.addDropHandler(new DropHandler() {
				public void onDrop(DropEvent event) {
					dropFilesLabel.removeStyleName("dropFilesLabelHover");

					if (uploader.getStats().getUploadsInProgress() <= 0) {
						progressBarPanel.clear();
						cancelButtons.clear();
					}

					uploader.addFilesToQueue(Uploader.getDroppedFiles(event
							.getNativeEvent()));
					event.preventDefault();
				}
			});
			verticalPanel.add(dropFilesLabel);
		}

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(verticalPanel);
		horizontalPanel.add(progressBarPanel);
		horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		horizontalPanel.setCellHorizontalAlignment(uploader,
				HorizontalPanel.ALIGN_LEFT);
		horizontalPanel.setCellHorizontalAlignment(progressBarPanel,
				HorizontalPanel.ALIGN_RIGHT);

		VLayout uploadLayout = new VLayout();
		uploadLayout.addMember(horizontalPanel);
		tabUpload.setPane(uploadLayout);
	}
}
