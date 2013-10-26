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
		switch (this) {
		case PATH:
			for (int i = 0; i < size; i++) {
				final Point current = TileMath.getPoint(worldMapModel.getTileArray()
						.get(i));
				if (i > 0) {
					final Point previous = TileMath.getPoint(worldMapModel.getTileArray().get(i - 1));
					g.setColor(Graphic.setAlpha(Color.BLACK, 150));
					g.drawLine(previous.x, previous.y, current.x, current.y);
				}
				/* 
				 * draws a circle around the last tile to indicate the area 
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
			final int[] xPoints = new int[size];
			final int[] yPoints = new int[size];
			for (int i = 0; i < size; i++) {
				final Point point = TileMath.getPoint(worldMapModel
						.getTileArray().get(i));
				xPoints[i] = point.x;
				yPoints[i] = point.y;
			}
			if (xPoints == null || yPoints == null) {
				break;
			}
			g.setColor(Graphic.setAlpha(Color.BLACK, 250));
			g.drawPolygon(xPoints, yPoints, size);
			g.setColor(Graphic.setAlpha(Color.WHITE, 150));
			g.fillPolygon(xPoints, yPoints, size);
			break;
		}
		for (final Point currentTile : worldMapModel.getTileArray()) {
			if (currentTile == null) {
				continue;
			}
			final Point current = TileMath.getPoint(currentTile);
			g.setColor(Graphic.setAlpha(Color.WHITE, 150));
			g.fillOval(current.x - DOT_RADIUS / 2,
					current.y - DOT_RADIUS / 2, DOT_RADIUS, DOT_RADIUS);
		}
		
		
		final Point lastPoint = TileMath.getPoint(worldMapModel.getTileArray()
				.get(size - 1));
		switch (worldMapModel.getMode()) {
		case PATH:
			g.setColor(Graphic.setAlpha(
					lastPoint.distance(worldMapModel.getMouseLocation()) > worldMapModel
							.getMaxTileRadius() ? Color.RED : Color.GREEN, 150));
			break;
		default:
			g.setColor(Color.BLACK);
			break;
		}
		final Point mouseLocation = worldMapModel.getMouseLocation();
		g.drawLine(lastPoint.x, lastPoint.y,
				mouseLocation.x,
				mouseLocation.y);
		g.setColor(Graphic.setAlpha(Color.WHITE, 150));
		g.fillOval(mouseLocation.x - DOT_RADIUS / 2,
				mouseLocation.y - DOT_RADIUS / 2, DOT_RADIUS, DOT_RADIUS);
	}
}