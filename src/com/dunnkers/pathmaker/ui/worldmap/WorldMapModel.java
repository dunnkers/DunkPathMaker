package com.dunnkers.pathmaker.ui.worldmap;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.util.TileMode;
import com.dunnkers.pathmaker.util.WorldMap;
import com.dunnkers.util.Enums;

/**
 * 
 * @author Dunnkers
 */
public class WorldMapModel {

	private TileMode mode;
	private Point mouseLocation;
	private ArrayList<Point> tileArray;
	private int maxTileRadius;
	private int dragSensitivity;
	private WorldMap worldMap;

	private PropertyChangeListener modePropertyChangeListener;

	private final Preferences preferences;

	public WorldMapModel() {
		preferences = Preferences
				.userNodeForPackage(Configuration.PREFERENCE_PACKAGE);
		mode = Enums.findEnumObject(TileMode.class, preferences.get(
				Configuration.MODE_KEY,
				Configuration.INITIAL_MODE.name()),
				Configuration.INITIAL_MODE);
		mouseLocation = new Point(0, 0);
		tileArray = new ArrayList<Point>();
		maxTileRadius = Configuration.MAX_TILE_RADIUS;
		dragSensitivity = preferences.getInt(
				Configuration.DRAG_SENSITIVITY_KEY,
				Configuration.INITIAL_DRAG_SENSITIVITY);
		worldMap = Enums.findEnumObject(WorldMap.class, preferences.get(
				Configuration.WORLD_MAP_KEY,
				Configuration.INITIAL_WORLD_MAP.name()),
				Configuration.INITIAL_WORLD_MAP);
	}

	public TileMode getMode() {
		return mode;
	}

	public void setMode(TileMode mode) {
		this.setMode(mode, null);
	}

	public void setMode(TileMode mode, final Object source) {
		if (modePropertyChangeListener != null) {
			modePropertyChangeListener
					.propertyChange(new PropertyChangeEvent(source,
							Configuration.MODE_KEY,
							this.mode,
							mode));
		}
		this.mode = mode;
		preferences.put(Configuration.MODE_KEY, this.mode.name());
	}

	public Point getMouseLocation() {
		return mouseLocation;
	}

	public void setMouseLocation(Point mouseLocation) {
		this.mouseLocation = mouseLocation;
	}

	public ArrayList<Point> getTileArray() {
		return tileArray;
	}

	public void setTileArray(ArrayList<Point> tileArray) {
		this.tileArray = tileArray;
	}

	public int getMaxTileRadius() {
		return maxTileRadius;
	}

	public void setMaxTileRadius(int maxTileRadius) {
		this.maxTileRadius = maxTileRadius;
	}

	public int getDragSensitivity() {
		return dragSensitivity;
	}

	public void setDragSensitivity(int dragSensitivity) {
		this.dragSensitivity = dragSensitivity;
		preferences.putInt(Configuration.DRAG_SENSITIVITY_KEY,
				this.dragSensitivity);
	}

	public void setModePropertyChangeListener(
			final PropertyChangeListener modePropertyChangeListener) {
		this.modePropertyChangeListener = modePropertyChangeListener;
	}

	public WorldMap getWorldMap() {
		return worldMap;
	}

	/*
	 * TODO fix; when on old school map with osbot selected, then going to
	 * recent, does not select a new code format
	 */
	public void setWorldMap(WorldMap worldMap) {
		this.worldMap = worldMap;
		preferences.put(Configuration.WORLD_MAP_KEY, this.worldMap.name());
	}
}
