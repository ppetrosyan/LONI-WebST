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

package edu.ucla.loni.pipeline.client.MainPage.Preferences.Packages;

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

public class PackagesTab {
	private final ListGrid listUsersPackages = new ListGrid();
	VLayout layoutUsersPackages = new VLayout();

	public PackagesTab() {

	}

	public Tab setTab() {
		Tab tabPackages = new Tab("Packages");

		layoutUsersPackages.setSize("100%", "100%");
		layoutUsersPackages.setMembersMargin(10);

		com.smartgwt.client.widgets.Label intro = new com.smartgwt.client.widgets.Label(
				"Please specify package name and version, the location, and optionally sources and variables.<br>Double-click any cell to edit.");
		intro.setSize("800px", "49px");
		layoutUsersPackages.addMember(intro);

		listUsersPackages.setCanEdit(true);
		listUsersPackages.setEditEvent(ListGridEditEvent.DOUBLECLICK);
		listUsersPackages.setListEndEditAction(RowEndEditAction.NEXT);
		listUsersPackages.setSize("100%", "100%");
		listUsersPackages.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		listUsersPackages.setCanPickFields(false);
		listUsersPackages.setCanFreezeFields(false);
		listUsersPackages.setAutoFitFieldWidths(true);
		listUsersPackages.setCanRemoveRecords(true);

		// specific which field you want to expend
		listUsersPackages.setAutoFitExpandField("location");

		listUsersPackages
				.setFields(
						new ListGridField(
								"package_name",
								"Package Name&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField(
								"version",
								"Version&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField("location", "Location"),
						new ListGridField(
								"variables",
								"Variables&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"),
						new ListGridField(
								"sources",
								"Sources&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;")

				);

		// get default data from PackagesData.java
		listUsersPackages.setData(PackagesData.getRecords());
		layoutUsersPackages.addMember(listUsersPackages);

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
						listUsersPackages.startEditingNew();
					}
				});

		// Remove data
		IButton RemoveButton = new IButton("Remove");
		RemoveButton.setIcon(listUsersPackages.getRemoveIcon());
		hLayout1.addMember(RemoveButton);
		RemoveButton
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						listUsersPackages.removeSelectedData();
					}
				});

		layoutUsersPackages.addMember(hLayout1);
		tabPackages.setPane(layoutUsersPackages);

		return tabPackages;
	}

	public void parsePackageXML(Document doc) {
		NodeList PackagesList = doc.getElementsByTagName("Packages");
		int totalPackages = PackagesList.getLength();
		PackagesRecord array[] = new PackagesRecord[totalPackages];
		for (int k = 0; k < totalPackages; k++) {
			Node PackageNode = PackagesList.item(k);
			if (PackageNode.getNodeType() == Node.ELEMENT_NODE) {
				Element PackagesElement = (Element) PackageNode;

				// PackageName
				NodeList PkgList = PackagesElement
						.getElementsByTagName("PackageName");
				Element PkgElement = (Element) PkgList.item(0);
				NodeList textPkgList = PkgElement.getChildNodes();

				// Version
				NodeList VerList = PackagesElement
						.getElementsByTagName("Version");
				Element VerElement = (Element) VerList.item(0);
				NodeList textVerList = VerElement.getChildNodes();

				// Location
				NodeList LocList = PackagesElement
						.getElementsByTagName("Location");
				Element ageElement = (Element) LocList.item(0);
				NodeList textLocList = ageElement.getChildNodes();

				// Variables
				NodeList VarList = PackagesElement
						.getElementsByTagName("Variables");
				Element VarElement = (Element) VarList.item(0);
				NodeList textVarList = VarElement.getChildNodes();

				// Location
				NodeList SrcList = PackagesElement
						.getElementsByTagName("Sources");
				Element SrcElement = (Element) SrcList.item(0);
				NodeList textSrcList = SrcElement.getChildNodes();

				array[k] = new PackagesRecord(textPkgList.item(0)
						.getNodeValue().trim(), textVerList.item(0)
						.getNodeValue().trim(), textLocList.item(0)
						.getNodeValue().trim(), textVarList.item(0)
						.getNodeValue().trim(), textSrcList.item(0)
						.getNodeValue().trim());
			}// end if
		}// end loop
		listUsersPackages.setData(array);
		layoutUsersPackages.addMember(listUsersPackages);
	}

}
