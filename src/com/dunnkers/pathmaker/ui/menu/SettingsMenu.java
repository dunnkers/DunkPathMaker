package com.dunnkers.pathmaker.ui.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.UIManager;

import com.dunnkers.pathmaker.Configuration;
import com.dunnkers.pathmaker.ui.WindowModel;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapModel;
import com.dunnkers.pathmaker.util.CodeFormat;
import com.dunnkers.pathmaker.util.TileMode;

public class SettingsMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private final ButtonGroup buttonGroup;
	private final JRadioButtonMenuItem path;
	private final JRadioButtonMenuItem area;
	private final CodeFormatMenu codeFormatMenu;
	private final JMenuItem sensitivity;
	private final JSlider sensitivitySlider;

	private final WindowModel windowModel;
	private final WorldMapModel worldMapModel;
	private final Component parentComponent;

	public SettingsMenu(final String text, final WindowModel windowModelParam,
			final WorldMapModel worldMapModelParam,
			final Component parentComponentParam) {
		this.setText(text);
		this.windowModel = windowModelParam;
		this.worldMapModel = worldMapModelParam;
		this.parentComponent = parentComponentParam;
		this.buttonGroup = new ButtonGroup();
		{
			this.path = new JRadioButtonMenuItem("Path");
			this.path.setSelected(true);
			this.path.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					worldMapModel.setMode(TileMode.PATH, path);
				}
			});
			this.path.setIcon(Configuration.ICON_PATH.getIcon());
		}
		{
			this.area = new JRadioButtonMenuItem("Area");
			this.area.setEnabled(false);
			this.area.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					worldMapModel.setMode(TileMode.AREA, area);
				}
			});
			this.area.setIcon(Configuration.ICON_AREA.getIcon());
		}
		this.buttonGroup.add(path);
		this.buttonGroup.add(area);

		this.codeFormatMenu = new CodeFormatMenu("Code Format");
		this.codeFormatMenu.setIcon(UIManager.getIcon("FileView.fileIcon"));
		this.codeFormatMenu.addItemActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				for (final CodeFormat codeFormat : CodeFormat.values()) {
					if (codeFormat.getName().equals(e.getActionCommand())) {
						windowModel.setCodeFormat(codeFormat);
					}
				}
			}
		});
		{
			this.sensitivity = new JMenuItem("Drag sensitivity");
			this.sensitivity.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					showInputDialog();
				}
			});
			this.sensitivity.setIcon(Configuration.ICON_MOUSE.getIcon());
			this.sensitivitySlider = new JSlider(0, 30);
			this.sensitivitySlider.setMinorTickSpacing(1);
			this.sensitivitySlider.setMajorTickSpacing(5);
			this.sensitivitySlider.setPaintLabels(true);
			this.sensitivitySlider.setPaintTicks(true);
		}
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
		final int returnValue = JOptionPane.showOptionDialog(parentComponent,
				new Object[] {
						"Change the distance between tiles",
						sensitivitySlider }, "Change drag sensitivity",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, null, null);
		if (returnValue == JOptionPane.OK_OPTION) {
			worldMapModel.setDragSensitivity(sensitivitySlider.getValue());
		}
	}
}