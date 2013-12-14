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
	OSBOT("OSBot", true, WorldMap.OLD_SCHOOL), 
	TRIBOT_OLD_SCHOOL("TRiBot", true, WorldMap.OLD_SCHOOL),
	TRIBOT_RECENT("TRiBot", true, WorldMap.RECENT), 
	RSBOT("RSBot", true, WorldMap.RECENT);

	private final String name;
	private final List<WorldMap> worldMaps;
	private final boolean enabled;

	private static final String DEFAULT_TEXT = "Not supported yet!";

	private CodeFormat(String name, boolean enabled,
			final WorldMap... worldMaps) {
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

	public String getCode(final ArrayList<Point> tileArray,
			final TileMode tileMode) {
		final StringBuilder output = new StringBuilder(200);
		/*
		 * TODO convert to tile here, and store tile array as mouse points: more
		 * efficient in drawing paint
		 */
		final Object[] args = getCodeFormatArguments(tileMode);
		if (args == null) {
			return DEFAULT_TEXT;
		}
		output.append(String.format("\tprivate final %s"
				+ getFormattedTiles(tileArray) + "%s", args));
		return output.toString();
	}
	
	private final Object[] getCodeFormatArguments(final TileMode tileMode) {
		switch (tileMode) {
		case PATH:
			return getPath();
		case AREA:
			return getArea();
		default:
			return null;
		}
	}

	private Object[] getPath() {
		switch (this) {
		case RSBOT:
			return new String[] { "Tile[] path = new Tile[] {\n", "\t};" };
		case OSBOT:
			return new String[] {
					"Position[] path = new Position[] {\n",
					"\t};" };
		case TRIBOT_OLD_SCHOOL:
			return new String[] { "RSTile[] path = new RSTile[] {\n", "\t};" };
		default:
			return null;
		}
	}

	private Object[] getArea() {
		switch (this) {
		case RSBOT:
			return new String[] { "Area area = new Area(\n", "\t);" };
		case OSBOT:
			return new String[] {
					"Area area = new Area(new Position[] {\n",
					"\t});" };
		case TRIBOT_OLD_SCHOOL:
			return new String[] { "RSArea area = new RSArea(\n", "\t);" };
		default:
			return null;
		}
	}

	private String getTile(final Point point) {
		return String.format(getTileFormat(), point.x, point.y, 0);
	}

	private String getTileFormat() {
		switch (this) {
		case RSBOT:
			return "new Tile(%d, %d, %d)";
		case OSBOT:
			return "new Position(%d, %d, %d)";
		case TRIBOT_OLD_SCHOOL:
			return "new RSTile(%d, %d, %d)";
		default:
			return DEFAULT_TEXT;
		}
	}

	private String getFormattedTiles(final ArrayList<Point> tileArray) {
		final StringBuilder output = new StringBuilder(200);
		for (int i = 0; i < tileArray.size(); i++) {
			final boolean lastTile = tileArray.size() - 1 == i;
			output.append("\t\t\t" + getTile(tileArray.get(i))
					+ (lastTile ? "" : ",") + "\n");
		}
		return output.toString();
	}

	public List<WorldMap> getWorldMaps() {
		return worldMaps;
	}

	public boolean canGenerate(final ArrayList<Point> tileArray, final TileMode tileMode) {
		switch (tileMode) {
		case PATH:
			return tileArray.size() > 0;
		case AREA:
			switch (this) {
			case OSBOT:
				return tileArray.size() >= 3;
			default:
				return tileArray.size() >= 2;
			}
		default:
			return false;
		}
	}
}
