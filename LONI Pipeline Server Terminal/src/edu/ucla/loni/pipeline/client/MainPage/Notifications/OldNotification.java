package edu.ucla.loni.pipeline.client.MainPage.Notifications;

public class OldNotification {
	public static native boolean isDesktopNotificationSupported() /*-{
            return typeof $wnd.webkitNotifications != "undefined";
    }-*/;

	private native void fireSimpleNotification(String iconUrl, String title, String content) /*-{
            if ($wnd.webkitNotifications.checkPermission() == 0) {
                    $wnd.webkitNotifications.createNotification(iconUrl, title, content).show();
            } else {
                    $wnd.webkitNotifications.requestPermission();
            }
    }-*/;

	private native void fireHtmlNotification(String contentUrl) /*-{
            if ($wnd.webkitNotifications.checkPermission() == 0) {
                    $wnd.webkitNotifications.createHTMLNotification(contentUrl).show();
            } else {
                    $wnd.webkitNotifications.requestPermission();
            }
    }-*/;

	private String contentUrl;
	private String iconUrl;
	private String title;
	private String content;
	private boolean byUrl;

	public OldNotification(String contentUrl) {
		this.contentUrl = contentUrl;
		this.byUrl = true;
	}

	public OldNotification(String iconUrl, String title, String content) {
		this.iconUrl = iconUrl;
		this.title = title;
		this.content = content;
		this.byUrl = false;
	}

	private native void requestPermission() /*-{
            if ($wnd.webkitNotifications.checkPermission() != 0) {
                    $wnd.webkitNotifications.requestPermission();
            }
    }-*/;

	public void fire() {
		if (isDesktopNotificationSupported()) {
			if (this.byUrl) {
				this.fireHtmlNotification(contentUrl);
			} else {
				this.fireSimpleNotification(iconUrl, title, content);
			}
		}
	}

}