package com.dunnkers.pathmaker.ui.container;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dunnkers.pathmaker.ui.worldmap.WorldMapController;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapModel;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapView;
import com.dunnkers.pathmaker.util.TileMath;
import com.dunnkers.pathmaker.util.WorldMap;
import com.dunnkers.util.ListenedArrayList;

/**
 * 
 * @author Dunnkers
 */
public class ContentPane extends Container {

	private static final long serialVersionUID = 1L;
	private final MenuBar menuBar;
	private final ToolBar toolBar;
	private final JLabel statusLabel;

	private final WorldMapModel worldMapModel;
	private final WorldMapView worldMapView;
	private final InteractiveWorldMapController worldMapController;

	private final ContentPaneModel contentPaneModel;

	public ContentPane(final ContentPaneModel contentPaneModel) {
		this.contentPaneModel = contentPaneModel;

		worldMapModel = new WorldMapModel();
		this.contentPaneModel.setTileArrayChangeListener(new TileArrayChangeListener());
		worldMapModel.setTileArray(new ListenedArrayList<Point>(200,
				this.contentPaneModel.getTileArrayChangeListener()));
		worldMapView = new WorldMapView(worldMapModel, contentPaneModel);
		worldMapController = new InteractiveWorldMapController(worldMapModel,
				worldMapView);

		{
			// TODO Move the actionlistener to the MapMenu class itself
			menuBar = new MenuBar(contentPaneModel, worldMapModel);
			menuBar.getMapMenu().addMapActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					for (final WorldMap worldMap : WorldMap.values()) {
						if (worldMap.getName().equals(e.getActionCommand())) {
							if (worldMap.equals(worldMapModel.getWorldMap())) {
								return;
							}
							worldMapView.getLabel().setWorldMap(worldMap);
							menuBar.getSettingsMenu().getCodeFormatMenu()
									.construct();
							contentPaneModel.getTileArrayChangeListener().stateChanged(null);
							break;
						}
					}
				}
			});
		}
		{
			statusLabel = new JLabel("Hover over the map to start", JLabel.LEFT);
			final Border paddingBorder = BorderFactory.createEmptyBorder(3, 5,
					3, 5);
			statusLabel.setBorder(BorderFactory.createCompoundBorder(
					statusLabel.getBorder(), paddingBorder));
		}
		toolBar = new ToolBar("Tools", worldMapController, contentPaneModel);
	}

	public void initMenuBar(final JFrame frame) {
		frame.setJMenuBar(menuBar);
	}

	public void initMenuBar(final JApplet applet) {
		applet.setJMenuBar(menuBar);
	}

	public void initContentPane(final Container contentPane) {
		contentPane.add(toolBar, BorderLayout.PAGE_START);
		contentPane.add(statusLabel, BorderLayout.SOUTH);
		contentPane.add(worldMapView, BorderLayout.CENTER);
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
			statusLabel.setText("Current tile: " + tile + " Region: " + (tilePoint.x >> 6) + ", " + (tilePoint.y >> 6));
		}
	}

	public class TileArrayChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			toolBar.getUndo().setEnabled(
					worldMapController.getUndoManager().canUndo());
			toolBar.getRedo().setEnabled(
					worldMapController.getUndoManager().canRedo());
			toolBar.getClear().setEnabled(
					worldMapModel.getTileArray().size() > 0);
			toolBar.getGenerate().setEnabled(
					contentPaneModel.getCodeFormat() != null
							&& contentPaneModel.getCodeFormat().canGenerate(
									worldMapModel.getTileArray(),
									worldMapModel.getMode()));
			worldMapView.repaintLabel();
		}
	}
}
