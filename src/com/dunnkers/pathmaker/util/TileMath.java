package com.dunnkers.pathmaker.util;

import java.awt.Point;

/**
 * RuneScape Tile math.
 * 
 * @author Dunnkers
 */
public class TileMath {

	/** The most western horizontal Tile. */
	private static double MAP_TILES_HORIZONTAL_WEST = 2047.5;
	/** The most eastern horizontal Tile. */
	private static double MAP_TILES_HORIZONTAL_EAST = 3904;
	/** The most northern vertical tile. */
	private static double MAP_TILES_VERTICAL_NORTH = 4159;
	/** The most southern vertical tile. */
	private static double MAP_TILES_VERTICAL_SOUTH = 2494.5;
	/** The amount of horizontal Tiles. */
	private static double MAP_TILES_HORIZONTAL = MAP_TILES_HORIZONTAL_EAST
			- MAP_TILES_HORIZONTAL_WEST;
	/** The amount of vertical Tiles. */
	private static double MAP_TILES_VERTICAL = MAP_TILES_VERTICAL_SOUTH
			- MAP_TILES_VERTICAL_NORTH;

	/** The amount of horizontal pixels in the world map image. */
	public static double MAP_IMAGE_PIXELS_HORIZONTAL = 3713;
	/** The amount of vertical pixels in the world map image. */
	public static double MAP_IMAGE_PIXELS_VERTICAL = 3329;

	/** The amount of horizontal world map pixels for one tile. */
	public static double PIXELS_PER_TILE_HORIZONTAL = MAP_IMAGE_PIXELS_HORIZONTAL
			/ MAP_TILES_HORIZONTAL;
	/** The amount of vertical world map pixels for one tile. */
	public static double PIXELS_PER_TILE_VERTICAL = MAP_IMAGE_PIXELS_VERTICAL
			/ MAP_TILES_VERTICAL;

	/**
	 * Converts a Point in the coordinate system to a RuneScape Tile.
	 * 
	 * @param point
	 *            A point.
	 * @return A Tile as used in RuneScape, in a Point object.
	 * @since 04-04-2012
	 * @author Dunnkers
	 */
	public static Point getTile(final Point point) {
		final int x = (int) Math.round(MAP_TILES_HORIZONTAL_WEST + point.x
				/ PIXELS_PER_TILE_HORIZONTAL);
		final int y = (int) Math.round(MAP_TILES_VERTICAL_NORTH + point.y
				/ PIXELS_PER_TILE_VERTICAL);
		return new Point(x, y);
	}

	/**
	 * Converts a RuneScape Tile to a point in the coordinate system.
	 * 
	 * @param tile
	 *            A tile.
	 * @return A point usable in the given coordinate system.
	 * @since 04-04-2012
	 * @author Dunnkers
	 */
	public static Point getPoint(final Point tile) {
		final int x = (int) ((tile.x - MAP_TILES_HORIZONTAL_WEST) * PIXELS_PER_TILE_HORIZONTAL);
		final int y = (int) ((tile.y - MAP_TILES_VERTICAL_NORTH) * PIXELS_PER_TILE_VERTICAL);
		return new Point(x, y);
	}

	/**
	 * Get the distance between two points.
	 * @param current The first point.
	 * @param destination The second point.
	 * @return The distance between the two points in a double.
	 */
	public static double getDistanceBetween(final Point current, final Point destination) {
		return Math
				.sqrt(((current.x - destination.x) * (current.x - destination.x))
						+ ((current.y - destination.y) * (current.y - destination.y)));
	}
}
