package com.google.gwt.user.client.ui; // important for package visibility access

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;

public class LONINotificationsHelper {

	protected NotificationMole notificationMole;

	public LONINotificationsHelper(NotificationMole notificationMole) {
		super();
		this.notificationMole = notificationMole;
	}

	public SpanElement getNotificationText() {
		return notificationMole.notificationText;
	}

	public DivElement getHeightMeasure() {
		return notificationMole.heightMeasure;
	}

	public DivElement getBorderElement() {
		return notificationMole.borderElement;
	}

	/**
	 * Change heightMeasure's background color
	 * 
	 * @param backgroundColor
	 */
	public void setBackgroundColor(String backgroundColor) {

		getBorderElement().getStyle().setBackgroundColor(backgroundColor);
	}
}