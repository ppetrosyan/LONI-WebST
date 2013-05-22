package edu.ucla.loni.pipeline.client.Login;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import edu.ucla.loni.pipeline.client.MainPage.LONI_Pipeline_ST_Tabset_Display;

public class LONI_Pipeline_ST_Login_Display {
	VLayout mainLayout = new VLayout();

	public void buildMainPage() {
		HLayout headLayout = new HLayout();
		headLayout.setMembersMargin(10);
		headLayout.setSize("100%", "3%");

		com.smartgwt.client.widgets.Label LONILabel = new com.smartgwt.client.widgets.Label("<b><font size='6'>LONI Pipeline</font></b>");
		LONILabel.setSize("100%", "100%");
		LONILabel.setAlign(Alignment.LEFT);
		LONILabel.setValign(VerticalAlignment.CENTER);
		headLayout.addMember(LONILabel);

		HLayout loginLayout = new HLayout();
		loginLayout.setMembersMargin(5);
		loginLayout.setSize("100%", "97%");

		FlexTable flexTable = new FlexTable();
		flexTable.setSize("173px", "93px");

		Label labelSignIn = new Label("Sign in to your account");
		flexTable.setWidget(0, 0, labelSignIn);
		
		Label labelUsername = new Label("Username:");
		flexTable.setWidget(1, 0, labelUsername);
		
		TextBox textBoxUsername = new TextBox();
		flexTable.setWidget(1, 1, textBoxUsername);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		
		Label labelPassword = new Label("Password:");
		flexTable.setWidget(2, 0, labelPassword);
		
		PasswordTextBox textBoxPassword = new PasswordTextBox();
		flexTable.setWidget(2, 1, textBoxPassword);
		
		Button buttonSignIn = new Button("Sign in");
		buttonSignIn.setText("Sign In");
		flexTable.setWidget(3, 1, buttonSignIn);
		
		loginLayout.addMember(flexTable);
		
		mainLayout.addMember(headLayout);
		mainLayout.addMember(loginLayout);
		mainLayout.draw();

		buttonSignIn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mainLayout.clear();
				Window.alert("Successful");
				LONI_Pipeline_ST_Tabset_Display wbst = new LONI_Pipeline_ST_Tabset_Display();
				wbst.buildMainPage(null, false);
			}
		});
	}
}
