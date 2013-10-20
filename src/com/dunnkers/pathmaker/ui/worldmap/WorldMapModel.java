package com.dunnkers.pathmaker.ui.worldmap;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.util.TileMode;
import com.dunnkers.pathmaker.util.WorldMap;

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

	public WorldMapModel() {
		mode = TileMode.PATH;
		mouseLocation = new Point(0, 0);
		tileArray = new ArrayList<Point>();
		maxTileRadius = Configuration.MAX_TILE_RADIUS;
		dragSensitivity = Configuration.INITIAL_DRAG_SENSITIVITY;
		worldMap = Configuration.INITIAL_WORLD_MAP;
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
							"mode",
							this.mode,
							mode));
		}
		this.mode = mode;
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
	}

	public void setModePropertyChangeListener(
			final PropertyChangeListener modePropertyChangeListener) {
		this.modePropertyChangeListener = modePropertyChangeListener;
	}

	public WorldMap getWorldMap() {
		return worldMap;
	}

	public void setWorldMap(WorldMap worldMap) {
		this.worldMap = worldMap;
	}
}
