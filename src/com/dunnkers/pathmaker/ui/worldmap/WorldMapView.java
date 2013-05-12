package com.dunnkers.pathmaker.ui.worldmap;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.dunnkers.pathmaker.ui.container.ContentPaneModel;
import com.dunnkers.pathmaker.util.TileMath;
import com.dunnkers.pathmaker.util.WorldMap;

/**
 * 
 * @author Dunnkers
 */
public class WorldMapView extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private final WorldMapModel worldMapModel;
	private final ContentPaneModel contentPaneModel;

	private final WorldMapLabel label;

	public WorldMapView(final WorldMapModel worldMapModel,
			final ContentPaneModel contentPaneModel) {
		this.worldMapModel = worldMapModel;
		this.contentPaneModel = contentPaneModel;

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
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			worldMapModel.getMode().paint(g, worldMapModel, contentPaneModel);
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
						worldMap.setImageIcon(worldMap.getResourcePath()
								.getIcon());
						setText(null);

						setIcon(worldMap.getImageIcon());
						if ((viewPosition.x & viewPosition.y) == 0) {
							viewPosition = new Point((int) (TileMath.MAP_IMAGE_PIXELS_HORIZONTAL / 2),
									(int) (TileMath.MAP_IMAGE_PIXELS_VERTICAL / 2));
						}
						getViewport().setViewPosition(viewPosition);
						return;
					} else {
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
