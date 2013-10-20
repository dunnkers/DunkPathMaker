package com.dunnkers.pathmaker.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dunnkers.awt.AwtMath;

/**
 * 
 * @author Dunnkers
 */
public enum CodeFormat {
	//VINSERT("vInsert", false, WorldMap.OLD_SCHOOL), 
	OSBOT("OSBot", false, WorldMap.OLD_SCHOOL), 
	TRIBOT("TRiBot", false, WorldMap.OLD_SCHOOL, WorldMap.RECENT), 
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
		StringBuilder output = new StringBuilder(200);
		// TODO convert to tile here, and store tile array as mouse points;
		// more efficient in drawing paint
		switch (tileMode) {
		case PATH:
			output.append(getPath());
			output.append(geFormattedTiles(tileArray));
			output.append("\t);");
			break;
		case AREA:
			output.append(getArea(tileArray));
			switch (this) {
			/*case VINSERT:
				if (tileArray.size() > 2) {
					output = new StringBuilder(50);
					output.append("You selected too many tiles!\n");
					output.append("A vInsert Area only supports a rectangle which is up to 2 tiles.");
					return output.toString();
				} else if (tileArray.size() < 2) {
					output = new StringBuilder(50);
					output.append("You haven't selected enough tiles!\n");
					output.append("A vInsert Area needs at least 2 tiles.");
					return output.toString();
				} else {
					// TODO when converting the todo, notice this!
					final Rectangle rectangle = AwtMath.getRectangle(
							tileArray.get(0), tileArray.get(1));
					output.append(String.format("\t\t\t%s, %s, %s, %s",
							rectangle.x, rectangle.y + rectangle.height,
							rectangle.width, rectangle.height));
				}
				break;*/
			default:
				output.append(geFormattedTiles(tileArray));
				break;
			}
			output.append("\t);");
			break;
		default:
			output.append(DEFAULT_TEXT);
			break;
		}
		return output.toString();
	}

	private String getPath() {
		switch (this) {
		/*case VINSERT:
			return "\tprivate final Path path = new Path(\n";*/
		case RSBOT:
			return "\tprivate final Tile[] path = new Tile[] {\n";
		default:
			return DEFAULT_TEXT;
		}
	}

	private String getArea(final ArrayList<Point> tileArray) {
		switch (this) {
		/*case VINSERT:
			return "\tprivate final Area area = new Area(\n";*/
		case RSBOT:
			return "\tprivate final Area area = new Area(\n";
		default:
			return DEFAULT_TEXT;
		}
	}

	private String getTile(final Point point) {
		switch (this) {
		/*case VINSERT:
			return String.format("new Tile(%s, %s)", point.x, point.y);*/
		case RSBOT:
			return String.format("new Tile(%s, %s, 0)", point.x, point.y);
		default:
			return DEFAULT_TEXT;
		}
	}
	
	private String geFormattedTiles(final ArrayList<Point> tileArray) {
		StringBuilder output = new StringBuilder(200);
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
}
