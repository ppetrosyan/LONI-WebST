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

package edu.ucla.loni.pipeline.client.MainPage.Preferences.Executables;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class ExecutablesTab {

	private final ListGrid listUsersExecutables = new ListGrid();
	VLayout layoutUsersExecutables = new VLayout();

	public ExecutablesTab() {

	}

	public Tab setTab() {

		Tab tabExecutables = new Tab("Executables");
		layoutUsersExecutables.setSize("100%", "100%");
		layoutUsersExecutables.setMembersMargin(5);

		com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"Please specify executable name, version and the location.<br>Double-click any cell to edit.");
		intro.setSize("800px", "49px");
		layoutUsersExecutables.addMember(intro);

		listUsersExecutables.setCanEdit(true);
		listUsersExecutables.setEditEvent(ListGridEditEvent.DOUBLECLICK);
		listUsersExecutables.setListEndEditAction(RowEndEditAction.NEXT);
		listUsersExecutables.setSize("100%", "100%");
		listUsersExecutables.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersExecutables.setCanPickFields(false);
		listUsersExecutables.setCanFreezeFields(false);
		listUsersExecutables.setAutoFitFieldWidths(true);
		listUsersExecutables.setCanRemoveRecords(true);

		// specific which field you want to expend
		listUsersExecutables.setAutoFitExpandField("location");

		listUsersExecutables
				.setFields(
						new ListGridField(
								"executables_name",
								"Executable Name&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField(
								"version",
								"Version&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField("location", "Location")

				);
		// get data from ExecutablesData.java
		listUsersExecutables.setData(ExecutablesData.getRecords());
		layoutUsersExecutables.addMember(listUsersExecutables);

		// Hlayout
		// horizontal layout
		HLayout hLayout1 = new HLayout();
		hLayout1.setMembersMargin(10);

		// add data
		IButton AddButton = new IButton("Add");
		AddButton.setIcon("addicon.png");
		hLayout1.addMember(AddButton);
		AddButton
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						listUsersExecutables.startEditingNew();
					}
				});

		// Remove data
		IButton RemoveButton = new IButton("Remove");
		RemoveButton.setIcon(listUsersExecutables.getRemoveIcon());
		hLayout1.addMember(RemoveButton);
		RemoveButton
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						listUsersExecutables.removeSelectedData();
					}
				});

		layoutUsersExecutables.addMember(hLayout1);
		tabExecutables.setPane(layoutUsersExecutables);

		return tabExecutables;
	}

	public void parseExecutablesXML(Document doc) {
		NodeList ExecutablesList = doc.getElementsByTagName("Executables");
		int totalExecutables = ExecutablesList.getLength();
		ExecutablesRecord array[] = new ExecutablesRecord[totalExecutables];
		for (int k = 0; k < totalExecutables; k++) {
			Node ExecutablesNode = ExecutablesList.item(k);
			if (ExecutablesNode.getNodeType() == Node.ELEMENT_NODE) {
				Element ExecutablesElement = (Element) ExecutablesNode;

				// PackageName
				NodeList PkgList = ExecutablesElement
						.getElementsByTagName("ExecutablesName");
				Element PkgElement = (Element) PkgList.item(0);
				NodeList textPkgList = PkgElement.getChildNodes();

				// Version
				NodeList VerList = ExecutablesElement
						.getElementsByTagName("Version");
				Element VerElement = (Element) VerList.item(0);
				NodeList textVerList = VerElement.getChildNodes();

				// Location
				NodeList LocList = ExecutablesElement
						.getElementsByTagName("Location");
				Element ageElement = (Element) LocList.item(0);
				NodeList textLocList = ageElement.getChildNodes();

				array[k] = new ExecutablesRecord(textPkgList.item(0)
						.getNodeValue().trim(), textVerList.item(0)
						.getNodeValue().trim(), textLocList.item(0)
						.getNodeValue().trim());
			}// end if
		}// end loop
		listUsersExecutables.setData(array);
		layoutUsersExecutables.addMember(listUsersExecutables);
	}
}
