package com.dunnkers.pathmaker.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Dunnkers
 */
public enum CodeFormat {
	VINSERT("vInsert", true, WorldMap.OLD_SCHOOL),
	OSBOT("OSBot", false, WorldMap.OLD_SCHOOL),
	TRIBOT("TRiBot", false, WorldMap.OLD_SCHOOL, WorldMap.RECENT), 
	RSBOT("RSBot", false, WorldMap.RECENT);
	
	private final String name;
	private final List<WorldMap> worldMaps;
	private final boolean enabled;
	
	private static final String DEFAULT_TEXT = "Not supported yet!";

	private CodeFormat(String name, boolean enabled, final WorldMap... worldMaps) {
		this.name = name;
		this.worldMaps = Arrays.asList(worldMaps);
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
			
			break;
		default:
			output.append(DEFAULT_TEXT);
			break;
		}
		return output.toString();
	}
	
	private String getPath() {
		switch (this) {
		case VINSERT:
			return "\tprivate final Path path = new Path(\n";
		default:
			return DEFAULT_TEXT;
		}
	}
	
	private String getTile(final Point point) {
		switch (this) {
		case VINSERT:
			return String.format("new Tile(%s, %s)", point.x, point.y);
		default:
			return DEFAULT_TEXT;
		}
	}

	public List<WorldMap> getWorldMaps() {
		return worldMaps;
	}
}
