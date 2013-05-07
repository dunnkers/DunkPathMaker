package com.dunnkers.pathmaker.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.DunkPathMaker;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapController;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapModel;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapView;
import com.dunnkers.pathmaker.util.CodeFormat;
import com.dunnkers.pathmaker.util.TileMath;
import com.dunnkers.pathmaker.util.TileMode;
import com.dunnkers.util.Resource;

/**
 * 
 * @author Dunnkers
 */
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private final ButtonBar buttonBar;
	private final ToolBar toolBar;
	private final JLabel statusLabel;

	private final WorldMapModel worldMapModel;
	private final WorldMapView worldMapView;
	private final InteractiveWorldMapController worldMapController;

	private CodeFormat codeFormat = CodeFormat.VINSERT;

	public Window() {
		buttonBar = new ButtonBar();
		{
			statusLabel = new JLabel("Hover over the map to start", JLabel.LEFT);
			final Border paddingBorder = BorderFactory.createEmptyBorder(3, 5,
					3, 5);
			statusLabel.setBorder(BorderFactory.createCompoundBorder(
					statusLabel.getBorder(), paddingBorder));
		}

		worldMapModel = new WorldMapModel();
		worldMapView = new WorldMapView(worldMapModel);
		worldMapController = new InteractiveWorldMapController(worldMapModel,
				worldMapView);

		toolBar = new ToolBar("Tools", worldMapController, getWindowComponent()) {
			private static final long serialVersionUID = 1L;

			@Override
			public CodeFormat getCodeFormat() {
				return codeFormat;
			}
		};

		this.setTitle(Configuration.WINDOW_TITLE);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add(Configuration.RESOURCE.getImage("resources/icon-map-16x16.png"));
		//icons.add(Resource.icon("resources/icon-map-32x32.png").getImage());
		/*icons.add(Configuration.RESOURCE.getIcon("resources/icon-map-48x48.png").getImage());*/
		this.setIconImages(icons);
		init();
	}

	public void init() {
		final Container contentPane = this.getContentPane();
		this.setJMenuBar(buttonBar);
		contentPane.add(toolBar, BorderLayout.PAGE_START);
		contentPane.add(statusLabel, BorderLayout.SOUTH);
		contentPane.add(worldMapView, BorderLayout.CENTER);
		this.pack();
		this.setSize(Configuration.WINDOW_SIZE);
		this.setLocationRelativeTo(this.getOwner());
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

	public class ButtonBar extends JMenuBar {

		private static final long serialVersionUID = 1L;
		private final SettingsMenu settingsMenu;
		private final MapMenu mapMenu;
		private final HelpMenu help;

		public ButtonBar() {
			this.settingsMenu = new SettingsMenu("Settings");
			this.mapMenu = new MapMenu("Map");
			this.help = new HelpMenu("Help", getWindowComponent());
			init();
		}

		public void init() {
			add(settingsMenu);
			add(mapMenu);
			add(help);
		}

		public SettingsMenu getSettingsMenu() {
			return settingsMenu;
		}
	}

	public class SettingsMenu extends JMenu {

		private static final long serialVersionUID = 1L;
		private final ButtonGroup buttonGroup;
		private final JRadioButtonMenuItem path;
		private final JRadioButtonMenuItem area;
		private final CodeFormatMenu codeFormatMenu;
		private final JMenuItem sensitivity;
		private final JSlider sensitivitySlider;

		public SettingsMenu(final String text) {
			this.buttonGroup = new ButtonGroup();
			{
				this.path = new JRadioButtonMenuItem("Path");
				this.path.setSelected(true);
				this.path.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						worldMapModel.setMode(TileMode.PATH);
						worldMapView.repaintLabel();
					}
				});
			}
			{
				this.area = new JRadioButtonMenuItem("Area");
				this.area.setEnabled(false);
				this.area.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						worldMapModel.setMode(TileMode.AREA);
						worldMapView.repaintLabel();
					}
				});
			}
			this.buttonGroup.add(path);
			this.buttonGroup.add(area);

			this.codeFormatMenu = new CodeFormatMenu("Code Format");

			{
				this.sensitivity = new JMenuItem("Drag sensitivity");
				this.sensitivity.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						showInputDialog();
					}
				});
				this.sensitivitySlider = new JSlider(0, 30);
				this.sensitivitySlider.setMinorTickSpacing(1);
				this.sensitivitySlider.setMajorTickSpacing(5);
				this.sensitivitySlider.setPaintLabels(true);
				this.sensitivitySlider.setPaintTicks(true);
			}
			this.setText(text);
			init();
		}

		public void init() {
			add(path);
			add(area);
			addSeparator();
			add(codeFormatMenu);
			addSeparator();
			add(sensitivity);
		}

		private void showInputDialog() {
			sensitivitySlider.setValue(worldMapModel.getDragSensitivity());
			final int returnValue = JOptionPane.showOptionDialog(
					getWindowComponent(), new Object[] {
							"Change the distance between tiles",
							sensitivitySlider }, "Change drag sensitivity",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, null, null);
			if (returnValue == JOptionPane.OK_OPTION) {
				worldMapModel.setDragSensitivity(sensitivitySlider.getValue());
			}
		}
	}

	public class CodeFormatMenu extends JMenu {

		private static final long serialVersionUID = 1L;
		private final ButtonGroup buttonGroup;
		private final ArrayList<JRadioButtonMenuItem> codeFormats;

		public CodeFormatMenu(final String text) {
			this.buttonGroup = new ButtonGroup();
			codeFormats = new ArrayList<JRadioButtonMenuItem>();
			for (final CodeFormat codeFormat : CodeFormat.values()) {
				final JRadioButtonMenuItem item = new JRadioButtonMenuItem(codeFormat
						.getName());
				item.setSelected(false);
				item.setEnabled(codeFormat.isEnabled());
				item.setActionCommand(codeFormat.getName());
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						for (final CodeFormat codeFormat : CodeFormat.values()) {
							if (codeFormat.getName().equals(
									e.getActionCommand())) {
								setCodeFormat(codeFormat);
							}
						}
					}
				});
				this.buttonGroup.add(item);
				codeFormats.add(item);
			}
			codeFormats.get(codeFormats.size() - 1).setSelected(true);
			this.setText(text);
			init();
		}

		public void init() {
			for (final JRadioButtonMenuItem codeFormat : codeFormats) {
				add(codeFormat);
			}
		}
	}

	/**
	 * Used in the 'Drag Sensitivity' menu to call the parent component in a
	 * OptionPane.
	 * 
	 * @return This component, a Window.
	 */
	public Component getWindowComponent() {
		return this;
	}

	public void setCodeFormat(final CodeFormat codeFormat) {
		this.codeFormat = codeFormat;
	}
}