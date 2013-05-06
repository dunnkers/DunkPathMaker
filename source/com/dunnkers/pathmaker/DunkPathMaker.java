package com.dunnkers.pathmaker;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.AnalyticsRequestData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.dmurph.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;
import com.dunnkers.pathmaker.ui.Window;


/**
 * 
 * @author Dunnkers
 */
// TODO create auto updater
public class DunkPathMaker {

	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				track();
			};
		}.start();
		final Window window = new Window();
		window.setVisible(true);
	}

	/*// adds an application track record to this analytic id
	private static void track() {
		final JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker(Configuration.APPLICATION_TITLE,
				String.valueOf(Configuration.APPLICATION_VERSION),
				"UA-40680192-1");
		final FocusPoint focusPoint = new FocusPoint("Track");
		tracker.trackAsynchronously(focusPoint);
	}*/
	
	private static void track() {
		JGoogleAnalyticsTracker.setProxy(System.getenv("http_proxy"));
		AnalyticsConfigData config = new AnalyticsConfigData("UA-40680192-3");
		// AWTSystemPopulator.populateConfigData(config); v 1.2.1 only
		JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker(config,
				GoogleAnalyticsVersion.V_4_7_2);
		//tracker.setDispatchMode(DispatchMode.SINGLE_THREAD);
		// unnecessary, thread already created in main
		
		AnalyticsRequestData d = new AnalyticsRequestData();
		d.setHostName("www.dunnkers.com");
		d.setPageTitle("Application start");
		d.setPageURL("com.dunnkers.pathmaker.DunkPathMaker.java");
		d.setReferrer("www.dunnkers.com", "com.dunnkers");
		tracker.makeCustomRequest(d);
		/*d.setEventCategory("Application");
		d.setEventAction("start");
		tracker.makeCustomRequest(d);*/
		//JGoogleAnalyticsTracker.completeBackgroundTasks(1000);
	}
}
