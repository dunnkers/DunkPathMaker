package com.dunnkers.pathmaker.ui.menu;

import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import com.dunnkers.pathmaker.util.WorldMap;

public class MapMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private final ButtonGroup buttonGroup;
	private final ArrayList<JRadioButtonMenuItem> maps;

	public MapMenu(final String text) {
		this.setText(text);
		this.buttonGroup = new ButtonGroup();
		maps = new ArrayList<JRadioButtonMenuItem>();
		for (final WorldMap worldMap : WorldMap.values()) {
			final JRadioButtonMenuItem map = new JRadioButtonMenuItem(worldMap.getName());
			map.setEnabled(map.isEnabled());
			this.buttonGroup.add(map);
			maps.add(map);
		}
		maps.get(0).setSelected(true);
		init();
	}

	public void init() {
		for (final JRadioButtonMenuItem map : maps) {
			add(map);
		}
	}
}