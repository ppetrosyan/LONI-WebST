package edu.ucla.loni.pipeline.client.MainPage.Notifications;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NotificationImpl {
	public static final int PERMISSION_ALLOWED = 0;
	public static final int PERMISSION_NOT_ALLOWED = 1;
	public static final int PERMISSION_DENIED = 2;
	private JavaScriptObject jsObject;

	protected NotificationImpl() {

	}

	public native int checkPermission() /*-{
                return $wnd.webkitNotifications.checkPermission();
        }-*/;

	public void requestPermission(AsyncCallback<Void> callback) {
		this.requestPermission(this, callback);
	}

	private native void requestPermission(NotificationImpl x, AsyncCallback<Void> callback) /*-{
                $wnd.webkitNotifications.requestPermission($entry(function() {
                        x.@edu.ucla.loni.pipeline.client.MainPage.Notifications.NotificationImpl::callbackRequestPermission(Lcom/google/gwt/user/client/rpc/AsyncCallback;)(callback);
                }));
        }-*/;

	private void callbackRequestPermission(AsyncCallback<Void> callback) {
		if (callback != null) {
			callback.onSuccess(null);
		}
	}

	public void createNotification(String iconUrl, String title, String body) {
		this.jsObject = null;
		this.jsObject = this.createJSNotification(iconUrl, title, body);
	}

	public void createNotification(String contentUrl) {
		this.jsObject = null;
		this.jsObject = this.createHtmlNotification(contentUrl);
	}

	private native JavaScriptObject createJSNotification(String iconUrl, String title, String body) /*-{
                return $wnd.webkitNotifications.createNotification(iconUrl, title, body);
        }-*/;

        private native JavaScriptObject createHtmlNotification(String contentUrl) /*-{
                return $wnd.webkitNotifications.createHTMLNotification(contentUrl);
        }-*/;

        public native void show() /*-{
                var obj = this.@edu.ucla.loni.pipeline.client.MainPage.Notifications.NotificationImpl::jsObject;
                obj.show();
        }-*/;

}
