package com.dunnkers.pathmaker.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.dunnkers.awt.Graphic;
import com.dunnkers.pathmaker.ui.container.ContentPaneModel;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapModel;

public enum TileMode {
	PATH, AREA;

	private static final int DOT_RADIUS = 4;

	public void paint(final Graphics g, final WorldMapModel worldMapModel,
			final ContentPaneModel contentPaneModel) {
		final int size = worldMapModel.getTileArray().size();
		final Point mouseLocation = worldMapModel.getMouseLocation();
		switch (this) {
		case PATH:
			for (int i = 0; i < size; i++) {
				final Point current = TileMath.getPoint(worldMapModel
						.getTileArray().get(i));
				if (i > 0) {
					final Point previous = TileMath.getPoint(worldMapModel
							.getTileArray().get(i - 1));
					g.setColor(Graphic.setAlpha(Color.BLACK, 150));
					g.drawLine(previous.x, previous.y, current.x, current.y);
				}
				/*
				 * draws a circle around the last tile, to indicate the area
				 * suitable for another tile placement
				 */
				if (i == size - 1) {
					g.setColor(Graphic.setAlpha(Color.BLACK, 50));
					final int radius = (int) TileMath.PIXELS_PER_TILE_HORIZONTAL
							* worldMapModel.getMaxTileRadius();
					g.fillOval(current.x - (radius / 2), current.y
							- (radius / 2), radius, radius);
				}
			}
			break;
		case AREA:
			/*
			 * add one tile additionally to the tiles the user added, to display
			 * a polygon as the mouse moves
			 */
			final int areaSize = mouseLocation == null ? size : size + 1;
			final int[] xPoints = new int[areaSize];
			final int[] yPoints = new int[areaSize];
			for (int i = 0; i < areaSize; i++) {
				final Point point = i == areaSize - 1 && areaSize != size ? mouseLocation
						: TileMath
								.getPoint(worldMapModel.getTileArray().get(i));
				xPoints[i] = point.x;
				yPoints[i] = point.y;
			}
			if (xPoints == null || yPoints == null) {
				break;
			}
			g.setColor(Graphic.setAlpha(Color.BLACK, 250));
			g.drawPolygon(xPoints, yPoints, areaSize);
			g.setColor(Graphic.setAlpha(Color.WHITE, 150));
			g.fillPolygon(xPoints, yPoints, areaSize);
			break;
		}
		for (final Point currentTile : worldMapModel.getTileArray()) {
			if (currentTile == null) {
				continue;
			}
			final Point current = TileMath.getPoint(currentTile);
			g.setColor(Graphic.setAlpha(Color.WHITE, 150));
			g.fillOval(current.x - DOT_RADIUS / 2, current.y - DOT_RADIUS / 2,
					DOT_RADIUS, DOT_RADIUS);
		}
		
		// draw to current mouse location
		if (mouseLocation == null) {
			return;
		}
		final Point lastPoint = TileMath.getPoint(worldMapModel.getTileArray()
				.get(size - 1));
		if (this.equals(PATH)) {
			g.setColor(Graphic.setAlpha(
					lastPoint.distance(worldMapModel.getMouseLocation()) > worldMapModel
							.getMaxTileRadius() ? Color.RED : Color.GREEN, 150));
			g.drawLine(lastPoint.x, lastPoint.y, mouseLocation.x,
					mouseLocation.y);
		}
		g.setColor(Graphic.setAlpha(Color.WHITE, 150));
		g.fillOval(mouseLocation.x - DOT_RADIUS / 2, mouseLocation.y
				- DOT_RADIUS / 2, DOT_RADIUS, DOT_RADIUS);
	}
}