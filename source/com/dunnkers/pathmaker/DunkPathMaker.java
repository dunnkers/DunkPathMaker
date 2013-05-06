package com.dunnkers.pathmaker;

import com.boxysystems.jgoogleanalytics.FocusPoint;
import com.boxysystems.jgoogleanalytics.JGoogleAnalyticsTracker;
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

	private static void track() {
		final JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker(Configuration.APPLICATION_TITLE,
				String.valueOf(Configuration.APPLICATION_VERSION),
				"UA-40680192-1");
		final FocusPoint focusPoint = new FocusPoint("Track");
		tracker.trackAsynchronously(focusPoint);
	}
}
