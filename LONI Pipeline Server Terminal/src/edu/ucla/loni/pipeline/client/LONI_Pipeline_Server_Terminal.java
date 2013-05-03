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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.types.Alignment;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LONI_Pipeline_Server_Terminal implements EntryPoint {
	private ListGrid listWorkflows, listUsersOnline, listUsersUsage, listUsersUsageCount;
	
	/**
	 * This is the entry point method. 
	 * This is generated and managed by the visual designer.
	 */
	public void onModuleLoad() {
		TabSet tabset = new TabSet();
		tabset.setSize("100%", "100%");
		
		Tab tabWorkflows = new Tab("Workflows");
		
		VLayout layoutWorkflows = new VLayout();
		layoutWorkflows.setSize("100%", "100%");
		layoutWorkflows.setMembersMargin(5);
		
		listWorkflows = new ListGrid();
		listWorkflows.setSize("100%", "100%");
		listWorkflows.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listWorkflows.setFields(
				new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("username", "Username"),
				new ListGridField("state", "State"),
				new ListGridField("startTime", "Start Time"),
				new ListGridField("endTime", "End Time"),
				new ListGridField("duration", "Duration"),
				// TODO (Jeff): find out what some of fields mean
				new ListGridField("jobsN", "N"),
				new ListGridField("jobsI", "I"),
				new ListGridField("jobsB", "B"),
				new ListGridField("jobsS", "S"),
				new ListGridField("jobsQueued", "Q"),
				new ListGridField("jobsRunning", "R"),
				new ListGridField("jobsCompleted", "C"),
				new ListGridField("stop", "Stop/Reset"),
				new ListGridField("pause", "Pause/Rsm"),
				new ListGridField("view", "View")
		);
		
		layoutWorkflows.addMember(listWorkflows);
		
		Button exportWorkflows = new Button("Export to CSV...");
		layoutWorkflows.addMember(exportWorkflows);
		
		tabWorkflows.setPane(layoutWorkflows);
		tabset.addTab(tabWorkflows);
		
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
				new ListGridField("disconnect", "Disconnect")
		);
		layoutUsersOnline.addMember(listUsersOnline);
		
		Button exportUsersOnline = new Button("Export to CSV...");
		layoutUsersOnline.addMember(exportUsersOnline);
		tabUsersOnline.setPane(layoutUsersOnline);
		tabset.addTab(tabUsersOnline);
		
		Tab tabUsersUsage = new Tab("User Usage");
		
		VLayout layoutUsersUsage = new VLayout();
		layoutUsersUsage.setSize("100%", "100%");
		layoutUsersUsage.setDefaultLayoutAlign(Alignment.CENTER);	// Horizontal centering
		layoutUsersUsage.setMembersMargin(5);
		
		listUsersUsage = new ListGrid();
		listUsersUsage.setSize("100%", "50%");
		listUsersUsage.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersUsage.setFields(
				new ListGridField("username", "Username"),
				new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("nodeName", "NodeName"),
				new ListGridField("instance", "Instance")
		);
		layoutUsersUsage.addMember(listUsersUsage);
		
		listUsersUsageCount = new ListGrid();
		listUsersUsageCount.setSize("50%", "50%");
		listUsersUsageCount.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersUsageCount.setFields(
				new ListGridField("username", "Username"),
				new ListGridField("count", "Count")
		);
		layoutUsersUsage.addMember(listUsersUsageCount);
		
		tabUsersUsage.setPane(layoutUsersUsage);
		tabset.addTab(tabUsersUsage);
		
		Tab tabMemoryUsage = new Tab("Memory Usage");
		
		final LONI_Chart memChart = new LONI_Chart("Memory");
		tabMemoryUsage.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				memChart.redraw();
			}});
		
		tabMemoryUsage.setPane(memChart);
		
		tabset.addTab(tabMemoryUsage);
		
		Tab tabThreadUsage = new Tab("Thread Usage");
		
		final LONI_Chart thrdChart = new LONI_Chart("Thread");
		tabThreadUsage.addTabSelectedHandler(new TabSelectedHandler(){

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				thrdChart.redraw();
				
			}});
		
		tabThreadUsage.setPane(thrdChart);
		
		tabset.addTab(tabThreadUsage);
		
		Tab tabPreferences = new Tab("Preferences");
		tabset.addTab(tabPreferences);
		
		Tab tabUpload = new Tab("Upload");
		createUploadTab(tabUpload);
		tabset.addTab(tabUpload);
		
		tabset.draw();
	}
	
	public void createUploadTab(Tab tabUpload) {
		final VerticalPanel progressBarPanel = new VerticalPanel();  
		final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();  
		final Uploader uploader = new Uploader();  
		uploader.setUploadURL("/DevNullUploadServlet")  
		.setButtonImageURL(GWT.getModuleBaseURL() + "resources/images/buttons/upload_new_version_button.png")  
		.setButtonWidth(133)  
		.setButtonHeight(22)  
		.setFileSizeLimit("50 MB")  
		.setButtonCursor(Uploader.Cursor.HAND)  
		.setButtonAction(Uploader.ButtonAction.SELECT_FILES)  
		.setFileQueuedHandler(new FileQueuedHandler() {  
			public boolean onFileQueued(final FileQueuedEvent fileQueuedEvent) {  
				// Add Cancel Button Image  
				final Image cancelButton = new Image(GWT.getModuleBaseURL() + "resources/images/icons/cancel.png");  
				cancelButton.setStyleName("cancelButton");  
				cancelButton.addClickHandler(new ClickHandler() {  
					public void onClick(ClickEvent event) {  
						uploader.cancelUpload(fileQueuedEvent.getFile().getId(), false);  
						cancelButton.removeFromParent();  
					}  
				});  
				cancelButtons.put(fileQueuedEvent.getFile().getId(), cancelButton);  

				// Add the Bar and Button to the interface  
				HorizontalPanel progressBarAndButtonPanel = new HorizontalPanel();  
				progressBarAndButtonPanel.add(cancelButton);  
				progressBarPanel.add(progressBarAndButtonPanel);  

				return true;  
			}  
		})    
		.setUploadCompleteHandler(new UploadCompleteHandler() {  
			public boolean onUploadComplete(UploadCompleteEvent uploadCompleteEvent) {  
				cancelButtons.get(uploadCompleteEvent.getFile().getId()).removeFromParent();  
				uploader.startUpload();  
				return true;  
			}  
		})  
		.setFileDialogStartHandler(new FileDialogStartHandler() {  
			public boolean onFileDialogStartEvent(FileDialogStartEvent fileDialogStartEvent) {  
				if (uploader.getStats().getUploadsInProgress() <= 0) {  
					// Clear the uploads that have completed, if none are in process  
					progressBarPanel.clear();  
					cancelButtons.clear();  
				}  
				return true;  
			}  
		})  
		.setFileDialogCompleteHandler(new FileDialogCompleteHandler() {  
			public boolean onFileDialogComplete(FileDialogCompleteEvent fileDialogCompleteEvent) {  
				if (fileDialogCompleteEvent.getTotalFilesInQueue() > 0) {  
					if (uploader.getStats().getUploadsInProgress() <= 0) {  
						uploader.startUpload();  
					}  
				}  
				return true;  
			}  
		})  
		.setFileQueueErrorHandler(new FileQueueErrorHandler() {  
			public boolean onFileQueueError(FileQueueErrorEvent fileQueueErrorEvent) {  
				Window.alert("Upload of file " + fileQueueErrorEvent.getFile().getName() + " failed due to [" +  
						fileQueueErrorEvent.getErrorCode().toString() + "]: " + fileQueueErrorEvent.getMessage()  
						);  
				return true;  
			}  
		})  
		.setUploadErrorHandler(new UploadErrorHandler() {  
			public boolean onUploadError(UploadErrorEvent uploadErrorEvent) {  
				cancelButtons.get(uploadErrorEvent.getFile().getId()).removeFromParent();  
				Window.alert("Upload of file " + uploadErrorEvent.getFile().getName() + " failed due to [" +  
						uploadErrorEvent.getErrorCode().toString() + "]: " + uploadErrorEvent.getMessage()  
						);  
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

					uploader.addFilesToQueue(Uploader.getDroppedFiles(event.getNativeEvent()));  
					event.preventDefault();  
				}  
			});  
			verticalPanel.add(dropFilesLabel);  
		}  

		HorizontalPanel horizontalPanel = new HorizontalPanel();  
		horizontalPanel.add(verticalPanel);  
		horizontalPanel.add(progressBarPanel);  
		horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);  
		horizontalPanel.setCellHorizontalAlignment(uploader, HorizontalPanel.ALIGN_LEFT);  
		horizontalPanel.setCellHorizontalAlignment(progressBarPanel, HorizontalPanel.ALIGN_RIGHT);
		
		VLayout uploadLayout = new VLayout();
		uploadLayout.addMember(horizontalPanel);
		tabUpload.setPane(uploadLayout);
	}
}
