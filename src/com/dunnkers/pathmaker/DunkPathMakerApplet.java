package com.dunnkers.pathmaker;

import java.awt.Container;

import javax.swing.JApplet;

import com.dunnkers.pathmaker.ui.Window;
import com.dunnkers.pathmaker.ui.WindowModel;

/**
 * 
 * @author Dunnkers
 */
public class DunkPathMakerApplet extends JApplet {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		final Window window = new Window(new WindowModel());
		window.initJMenuBar(this);
		final Container contentPane = this.getContentPane();
		window.initContentPane(contentPane);
	}
}
