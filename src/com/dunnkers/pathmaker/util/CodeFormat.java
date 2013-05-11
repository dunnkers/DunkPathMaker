package com.dunnkers.pathmaker.util;

import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 * @author Dunnkers
 */
public enum CodeFormat {
	OSBOT("OSBot", WorldMap.OLD_SCHOOL, false), 
	VINSERT("vInsert", WorldMap.RECENT, true);
	
	private final String name;
	private final WorldMap worldMap;
	private final boolean enabled;

	private CodeFormat(String name, final WorldMap worldMap, boolean enabled) {
		this.name = name;
		this.worldMap = worldMap;
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public String getCode(final ArrayList<Point> tileArray, final TileMode tileMode) {
		final StringBuilder output = new StringBuilder(200);
		// TODO convert to tile here, and store tile array as mouse points; 
		// more efficient in drawing paint
		switch (tileMode) {
		case PATH:
			output.append(getPath());
			for (int i = 0; i < tileArray.size(); i++) {
				final boolean lastTile = tileArray.size() - 1 == i;
				output.append("\t\t\t"+getTile(tileArray.get(i))+(lastTile ? "" : ",")+"\n");
			}
			output.append("\t);");
			break;
		case AREA:
			output.append("Not supported yet!");
			break;
		}
		return output.toString();
	}
	
	private String getPath() {
		switch (this) {
		case OSBOT:
			
			break;
		case VINSERT:
			return "\tprivate final Path path = new Path(\n";
		}
		return "";
	}
	
	private String getTile(final Point point) {
		switch (this) {
		case OSBOT:
			
			break;
		case VINSERT:
			return String.format("new Tile(%s, %s)", point.x, point.y);
		}
		return "";
		
	}

	public WorldMap getWorldMap() {
		return worldMap;
	}
}
