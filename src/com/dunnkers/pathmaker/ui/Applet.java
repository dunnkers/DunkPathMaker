package com.dunnkers.pathmaker.ui;

import java.awt.Container;

import javax.swing.JApplet;

import com.dunnkers.pathmaker.ui.container.ContentPane;
import com.dunnkers.pathmaker.ui.container.ContentPaneModel;


/**
 * 
 * @author Dunnkers
 */
// TODO optimize; generate button not yet working
public class Applet extends JApplet {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		final ContentPane window = new ContentPane(new ContentPaneModel());
		window.initMenuBar(this);
		final Container contentPane = this.getContentPane();
		window.initContentPane(contentPane);
	}
}
