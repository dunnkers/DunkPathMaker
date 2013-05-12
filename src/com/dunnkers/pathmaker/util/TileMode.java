package com.dunnkers.pathmaker.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.dunnkers.awt.AwtMath;
import com.dunnkers.awt.Graphic;
import com.dunnkers.pathmaker.ui.container.ContentPaneModel;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapModel;

public enum TileMode {
	PATH, AREA;

	public void paint(final Graphics g, final WorldMapModel worldMapModel,
			final ContentPaneModel contentPaneModel) {
		final int dotRadius = 4;
		boolean drawLineToMouse = true;
		switch (this) {
		case PATH:
			Point previous = null;
			for (int i = 0; i < worldMapModel.getTileArray().size(); i++) {
				Point current = TileMath.getPoint(worldMapModel.getTileArray()
						.get(i));
				if (current == null) {
					continue;
				}
				g.setColor(Graphic.setAlpha(Color.WHITE, 150));
				g.fillOval(current.x - dotRadius / 2,
						current.y - dotRadius / 2, dotRadius, dotRadius);
				if (previous != null) {
					g.setColor(Graphic.setAlpha(Color.BLACK, 150));
					g.drawLine(previous.x, previous.y, current.x, current.y);
				}
				boolean isLast = i == worldMapModel.getTileArray().size() - 1;
				if (isLast) {
					g.setColor(Graphic.setAlpha(Color.BLACK, 50));
					int radius = (int) TileMath.PIXELS_PER_TILE_HORIZONTAL
							* worldMapModel.getMaxTileRadius();
					g.fillOval(current.x - (radius / 2), current.y
							- (radius / 2), radius, radius);
				}
				previous = current;
			}
			break;
		case AREA:
			final int nPoints = worldMapModel.getTileArray().size();
			if (CodeFormat.VINSERT.equals(contentPaneModel.getCodeFormat())
					&& nPoints >= 1) {
				drawLineToMouse = false;
				final Point one = TileMath.getPoint(worldMapModel
						.getTileArray().get(0));
				final Point two = nPoints >= 2 ? TileMath
						.getPoint(worldMapModel.getTileArray().get(1))
						: worldMapModel.getMouseLocation();
				final Rectangle r = AwtMath.getRectangle(one, two);
				g.setColor(Graphic.setAlpha(Color.BLACK, 250));
				g.drawRect(r.x, r.y, r.width, r.height);
				g.setColor(Graphic.setAlpha(Color.WHITE, 150));
				g.fillRect(r.x, r.y, r.width, r.height);
				break;
			}
			final int[] xPoints = new int[worldMapModel.getTileArray().size()];
			final int[] yPoints = new int[worldMapModel.getTileArray().size()];
			for (int i = 0; i < nPoints; i++) {
				final Point point = TileMath.getPoint(worldMapModel
						.getTileArray().get(i));
				xPoints[i] = point.x;
				yPoints[i] = point.y;
			}
			if (xPoints == null || yPoints == null) {
				break;
			}
			g.setColor(Graphic.setAlpha(Color.BLACK, 250));
			g.drawPolygon(xPoints, yPoints, nPoints);
			g.setColor(Graphic.setAlpha(Color.WHITE, 150));
			g.fillPolygon(xPoints, yPoints, nPoints);
			for (Point current : worldMapModel.getTileArray()) {
				if (current == null) {
					continue;
				}
				g.setColor(Graphic.setAlpha(Color.WHITE, 150));
				g.fillOval(current.x - dotRadius / 2,
						current.y - dotRadius / 2, dotRadius, dotRadius);
			}
			break;
		}
		final Point lastPoint = TileMath.getPoint(worldMapModel.getTileArray()
				.get(worldMapModel.getTileArray().size() - 1));
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
		if (drawLineToMouse) {
			g.drawLine(lastPoint.x, lastPoint.y,
					worldMapModel.getMouseLocation().x,
					worldMapModel.getMouseLocation().y);
		}
	}
}