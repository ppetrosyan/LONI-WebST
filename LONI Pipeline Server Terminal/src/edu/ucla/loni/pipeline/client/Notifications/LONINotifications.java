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

package edu.ucla.loni.pipeline.client.Notifications;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.LONINotificationsHelper;
import com.google.gwt.user.client.ui.NotificationMole;

/**
 * Constructs Passive Notifications for User Interface
 * 
 * @author Jared
 */
public class LONINotifications {

	private final NotificationMole notificationMole;
	private final LONINotificationsHelper helper;
	private final Timer timer;

	/**
	 * Constructor
	 */
	public LONINotifications() {
		notificationMole = new NotificationMole();
		helper = new LONINotificationsHelper(notificationMole);

		timer = new Timer() {
			@Override
			public void run() {
				notificationMole.hideNow();
			}
		};

		configureNotifications();
	}

	/**
	 * Configures Notifications
	 */
	private void configureNotifications() {
		notificationMole.setTitle("LONI Notifications");
		notificationMole.setAnimationDuration(500);
		notificationMole.setHeight("100%");
		notificationMole.setWidth("100%");
		notificationMole.setStyleName("notificationNormal");
	}

	/**
	 * Displays notification message
	 * 
	 * @param message
	 * @param timer
	 */
	public void showMessage(String message) {
		if (message.startsWith("ERROR:")) {
			notificationMole.show(message);
			helper.setBackgroundColor("#F5C6D6");

			// Schedule the timer to run once in 5 seconds.
			timer.schedule(5000);
		} else {
			helper.setBackgroundColor("#E5EDF9");

			notificationMole.show(message);

			// Schedule the timer to run once in 3 seconds.
			timer.schedule(3000);
		}
	}

	/**
	 * Returns the notification object
	 * 
	 * @return notification
	 */
	public NotificationMole getNotificationMole() {
		return notificationMole;
	}
}
