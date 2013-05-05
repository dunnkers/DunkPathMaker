package com.dunnkers.pathmaker.ui.worldmap;

import java.awt.Point;
import java.util.ArrayList;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.util.TileMode;

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

	public WorldMapModel() {
		mode = TileMode.PATH;
		mouseLocation = new Point(0, 0);
		tileArray = new ArrayList<Point>();
		maxTileRadius = Configuration.MAX_TILE_RADIUS;
		dragSensitivity = Configuration.INITIAL_DRAG_SENSITIVITY;
	}

	public TileMode getMode() {
		return mode;
	}

	public void setMode(TileMode mode) {
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
}
