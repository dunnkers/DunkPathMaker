package com.dunnkers.pathmaker.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

import com.dunnkers.pathmaker.ui.worldmap.WorldMapController;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapModel;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapView;
import com.dunnkers.pathmaker.util.TileMath;

/**
 * 
 * @author Dunnkers
 */
public class Window extends Container {

	private static final long serialVersionUID = 1L;
	private final ButtonBar buttonBar;
	private final ToolBar toolBar;
	private final JLabel statusLabel;

	private final WorldMapModel worldMapModel;
	private final WorldMapView worldMapView;
	private final InteractiveWorldMapController worldMapController;

	public Window(final WindowModel windowModel) {
		worldMapModel = new WorldMapModel();
		worldMapView = new WorldMapView(worldMapModel);
		worldMapController = new InteractiveWorldMapController(worldMapModel,
				worldMapView);

		buttonBar = new ButtonBar(windowModel, worldMapModel, this);
		{
			statusLabel = new JLabel("Hover over the map to start", JLabel.LEFT);
			final Border paddingBorder = BorderFactory.createEmptyBorder(3, 5,
					3, 5);
			statusLabel.setBorder(BorderFactory.createCompoundBorder(
					statusLabel.getBorder(), paddingBorder));
		}

		// TODO use mvc here.
		toolBar = new ToolBar("Tools", worldMapController, windowModel, this);
	}
	
	public void initContentPane(final Container contentPane) {
		contentPane.add(toolBar, BorderLayout.PAGE_START);
		contentPane.add(statusLabel, BorderLayout.SOUTH);
		contentPane.add(worldMapView, BorderLayout.CENTER);
	}
	
	public void initJMenuBar(final JFrame frame) {
		frame.setJMenuBar(buttonBar);
	}
	
	public void initJMenuBar(final JApplet applet) {
		applet.setJMenuBar(buttonBar);
	}

	public class InteractiveWorldMapController extends WorldMapController {

		public InteractiveWorldMapController(WorldMapModel worldMapModel,
				WorldMapView worldMapView) {
			super(worldMapModel, worldMapView);
		}

		@Override
		public void onMouseMove(final MouseEvent e) {
			super.onMouseMove(e);
			final Point tilePoint = TileMath.getTile(e.getPoint());
			final String tile = String.format("(%s, %s)", tilePoint.x,
					tilePoint.y);
			statusLabel.setText("Current tile: " + tile);
		}

		@Override
		public void setClear(boolean enabled) {
			super.setClear(enabled);
			toolBar.getClear().setEnabled(enabled);
		}

		@Override
		public void setRedo(boolean enabled) {
			super.setRedo(enabled);
			toolBar.getRedo().setEnabled(enabled);
		}

		@Override
		public void setUndo(boolean enabled) {
			super.setUndo(enabled);
			toolBar.getUndo().setEnabled(enabled);
		}

		@Override
		public void setGenerate(boolean enabled) {
			super.setGenerate(enabled);
			toolBar.getGenerate().setEnabled(enabled);
		}
	}
}
