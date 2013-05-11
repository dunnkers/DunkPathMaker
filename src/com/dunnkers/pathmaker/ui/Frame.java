package com.dunnkers.pathmaker.ui;

import java.awt.Container;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.ui.Window.ButtonBar;
import com.dunnkers.util.resource.ResourcePath;

/**
 * 
 * @author Dunnkers
 */
public class Frame extends JFrame {

	
	private static final long serialVersionUID = 1L;

	public Frame() {
		final Window window = new Window(new WindowModel());
		window.initJMenuBar(this);
		
		final Container contentPane = this.getContentPane();
		window.initContentPane(contentPane);
		
		this.setTitle(Configuration.WINDOW_TITLE);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		final ArrayList<Image> icons = new ArrayList<Image>();
		for (final ResourcePath ICON_MAP_X : Configuration.ICON_MAP) {
			icons.add(ICON_MAP_X.getImage());
		}
		this.setIconImages(icons);
		this.pack();
		this.setSize(Configuration.WINDOW_SIZE);
		this.setLocationRelativeTo(this.getOwner());
	}
}
