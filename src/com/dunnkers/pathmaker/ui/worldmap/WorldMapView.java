package com.dunnkers.pathmaker.ui.worldmap;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.util.TileMath;
import com.dunnkers.pathmaker.util.WorldMap;
import com.dunnkers.util.Graphic;

/**
 * 
 * @author Dunnkers
 */
public class WorldMapView extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private final WorldMapModel worldMapModel;
	private final WorldMapLabel label;

	public WorldMapView(final WorldMapModel worldMapModel) {
		this.worldMapModel = worldMapModel;

		label = new WorldMapLabel("Loading world map...");
		this.setViewportView(label);
		label.setWorldMap(this.worldMapModel.getWorldMap());
	}

	public class WorldMapLabel extends JLabel {

		private static final long serialVersionUID = 1L;
		
		private final String textString;

		public WorldMapLabel(final String text) {
			super(text, SwingConstants.CENTER);
			this.textString = text;
		}

		@Override
		public void paint(final Graphics g) {
			super.paint(g);
			if (!(worldMapModel.getTileArray().size() > 0)) {
				return;
			}
			((Graphics2D) g).setRenderingHint
			  (RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_ON); 
			final int dotRadius = 4;
			switch (worldMapModel.getMode()) {
			case PATH:
				Point previous = null;
				for (int i = 0; i < worldMapModel.getTileArray().size(); i++) {
					Point current = TileMath.getPoint(worldMapModel
							.getTileArray().get(i));
					if (current == null) {
						continue;
					}
					g.setColor(Graphic.setAlpha(Color.WHITE, 150));
					g.fillOval(current.x - dotRadius / 2, current.y - dotRadius
							/ 2, dotRadius, dotRadius);
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
				final int[] xPoints = new int[worldMapModel.getTileArray()
						.size()];
				final int[] yPoints = new int[worldMapModel.getTileArray()
						.size()];
				final int nPoints = worldMapModel.getTileArray().size();
				for (int i = 0; i < worldMapModel.getTileArray().size(); i++) {
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
					g.fillOval(current.x - dotRadius / 2, current.y - dotRadius
							/ 2, dotRadius, dotRadius);
				}
				break;
			}
			final Point lastPoint = TileMath.getPoint(worldMapModel.getTileArray()
					.get(worldMapModel.getTileArray().size()-1));
			switch (worldMapModel.getMode()) {
			case PATH:
				g.setColor(Graphic.setAlpha(
						lastPoint.distance(worldMapModel
								.getMouseLocation()) > worldMapModel
								.getMaxTileRadius() ? Color.RED
								: Color.GREEN, 150));
				break;
			default:
				g.setColor(Color.BLACK);
				break;
			}
			g.drawLine(lastPoint.x, lastPoint.y,
					worldMapModel.getMouseLocation().x,
					worldMapModel.getMouseLocation().y);
		}

		public String getTextString() {
			return textString;
		}
		
		public void setWorldMap(final WorldMap worldMap) {
			worldMapModel.setWorldMap(worldMap);
			new Thread() {
				@Override
				public void run() {
					if (worldMap.getImageIcon() == null) {
						Point viewPosition = getViewport().getViewPosition();
						setText(getTextString());
						worldMap.setImageIcon(worldMap.getResourcePath().getIcon());
						setText(null);

						setIcon(worldMap.getImageIcon());
						if ((viewPosition.x & viewPosition.y) == 0) {
							viewPosition = new Point((int) (TileMath.MAP_IMAGE_PIXELS_HORIZONTAL / 2),
									(int) (TileMath.MAP_IMAGE_PIXELS_VERTICAL / 2));
						}
						getViewport().setViewPosition(viewPosition);
						return;
					}else {
						setIcon(worldMap.getImageIcon());
					}
				}
			}.start();
		}
	}

	public void addMouseAdapter(final MouseAdapter mouseAdapter) {
		label.addMouseListener(mouseAdapter);
	}

	public void addMouseMotionAdapter(
			final MouseMotionAdapter mouseMotionAdapter) {
		label.addMouseMotionListener(mouseMotionAdapter);
	}

	public void repaintLabel() {
		label.repaint();
	}

	public int getLabelWidth() {
		return label.getWidth();
	}

	public int getLabelHeight() {
		return label.getHeight();
	}

	@Override
	public void setCursor(Cursor cursor) {
		label.setCursor(cursor);
	}

	public WorldMapLabel getLabel() {
		return label;
	}
}
