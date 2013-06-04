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

package edu.ucla.loni.pipeline.client.MainPage.WorkFlows;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import edu.ucla.loni.pipeline.client.MainPage.Services.AsyncClientServices;
import edu.ucla.loni.pipeline.client.Notifications.LONINotifications;

public class WorkFlowsTab {

	private ListGrid listWorkflows;
	private final AsyncClientServices asyncClientServices;
	VLayout layoutWorkflows = new VLayout();
	private int TotalWorkflows;
	private int TotalBacklogged;
	private int TotalSubmitting;
	private int TotalQueued;
	private int TotalRunning;
	private int TotalCompleted;
	private final LONINotifications notifications;

	public WorkFlowsTab(AsyncClientServices asyncClientServices,
			LONINotifications notifications) {
		TotalWorkflows = 0;
		TotalBacklogged = 0;
		TotalSubmitting = 0;
		TotalQueued = 0;
		TotalRunning = 0;
		TotalCompleted = 0;
		this.asyncClientServices = asyncClientServices;
		this.notifications = notifications;
		initializeListWorkflows();
	}

	public Tab setTab() {
		Tab tabWorkflows = new Tab("Workflows");

		layoutWorkflows.setSize("100%", "100%");
		layoutWorkflows.setDefaultLayoutAlign(Alignment.LEFT);
		layoutWorkflows.setMembersMargin(10);

		// Label that show statistic
		final com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"Loading... (If it takes too long, check the xml file format)");
		intro.setSize("1000px", "49px");
		layoutWorkflows.addMember(intro);

		// List
		listWorkflows.setShowRecordComponents(true);
		listWorkflows.setShowRecordComponentsByCell(true);
		listWorkflows.setShowAllRecords(true);
		listWorkflows.setSize("100%", "100%");
		listWorkflows.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listWorkflows.setCanPickFields(false);
		listWorkflows.setCanFreezeFields(false);
		listWorkflows.setAutoFitFieldWidths(true);

		// specific which field you want to expend
		listWorkflows.setAutoFitExpandField("workflowID");

		// Declare fields
		// Edit the behavior of the fields here
		final ListGridField workflowfield = new ListGridField("workflowID",
				"Workflow ID");
		workflowfield.setCellAlign(Alignment.CENTER);

		final ListGridField usernamefield = new ListGridField("username",
				"Username&#160;&#160;&#160;&#160;&#160;");
		usernamefield.setCellAlign(Alignment.CENTER);

		final ListGridField statefield = new ListGridField("state",
				"State&#160;&#160;&#160;&#160;&#160;");
		statefield.setCellAlign(Alignment.CENTER);

		final ListGridField starttimefield = new ListGridField("startTime",
				"Start Time&#160;&#160;&#160;&#160;&#160;");
		starttimefield.setCellAlign(Alignment.CENTER);

		final ListGridField endtimefield = new ListGridField("endTime",
				"End Time&#160;&#160;&#160;&#160;&#160;");
		endtimefield.setCellAlign(Alignment.CENTER);

		final ListGridField durationfield = new ListGridField("duration",
				"Duration&#160;&#160;&#160;&#160;&#160;");
		durationfield.setCellAlign(Alignment.CENTER);

		final ListGridField numofnodefield = new ListGridField("numofnode",
				"N&#160;&#160;&#160;");
		numofnodefield.setCellAlign(Alignment.RIGHT);

		final ListGridField numofinstancesfield = new ListGridField(
				"numofinstances", "I&#160;&#160;&#160;");
		numofinstancesfield.setCellAlign(Alignment.RIGHT);

		final ListGridField numBacklogfield = new ListGridField("numBacklog",
				"B&#160;&#160;&#160;");
		numBacklogfield.setCellAlign(Alignment.RIGHT);

		final ListGridField numSubmittingfield = new ListGridField(
				"numSubmitting", "S&#160;&#160;&#160;");
		numSubmittingfield.setCellAlign(Alignment.RIGHT);

		final ListGridField numQueuedfield = new ListGridField("numQueued",
				"Q&#160;&#160;&#160;");
		numQueuedfield.setCellAlign(Alignment.RIGHT);

