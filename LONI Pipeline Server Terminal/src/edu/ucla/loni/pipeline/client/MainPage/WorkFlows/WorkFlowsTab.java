package edu.ucla.loni.pipeline.client.MainPage.WorkFlows;

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

public class WorkFlowsTab {

	private ListGrid listWorkflows;
	private AsyncClientServices asyncClientServices;
	VLayout layoutWorkflows = new VLayout();

	public WorkFlowsTab(AsyncClientServices asyncClientServices) {
		this.asyncClientServices = asyncClientServices;
		initializeListWorkflows();
	}

	public WorkFlowsTab() {
		initializeListWorkflows();
	}

	public Tab setTab() {
		Tab tabWorkflows = new Tab("Workflows");

		layoutWorkflows.setSize("100%", "100%");
		layoutWorkflows.setDefaultLayoutAlign(Alignment.LEFT);
		layoutWorkflows.setMembersMargin(10);

		com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"All Workflows");
		intro.setSize("500px", "49px");
		layoutWorkflows.addMember(intro);

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

		// Need to declare these fields here so we can edit their behavior
		final ListGridField stopfield = new ListGridField("stop",
				"Stop/Reset&#160;&#160;&#160;&#160;&#160;");
		stopfield.setAlign(Alignment.CENTER);

		final ListGridField pausefield = new ListGridField("pause",
				"Pause/Rsm&#160;&#160;&#160;&#160;&#160;");
		pausefield.setAlign(Alignment.CENTER);

		final ListGridField viewfield = new ListGridField("view",
				"View&#160;&#160;&#160;&#160;&#160;");
		viewfield.setAlign(Alignment.CENTER);

		listWorkflows.setFields(new ListGridField("workflowID", "Workflow ID"),
				new ListGridField("username",
						"Username&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("state",
						"State&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("startTime",
						"Start Time&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("endTime",
						"End Time&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("duration",
						"Duration&#160;&#160;&#160;&#160;&#160;"),
				new ListGridField("numofnode", "N&#160;&#160;&#160;"),
				new ListGridField("numofinstances", "I&#160;&#160;&#160;"),
				new ListGridField("numBacklab", "B&#160;&#160;&#160;"),
				new ListGridField("numSubmitting", "S&#160;&#160;&#160;"),
				new ListGridField("numQueued", "Q&#160;&#160;&#160;"),
				new ListGridField("numRunning", "R&#160;&#160;&#160;"),
				new ListGridField("numCompleted", "C&#160;&#160;&#160;"),
				stopfield, pausefield, viewfield);

		//No default data
		layoutWorkflows.addMember(listWorkflows);

		tabWorkflows.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent event) {
				asyncClientServices.reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
					@Override
					public void onSuccess(final String xmlData) {
						refreshWorkflows(xmlData);
						System.out.println("Workflows refreshed successfully");
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Workflows refreshed failed");
					}
				});
			}
		});

		Button workflowsrefreshbutton = new Button("Refresh");
		workflowsrefreshbutton.setAlign(Alignment.CENTER);
		layoutWorkflows.addMember(workflowsrefreshbutton);
		workflowsrefreshbutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				asyncClientServices.reqResourceXMLService.getXMLData(new AsyncCallback<String>() {
							@Override
							public void onSuccess(final String xmlData) {
								refreshWorkflows(xmlData);
								System.out.println("Workflows refreshed successfully");
							}

							@Override
							public void onFailure(Throwable caught) {
								System.out.println("Workflows refreshed failed");
							}
						});
			}
		});
		tabWorkflows.setPane(layoutWorkflows);
		return tabWorkflows;
	}

	public void refreshWorkflows(String xml) {

		// remove whitespace
		String cleanXml = xml.replaceAll("\t", "");
		cleanXml.replaceAll("\n", "");

		try {
			Document doc = XMLParser.parse(cleanXml);
			parseWorkflowsXML(doc);
		} catch (DOMParseException e) {
			System.err.println("Could not parse XML file. Check XML file format.");
			return;
		}
	}

	public void parseWorkflowsXML(Document doc) {
		NodeList WorkflowsList = doc.getElementsByTagName("WorkflowEntry");
		int totalWorkflows = WorkflowsList.getLength();
		WorkFlowsRecord array[] = new WorkFlowsRecord[totalWorkflows];
		for (int k = 0; k < totalWorkflows; k++) {
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

				// NumBacklab
				NodeList NumBacklabList = WorkflowsElement
						.getElementsByTagName("NumBacklab");
				Element NumBacklabElement = (Element) NumBacklabList.item(0);
				NodeList textNumBacklabList = NumBacklabElement.getChildNodes();

				// NumSubmitting
				NodeList NumSubmittingList = WorkflowsElement
						.getElementsByTagName("NumSubmitting");
				Element NumSubmittingElement = (Element) NumSubmittingList
						.item(0);
				NodeList textNumSubmittingList = NumSubmittingElement
						.getChildNodes();

				// NumQueued
				NodeList NumQueuedList = WorkflowsElement
						.getElementsByTagName("NumQueued");
				Element NumQueuedElement = (Element) NumQueuedList.item(0);
				NodeList textNumQueuedList = NumQueuedElement.getChildNodes();

				// NumRunning
				NodeList NumRunningList = WorkflowsElement
						.getElementsByTagName("NumRunning");
				Element NumRunningElement = (Element) NumRunningList.item(0);
				NodeList textNumRunningList = NumRunningElement.getChildNodes();

				// NumCompleted
				NodeList NumCompletedList = WorkflowsElement
						.getElementsByTagName("NumCompleted");
				Element NumCompletedElement = (Element) NumCompletedList
						.item(0);
				NodeList textNumCompletedList = NumCompletedElement
						.getChildNodes();

				array[k] = new WorkFlowsRecord(
						((Node) textWorkflowsIDList.item(0)).getNodeValue()
								.trim(),
						((Node) textUsernameList.item(0)).getNodeValue().trim(),
						((Node) textStateList.item(0)).getNodeValue().trim(),
						((Node) textStartTimeList.item(0)).getNodeValue()
								.trim(),
						((Node) textEndTimeList.item(0)).getNodeValue().trim(),
						((Node) textDurationList.item(0)).getNodeValue().trim(),
						((Node) textNumOfNodeList.item(0)).getNodeValue()
								.trim(),
						((Node) textNumOfInstancesList.item(0)).getNodeValue()
								.trim(), ((Node) textNumBacklabList.item(0))
								.getNodeValue().trim(),
						((Node) textNumSubmittingList.item(0)).getNodeValue()
								.trim(), ((Node) textNumQueuedList.item(0))
								.getNodeValue().trim(),
						((Node) textNumRunningList.item(0)).getNodeValue()
								.trim(), ((Node) textNumCompletedList.item(0))
								.getNodeValue().trim());
			}// end if
		}// end loop
		listWorkflows.setData(array);
		// layoutWorkflows.addMember(listWorkflows);
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
						//Not stop, pause or view fields.
						return null;

					}
				} else {
					return null;
				}

			}
		}; // end of function
	}
}
