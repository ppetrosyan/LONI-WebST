package edu.ucla.loni.pipeline.client.Notifications;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.NotificationMole;

public class LONINotifications {

	private NotificationMole notificationMole;

	public LONINotifications() {
		notificationMole = new NotificationMole();

		configureNotifications();
	}

	private void configureNotifications() {
		notificationMole.setTitle("LONI Notifications");
		notificationMole.setAnimationDuration(500);
		notificationMole.setHeight("15px");
		notificationMole.setWidth("500px");
	}

	public void showMessage(String message, boolean timer) {
		/*if (notificationMole.isVisible()) {
			notificationMole.hideNow();
		}*/

		notificationMole.show(message);

		/*if(timer) {
			Timer t = new Timer() {
				public void run() {
					notificationMole.show("Welcome, " + userID, false);
				}
			};
			
			// Schedule the timer to run once in 3 seconds.
			t.schedule(3000);
		}*/
	}

	public NotificationMole getNotificationMole() {
		return notificationMole;
	}
}