		final ListGridField numRunningfield = new ListGridField("numRunning",
				"R&#160;&#160;&#160;");
		numRunningfield.setCellAlign(Alignment.RIGHT);

		final ListGridField numCompletedfield = new ListGridField(
				"numCompleted", "C&#160;&#160;&#160;");
		numCompletedfield.setCellAlign(Alignment.RIGHT);

		final ListGridField stopfield = new ListGridField("stop",
				"Stop/Reset&#160;&#160;&#160;&#160;&#160;");
		stopfield.setAlign(Alignment.CENTER);

		final ListGridField pausefield = new ListGridField("pause",
				"Pause/Rsm&#160;&#160;&#160;&#160;&#160;");
		pausefield.setAlign(Alignment.CENTER);

		final ListGridField viewfield = new ListGridField("view",
				"View&#160;&#160;&#160;&#160;&#160;");
		viewfield.setAlign(Alignment.CENTER);

		listWorkflows.setFields(workflowfield, usernamefield, statefield,
				starttimefield, endtimefield, durationfield, numofnodefield,
				numofinstancesfield, numBacklogfield, numSubmittingfield,
				numQueuedfield, numRunningfield, numCompletedfield, stopfield,
				pausefield, viewfield);

		// No default data
		layoutWorkflows.addMember(listWorkflows);

