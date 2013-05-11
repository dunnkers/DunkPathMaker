package com.dunnkers.pathmaker;

import java.awt.Container;

import javax.swing.JApplet;

import com.dunnkers.pathmaker.ui.Window;
import com.dunnkers.pathmaker.ui.Window.ButtonBar;

/**
 * 
 * @author Dunnkers
 */
public class DunkPathMakerApplet extends JApplet {
	
	@Override
	public void init() {
		final Window window = new Window();
		final ButtonBar buttonBar = window.new ButtonBar();
		this.setJMenuBar(buttonBar);
		final Container contentPane = this.getContentPane();
		window.initContentPane(contentPane);
	}
}
