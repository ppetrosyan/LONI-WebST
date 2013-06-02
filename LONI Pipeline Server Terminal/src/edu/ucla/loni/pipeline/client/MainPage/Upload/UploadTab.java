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

package edu.ucla.loni.pipeline.client.MainPage.Upload;

import java.util.LinkedHashMap;
import java.util.Map;

import org.moxieapps.gwt.uploader.client.Uploader;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

import edu.ucla.loni.pipeline.client.Requesters.RefreshAllTabs.LONIDataRequester;
import edu.ucla.loni.pipeline.client.Upload.Features.LONIDragandDropLabel;
import edu.ucla.loni.pipeline.client.Upload.Uploaders.LONIFileUploader;

public class UploadTab {

	private final LONIDataRequester dataRequester;

	public UploadTab(LONIDataRequester dataRequester) {
		this.dataRequester = dataRequester;
	}

	public Tab setTab() {
		Tab tabUpload = new Tab("Upload");

		VLayout layoutMain = new VLayout();
		layoutMain.setSize("100%", "100%");
		layoutMain.setMembersMargin(0);

		VLayout layoutUploads = new VLayout();
		layoutUploads.setSize("255px", "275px");
		layoutUploads.setAlign(VerticalAlignment.BOTTOM);
		layoutUploads.setMembersMargin(0);

		com.smartgwt.client.widgets.Label labelLocal = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Option 1: From Local Machine</font></b>");
		labelLocal.setSize("250px", "20px");
		layoutMain.addMember(labelLocal);

		final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();

		final LONIFileUploader LONIfileUploader = new LONIFileUploader(
				cancelButtons, layoutUploads, dataRequester);

		if (Uploader.isAjaxUploadWithProgressEventsSupported()) {
			final LONIDragandDropLabel fileuploadLabel = new LONIDragandDropLabel(
					"Drop Files Here", LONIfileUploader, cancelButtons,
					layoutUploads);
			layoutUploads.addMember(fileuploadLabel);

			com.smartgwt.client.widgets.Label orLabel = new com.smartgwt.client.widgets.Label(
					"------ OR ------");
			layoutUploads.addMember(orLabel);
		}

		layoutUploads.addMember(LONIfileUploader);

		layoutMain.addMember(layoutUploads);

		// add line
		com.smartgwt.client.widgets.Label line = new com.smartgwt.client.widgets.Label(
				"<hr>");
		layoutMain.addMember(line);

		com.smartgwt.client.widgets.Label labelRemote = new com.smartgwt.client.widgets.Label(
				"<b><font size=2>Option 2: From Remote Web Service</font></b>");
		labelRemote.setSize("300px", "20px");
		layoutMain.addMember(labelRemote);

		VLayout layoutWebUploads = new VLayout();
		layoutWebUploads.setSize("200px", "135px");
		layoutWebUploads.setAlign(VerticalAlignment.BOTTOM);
		layoutWebUploads.setMembersMargin(10);

		final DynamicForm fileUploadForm = new DynamicForm();

		final TextItem textFieldUsername = new TextItem("ti_username",
				"Username");
		textFieldUsername.setRequired(true);

		final PasswordItem passwordField = new PasswordItem("pi_password",
				"Password");
		passwordField.setRequired(true);

		final TextItem textFieldWebUrl = new TextItem("ti_weburl", "URL");
		textFieldWebUrl.setRequired(true);

		fileUploadForm.setFields(new FormItem[] { textFieldUsername,
				passwordField, textFieldWebUrl });
		layoutWebUploads.addMember(fileUploadForm);

		Button webUrlBtn = new Button("Retrieve");
		webUrlBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if ((fileUploadForm.getValueAsString("ti_weburl")).length() == 0) {
					Window.alert("Missing Web URL");
					return;
				}
				if ((fileUploadForm.getValueAsString("ti_username")).length() == 0) {
					Window.alert("Missing username");
					return;
				}
				if ((fileUploadForm.getValueAsString("pi_password")).length() == 0) {
					Window.alert("Missing Password");
					return;
				}

				dataRequester.getWebUrlXml(
						fileUploadForm.getValueAsString("ti_weburl"),
						fileUploadForm.getValueAsString("ti_username"),
						fileUploadForm.getValueAsString("pi_password"));
			}
		});
		layoutWebUploads.addMember(webUrlBtn);

		layoutMain.addMember(layoutWebUploads);

		tabUpload.setPane(layoutMain);
		return tabUpload;
	}
}
