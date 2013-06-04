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

package edu.ucla.loni.pipeline.client.Login;

import java.sql.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import edu.ucla.loni.pipeline.client.MainPage.LONI_Pipeline_ST_Tabset_Display;

public class LONI_Pipeline_ST_Login_Display {
	private final HLayout mainLayout;
	private LONI_Pipeline_ST_Tabset_Display wbst;
	private final static long TWO_MIN = 1000 * 60 * 2;
	private TextBox textboxUsername;
	private PasswordTextBox textboxPassword;

	public LONI_Pipeline_ST_Login_Display() {
		mainLayout = new HLayout();
	}

	public void buildMainPage(final UserDTO user, final SessionId sessionId) {
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		mainLayout.setLayoutAlign(Alignment.CENTER);
		mainLayout.setAlign(Alignment.CENTER);
		mainLayout.setAlign(VerticalAlignment.CENTER);
		mainLayout.setBackgroundColor("#EFF4FA");

		VLayout loginLayout = new VLayout();
		loginLayout.setLayoutAlign(Alignment.CENTER);
		loginLayout.setAlign(Alignment.CENTER);
		loginLayout.setAlign(VerticalAlignment.CENTER);
		loginLayout.setHeight("250px");
		loginLayout.setWidth("280px");
		loginLayout.setBorder("3px dashed #B0B0B0");
		loginLayout.setMembersMargin(10);

		VLayout padding;

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("100%", "40px");
		loginLayout.addMember(padding);

		Img logo = new Img(GWT.getModuleBaseURL()
				+ "../images/pipelineLogo.gif");
		logo.setLayoutAlign(Alignment.CENTER);
		logo.setAlign(Alignment.CENTER);
		logo.setValign(VerticalAlignment.CENTER);
		logo.setHeight("148px");
		logo.setWidth("160px");
		loginLayout.addMember(logo);

		padding = new VLayout();
		padding.setMembersMargin(0);
		padding.setSize("100%", "20px");
		loginLayout.addMember(padding);

		Label labelSignIn = new Label("Sign in to your account");
		labelSignIn.setAlign(Alignment.CENTER);
		labelSignIn.setHeight("20px");
		labelSignIn.setWidth100();
		loginLayout.addMember(labelSignIn);
		
		FlexTable flexTable = new FlexTable();
		loginLayout.addMember(flexTable);
		flexTable.setWidth("345px");
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		final Label labelUsername = new Label("Username:");
		labelUsername.setAlign(Alignment.CENTER);
		labelUsername.setHeight("20px");
		flexTable.setWidget(0, 0, labelUsername);
		
		textboxUsername = new TextBox();
		textboxUsername.setValue("loni");
		flexTable.setWidget(0, 1, textboxUsername);
		
		final Label labelPassword = new Label("Password:");
		labelPassword.setAlign(Alignment.CENTER);
		labelPassword.setHeight("20px");
		flexTable.setWidget(1, 0, labelPassword);
		
		textboxPassword = new PasswordTextBox();
		textboxPassword.setValue("123");
		flexTable.setWidget(1, 1, textboxPassword);

		VLayout buttonLayout = new VLayout();
		buttonLayout.setLayoutAlign(Alignment.CENTER);
		buttonLayout.setHeight("30px");
		buttonLayout.setWidth("70px");

		Button buttonSignIn = new Button("Sign in");
		buttonLayout.addMember(buttonSignIn);

		loginLayout.addMember(buttonLayout);

		mainLayout.addMember(loginLayout);

		mainLayout.draw();

		buttonSignIn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("onClick()");

				user.setUsername(textboxUsername.getText());
				user.setPassword(textboxPassword.getText());
				
				System.out.println("Username = " + user.getUsername());
				System.out.println("Password = " + user.getPassword());

				if (user.getUsername().equals("loni") == false) {
					Window.alert("Wrong username");
					return;
				}
				
				if (user.getPassword().equals("123") == false) {
					Window.alert("Wrong password");
					return;
				}

				LoginServiceAsync loginServiceAsync = GWT
						.create(LoginService.class);

				System.out.println("After creation");

				AsyncCallback<String> asyncCallback = new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						System.out.println("onClick(): onSuccess");
						if (result != null) {
							System.out
									.println("Set Cookie: result = " + result);
							Cookies.setCookie("session", result, new Date(
									System.currentTimeMillis() + TWO_MIN));
							sessionId.setSessionId(result);

							System.out.println("Login Successful");
							System.out.println("Login session => " + result);
						} else {
							System.out.println("Login Invalid !");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("onClick(): onFailure");
						System.out.println(caught);
					}
				};
				loginServiceAsync.login(user, asyncCallback);

				mainLayout.clear();
				wbst = new LONI_Pipeline_ST_Tabset_Display("Guest");
				wbst.buildMainPage(user, sessionId);
			}
		});
	}
}