		tabWorkflows.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				Update(intro);
			}
		});

		Button workflowsrefreshbutton = new Button("Refresh");
		workflowsrefreshbutton.setAlign(Alignment.CENTER);
		layoutWorkflows.addMember(workflowsrefreshbutton);
		workflowsrefreshbutton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Update(intro);
			}
		});
		tabWorkflows.setPane(layoutWorkflows);
		return tabWorkflows;
	}

	public void Update(final com.smartgwt.client.widgets.Label intro) {
		asyncClientServices.reqResourceXMLService
				.getXMLData(new AsyncCallback<String>() {
					@Override
					public void onSuccess(final String xmlData) {
						refreshWorkflows(xmlData);
						System.out.println("Workflows refreshed successfully");

						// get current time with specific format
						Date time = new Date();
						DateTimeFormat ft = DateTimeFormat
								.getFormat("EEE MMM d HH:mm:ss ZZZZ yyyy");

						// Update the content of the top label
						intro.setContents("All Workflows ( " + TotalWorkflows
								+ "&#160;) "
								+ "&#160;&#160;&#160;Total Instances ("
								+ "Backlogged: " + TotalBacklogged
								+ "&#160;Submitting: " + TotalSubmitting
								+ "&#160;Queued: " + TotalQueued
								+ "&#160;Running: " + TotalRunning
								+ "&#160;Completed: " + TotalCompleted
								+ "&#160;) " + "&#160;&#160;&#160;Updated: "
								+ ft.format(time));

						notifications.showMessage(
								"Workflows Tab refreshed successfully.", true);
					}

					@Override
					public void onFailure(Throwable caught) {
						notifications.showMessage(
								"Workflows Tab did not refresh successfully.",
								true);
					}
				});
	}

	public void refreshWorkflows(String xml) {

		// remove whitespace
		String cleanXml = xml.replaceAll("\t", "");
		cleanXml.replaceAll("\n", "");

		try {
			Document doc = XMLParser.parse(cleanXml);
			parseWorkflowsXML(doc);
		} catch (DOMParseException e) {
			System.err
					.println("Could not parse XML file. Check XML file format.");
			return;
		}
	}

	public void parseWorkflowsXML(Document doc) {
		NodeList WorkflowsList = doc.getElementsByTagName("WorkflowEntry");

		// we get the total number of workflows here
		TotalWorkflows = WorkflowsList.getLength();
		// reset statistic
		TotalBacklogged = 0;
		TotalSubmitting = 0;
		TotalQueued = 0;
		TotalRunning = 0;
		TotalCompleted = 0;

		WorkFlowsRecord array[] = new WorkFlowsRecord[TotalWorkflows];
		for (int k = 0; k < TotalWorkflows; k++) {
			Node WorkflowsNode = WorkflowsList.item(k);
			if (WorkflowsNode.getNodeType() == Node.ELEMENT_NODE) {
				Element WorkflowsElement = (Element) WorkflowsNode;

				// Workflows ID
				NodeList WorkflowsIDList = WorkflowsElement
						.getElementsByTagName("WorkflowID");
				Element WorkflowsIDElement = (Element) WorkflowsIDList.item(0);
				NodeList textWorkflowsIDList = WorkflowsIDElement
						.getChildNodes();

				// Username
				NodeList UsernameList = WorkflowsElement
						.getElementsByTagName("Username");
				Element UsernameElement = (Element) UsernameList.item(0);
				NodeList textUsernameList = UsernameElement.getChildNodes();

				// State
				NodeList StateList = WorkflowsElement
						.getElementsByTagName("State");
				Element StateElement = (Element) StateList.item(0);
				NodeList textStateList = StateElement.getChildNodes();

				// Start Time
				NodeList StartTimeList = WorkflowsElement
						.getElementsByTagName("StartTime");
				Element StartTimeElement = (Element) StartTimeList.item(0);
				NodeList textStartTimeList = StartTimeElement.getChildNodes();

				// End Time
				NodeList EndTimeList = WorkflowsElement
						.getElementsByTagName("EndTime");
				Element EndTimeElement = (Element) EndTimeList.item(0);
				NodeList textEndTimeList = EndTimeElement.getChildNodes();

				// Duration
				NodeList DurationList = WorkflowsElement
						.getElementsByTagName("Duration");
				Element DurationElement = (Element) DurationList.item(0);
				NodeList textDurationList = DurationElement.getChildNodes();

				// NumOfNode
				NodeList NumOfNodeList = WorkflowsElement
						.getElementsByTagName("NumOfNode");
				Element NumOfNodeElement = (Element) NumOfNodeList.item(0);
				NodeList textNumOfNodeList = NumOfNodeElement.getChildNodes();

				// NumOfInstances
				NodeList NumOfInstancesList = WorkflowsElement
						.getElementsByTagName("NumOfInstances");
				Element NumOfInstancesElement = (Element) NumOfInstancesList
						.item(0);
				NodeList textNumOfInstancesList = NumOfInstancesElement
						.getChildNodes();

				// NumBacklog
				NodeList NumBacklogList = WorkflowsElement
						.getElementsByTagName("NumBacklog");
				Element NumBacklogElement = (Element) NumBacklogList.item(0);
				NodeList textNumBacklogList = NumBacklogElement.getChildNodes();
				TotalBacklogged += Integer.parseInt(textNumBacklogList.item(0)
						.getNodeValue().trim());

				// NumSubmitting
				NodeList NumSubmittingList = WorkflowsElement
						.getElementsByTagName("NumSubmitting");
				Element NumSubmittingElement = (Element) NumSubmittingList
						.item(0);
				NodeList textNumSubmittingList = NumSubmittingElement
						.getChildNodes();
				TotalSubmitting += Integer.parseInt(textNumSubmittingList
						.item(0).getNodeValue().trim());

				// NumQueued
				NodeList NumQueuedList = WorkflowsElement
						.getElementsByTagName("NumQueued");
				Element NumQueuedElement = (Element) NumQueuedList.item(0);
				NodeList textNumQueuedList = NumQueuedElement.getChildNodes();
				TotalQueued += Integer.parseInt(textNumQueuedList.item(0)
						.getNodeValue().trim());

				// NumRunning
				NodeList NumRunningList = WorkflowsElement
						.getElementsByTagName("NumRunning");
				Element NumRunningElement = (Element) NumRunningList.item(0);
				NodeList textNumRunningList = NumRunningElement.getChildNodes();
				TotalRunning += Integer.parseInt(textNumRunningList.item(0)
						.getNodeValue().trim());

				// NumCompleted
				NodeList NumCompletedList = WorkflowsElement
						.getElementsByTagName("NumCompleted");
				Element NumCompletedElement = (Element) NumCompletedList
						.item(0);
				NodeList textNumCompletedList = NumCompletedElement
						.getChildNodes();
				TotalCompleted += Integer.parseInt(textNumCompletedList.item(0)
						.getNodeValue().trim());

				array[k] = new WorkFlowsRecord(textWorkflowsIDList.item(0)
						.getNodeValue().trim(), textUsernameList.item(0)
						.getNodeValue().trim(), textStateList.item(0)
						.getNodeValue().trim(), textStartTimeList.item(0)
						.getNodeValue().trim(), textEndTimeList.item(0)
						.getNodeValue().trim(), textDurationList.item(0)
						.getNodeValue().trim(), textNumOfNodeList.item(0)
						.getNodeValue().trim(), textNumOfInstancesList.item(0)
						.getNodeValue().trim(), textNumBacklogList.item(0)
						.getNodeValue().trim(), textNumSubmittingList.item(0)
						.getNodeValue().trim(), textNumQueuedList.item(0)
						.getNodeValue().trim(), textNumRunningList.item(0)
						.getNodeValue().trim(), textNumCompletedList.item(0)
						.getNodeValue().trim());
			}// end if
		}// end loop
		listWorkflows.setData(array);
	}

	private IButton createStopResetButton() {
		IButton button = new IButton();
		button.setHeight(16);
		button.setWidth(65);
		// change title after created for reset button
		button.setTitle("Stop");
		return button;
	}

	private IButton createPauseResumeButton() {
		IButton button = new IButton();
		button.setHeight(16);
		button.setWidth(65);
		// change title after created for resume button
		button.setTitle("Pause");
		return button;
	}

	private IButton createViewButton() {
		IButton button = new IButton();
		button.setHeight(16);
		button.setWidth(40);
		button.setTitle("View");
		return button;
	}

	private void initializeListWorkflows() {
		// this function displays the buttons in the ListGrids
		listWorkflows = new ListGrid() {
			@Override
			protected Canvas createRecordComponent(final ListGridRecord record,
					Integer colNum) {
				String fieldName = this.getFieldName(colNum);
				String state = record.getAttributeAsString("state");

				// State: Running
				// show Stop,Pause and View buttons
				if (state.equals("Running")) {
					if (fieldName.equals("stop")) {
						IButton button = createStopResetButton();
						return button;

					} else if (fieldName.equals("pause")) {
						IButton button = createPauseResumeButton();
						return button;

					} else if (fieldName.equals("view")) {
						IButton button = createViewButton();
						return button;

					} else {
						return null;

					}
				}
				// State: Stop
				// Show Reset,Pause and View button, disable Pause button.
				else if (state.equals("Stop")) {
					if (fieldName.equals("stop")) {
						IButton button = createStopResetButton();
						button.setTitle("Reset");
						return button;

					} else if (fieldName.equals("pause")) {
						IButton button = createPauseResumeButton();
						button.setDisabled(true);
						return button;

					} else if (fieldName.equals("view")) {
						IButton button = createViewButton();
						return button;

					} else {
						return null;

					}
				}
				// State: Backlog
				// Show Stop, Pause and View buttons, disable pause button.
				else if (state.equals("Backlog")) {
					if (fieldName.equals("stop")) {
						IButton button = createStopResetButton();
						return button;

					} else if (fieldName.equals("pause")) {
						IButton button = createPauseResumeButton();
						button.setDisabled(true);
						return button;

					} else if (fieldName.equals("view")) {
						IButton button = createViewButton();
						return button;

					} else {
						return null;

					}
				}
				// State: Pause
				// Show Reset, Resume and View buttons
				else if (state.equals("Pause")) {
					if (fieldName.equals("stop")) {
						IButton button = createStopResetButton();
						button.setTitle("Reset");
						return button;

					} else if (fieldName.equals("pause")) {
						IButton button = createPauseResumeButton();
						button.setTitle("Resume");
						return button;

					} else if (fieldName.equals("view")) {
						IButton button = createViewButton();
						return button;

					} else {
						// Not stop, pause or view fields.
						return null;

					}
				} else {
					return null;
				}

			}
		}; // end of function
	}
}
