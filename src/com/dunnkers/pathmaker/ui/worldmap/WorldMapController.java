package com.dunnkers.pathmaker.ui.worldmap;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import com.dunnkers.pathmaker.ui.container.ContentPaneModel;
import com.dunnkers.pathmaker.util.CodeFormat;
import com.dunnkers.pathmaker.util.TileMath;
import com.dunnkers.pathmaker.util.TileMode;

/**
 * 
 * @author Dunnkers
 */
public class WorldMapController {

	private final WorldMapModel worldMapModel;
	private final WorldMapView worldMapView;

	private final MouseAdapter mouseAdapter;
	private final MouseMotionAdapter mouseMotionAdapter;

	private final UndoManager undoManager;
	private final ContentPaneModel contentPaneModel;

	public WorldMapController(final WorldMapModel worldMapModel,
			final WorldMapView worldMapView, ContentPaneModel contentPaneModel) {
		this.worldMapModel = worldMapModel;
		this.worldMapModel.setModePropertyChangeListener(new ModePropertyChangeListener());
		this.worldMapView = worldMapView;
		this.contentPaneModel = contentPaneModel;

		mouseAdapter = new MouseAdapter();
		mouseMotionAdapter = new MouseMotionAdapter();

		this.worldMapView.addMouseAdapter(mouseAdapter);
		this.worldMapView.addMouseMotionAdapter(mouseMotionAdapter);

		undoManager = new UndoManager();
		undoManager.setLimit(1000);
	}
	
	public class ModePropertyChangeListener implements PropertyChangeListener {

		@Override
		public void propertyChange(final PropertyChangeEvent e) {
			worldMapView.repaintLabel();
		}
	}

	public class MouseAdapter extends java.awt.event.MouseAdapter {

		private Point initialDragPoint;

		@Override
		public void mousePressed(MouseEvent e) {
			initialDragPoint = e.getPoint();
			if (SwingUtilities.isLeftMouseButton(e)) {
				addTile(e);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			worldMapView.setCursor(Cursor
					.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		public Point getInitialDragPoint() {
			return initialDragPoint;
		}
	}

	public class MouseMotionAdapter extends java.awt.event.MouseMotionAdapter {

		@Override
		public void mouseDragged(final MouseEvent e) {
			mouseMoved(e);
			final int LEFT = 0;
			final int RIGHT = 1;
			final int BUTTON = (SwingUtilities.isLeftMouseButton(e) && !SwingUtilities
					.isRightMouseButton(e)) ? LEFT : (SwingUtilities
					.isRightMouseButton(e) ? RIGHT : -1);
			switch (BUTTON) {
			case LEFT:
				final Point tilePoint = TileMath.getTile(e.getPoint());
				if (worldMapModel.getTileArray().isEmpty()
						|| !worldMapModel.getTileArray().isEmpty()
						&& tilePoint.distance(worldMapModel.getTileArray().get(
								worldMapModel.getTileArray().size() - 1)) >= worldMapModel
								.getDragSensitivity()) {
					addTile(tilePoint);
				}
				break;
			case RIGHT:
				worldMapView.setCursor(Cursor
						.getPredefinedCursor(Cursor.MOVE_CURSOR));
				final JViewport viewport = worldMapView.getViewport();
				final Point viewPosition = viewport.getViewPosition();
				int newX = viewPosition.x
						- (e.getX() - mouseAdapter.getInitialDragPoint().x);
				int newY = viewPosition.y
						- (e.getY() - mouseAdapter.getInitialDragPoint().y);

				int maxX = worldMapView.getLabelWidth() - viewport.getWidth();
				int maxY = worldMapView.getLabelHeight() - viewport.getHeight();
				if (newX < 0) {
					newX = 0;
				}
				if (newX > maxX) {
					newX = maxX;
				}
				if (newY < 0) {
					newY = 0;
				}
				if (newY > maxY) {
					newY = maxY;
				}
				viewport.setViewPosition(new Point(newX, newY));
				break;
			default:
				break;
			}
		}

		@Override
		public void mouseMoved(final MouseEvent e) {
			worldMapModel.setMouseLocation(e.getPoint());
			onMouseMove(e);
			worldMapView.repaintLabel();
		}
	}

	public class UndoableTileEdit extends AbstractUndoableEdit {

		private static final long serialVersionUID = 1L;
		private final Point point;

		public UndoableTileEdit(final Point point) {
			this.point = point;
		}

		@Override
		public String getPresentationName() {
			return String.format("tile (%s, %s) has been added", point.getX(),
					point.getY());
		}

		@Override
		public void undo() {
			super.undo();
			worldMapModel.getTileArray().remove(point);
		}

		@Override
		public void redo() {
			super.redo();
			worldMapModel.getTileArray().add(point);
		}
	}

	public class UndoableClearEdit extends AbstractUndoableEdit {

		private static final long serialVersionUID = 1L;
		private final ArrayList<Point> points;

		@SuppressWarnings("unchecked")
		public UndoableClearEdit(final ArrayList<Point> points) {
			// store a version of the array before it's cleared
			this.points = (ArrayList<Point>) points.clone();
		}

		@Override
		public String getPresentationName() {
			return String.format("all tiles (%s) have been removed",
					points.size());
		}

		@Override
		public void undo() {
			super.undo();
			worldMapModel.getTileArray().addAll(points);
		}

		@Override
		public void redo() {
			super.redo();
			worldMapModel.getTileArray().clear();
		}
	}

	private void addTile(final MouseEvent e) {
		addTile(TileMath.getTile(e.getPoint()));
	}

	private void addTile(final Point tilePoint) {
		if (CodeFormat.VINSERT.equals(contentPaneModel.getCodeFormat())) {
			if (TileMode.AREA.equals(worldMapModel.getMode())
					&& worldMapModel.getTileArray().size() >= 2) {
				return;
			}
		}
		worldMapModel.getTileArray().add(tilePoint);
		final UndoableEditEvent undoableEditEvent = new UndoableEditEvent(worldMapView
				.getLabel(), new UndoableTileEdit(tilePoint));
		undoManager.undoableEditHappened(undoableEditEvent);
	}

	public boolean undo() {
		try {
			undoManager.undo();
		} catch (CannotUndoException e) {
		}
		return undoManager.canUndo();
	}

	public boolean redo() {
		try {
			undoManager.redo();
		} catch (CannotRedoException e) {
		}
		return undoManager.canRedo();
	}

	public boolean clearTiles() {
		if (!(worldMapModel.getTileArray().size() > 0)) {
			return false;
		}
		final UndoableClearEdit undoableClearEdit = new UndoableClearEdit(worldMapModel
				.getTileArray());
		final UndoableEditEvent undoableEditEvent = new UndoableEditEvent(worldMapView
				.getLabel(), undoableClearEdit);
		undoManager.undoableEditHappened(undoableEditEvent);
		worldMapModel.getTileArray().clear();
		return undoManager.canRedo();
	}
	
	public String getCode(final CodeFormat codeFormat) {
		return codeFormat.getCode(worldMapModel.getTileArray(), worldMapModel.getMode());
	}

	public void onMouseMove(MouseEvent e) {
	}
	
	public UndoManager getUndoManager() {
		return undoManager;
	}
}
