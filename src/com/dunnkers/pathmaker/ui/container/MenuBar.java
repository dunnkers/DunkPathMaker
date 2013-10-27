package com.dunnkers.pathmaker.ui.container;

import javax.swing.JMenuBar;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.ui.menu.HelpMenu;
import com.dunnkers.pathmaker.ui.menu.MapMenu;
import com.dunnkers.pathmaker.ui.menu.SettingsMenu;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapModel;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private final SettingsMenu settingsMenu;
	private final MapMenu mapMenu;
	private final HelpMenu help;

	public MenuBar(final ContentPaneModel contentPaneModel,
			final WorldMapModel worldMapModel) {
		this.settingsMenu = new SettingsMenu("Settings",
				contentPaneModel,
				worldMapModel);
		this.settingsMenu.setIcon(Configuration.ICON_SETTINGS.getIcon());
		this.mapMenu = new MapMenu("Map", worldMapModel);
		this.mapMenu.setIcon(Configuration.ICON_MAP_16.getIcon());
		this.help = new HelpMenu("Help", contentPaneModel.getComponent());
		this.help.setIcon(Configuration.ICON_HELP.getIcon());
		init();
	}

	public void init() {
		add(settingsMenu);
		add(mapMenu);
		add(help);
	}

	public SettingsMenu getSettingsMenu() {
		return settingsMenu;
	}

	public MapMenu getMapMenu() {
		return mapMenu;
	}
}