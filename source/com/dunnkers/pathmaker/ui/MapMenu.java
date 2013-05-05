package com.dunnkers.pathmaker.ui;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/*
 * TODO is actually part of 'settings'
 */
public class MapMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private final ButtonGroup buttonGroup;
	private final JRadioButtonMenuItem oldschool;
	private final JRadioButtonMenuItem newschool;

	public MapMenu(final String text) {
		this.buttonGroup = new ButtonGroup();
		this.oldschool = new JRadioButtonMenuItem("Old school (2007)");
		this.oldschool.setSelected(true);
		this.newschool = new JRadioButtonMenuItem("Recent (EOC)");
		this.newschool.setEnabled(false);
		this.buttonGroup.add(oldschool);
		this.buttonGroup.add(newschool);
		this.setText(text);
		init();
	}

	public void init() {
		add(oldschool);
		add(newschool);
	}
}