package com.dunnkers.pathmaker.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import com.dunnkers.pathmaker.ui.container.ContentPaneModel;
import com.dunnkers.pathmaker.ui.worldmap.WorldMapModel;
import com.dunnkers.pathmaker.util.CodeFormat;
import com.dunnkers.pathmaker.util.WorldMap;

public class CodeFormatMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private final ContentPaneModel contentPaneModel;
	private final WorldMapModel worldMapModel;

	private final CodeFormatActionListener codeFormatActionListener;

	public CodeFormatMenu(final String text,
			final ContentPaneModel contentPaneModel,
			final WorldMapModel worldMapModel) {
		this.setText(text);
		this.contentPaneModel = contentPaneModel;
		this.worldMapModel = worldMapModel;

		this.codeFormatActionListener = new CodeFormatActionListener();
		construct();
	}

	public void construct() {
		removeAll();
		final ButtonGroup buttonGroup = new ButtonGroup();
		for (final CodeFormat codeFormat : CodeFormat.values()) {
			boolean thisCodeFormatSupportsCurrentMap = false;
			for (final WorldMap worldMap : codeFormat.getWorldMaps()) {
				if (worldMap.equals(worldMapModel.getWorldMap())) {
					thisCodeFormatSupportsCurrentMap = true;
					break;
				}
			}
			if (!thisCodeFormatSupportsCurrentMap) {
				continue;
			}
			final JRadioButtonMenuItem item = new JRadioButtonMenuItem(codeFormat
					.getName());
			item.setSelected(contentPaneModel.getCodeFormat().equals(codeFormat));
			item.setEnabled(codeFormat.isEnabled());
			item.setActionCommand(codeFormat.name());
			item.addActionListener(codeFormatActionListener);
			buttonGroup.add(item);
			add(item);
		}
		/* When the new selection of code formats does not include 
		 * the one that's stored
		 */
		if (buttonGroup.getSelection() == null) {
			final Enumeration<AbstractButton> elements = buttonGroup
					.getElements();
			if (elements == null) {
				return;
			}
			while (elements.hasMoreElements()) {
				try {
					final ButtonModel buttonModel = elements.nextElement()
							.getModel();
					if (!buttonModel.isEnabled()) {
						continue;
					}
					buttonGroup.setSelected(buttonModel, true);
					for (final CodeFormat codeFormat : CodeFormat.values()) {
						if (codeFormat.name().equals(
								buttonModel.getActionCommand())) {
							contentPaneModel.setCodeFormat(codeFormat);
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
	}

	public class CodeFormatActionListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			for (final CodeFormat codeFormat : CodeFormat.values()) {
				if (codeFormat.name().equals(e.getActionCommand())) {
					contentPaneModel.setCodeFormat(codeFormat);
				}
			}
			contentPaneModel.getTileArrayChangeListener().stateChanged(null);
		}
	}
}