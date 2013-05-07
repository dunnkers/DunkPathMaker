package com.dunnkers.pathmaker;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.AnalyticsRequestData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.dmurph.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;
import com.dunnkers.pathmaker.ui.Window;

/**
 * 
 * @author Dunnkers
 */
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
		JGoogleAnalyticsTracker.setProxy(System.getenv("http_proxy"));
		final AnalyticsConfigData config = new AnalyticsConfigData("UA-40680192-3");
		final JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker(config,
				GoogleAnalyticsVersion.V_4_7_2);
		final AnalyticsRequestData request = new AnalyticsRequestData();
		request.setHostName("www.dunnkers.com");
		request.setPageTitle("Application start");
		request.setPageURL("com.dunnkers.pathmaker.DunkPathMaker.java");
		request.setReferrer("www.dunnkers.com", "com.dunnkers");
		tracker.makeCustomRequest(request);
	}
}
