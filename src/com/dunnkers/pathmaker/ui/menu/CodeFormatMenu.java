package com.dunnkers.pathmaker.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import com.dunnkers.pathmaker.ui.container.ContentPaneModel;
import com.dunnkers.pathmaker.util.CodeFormat;

public class CodeFormatMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private final ButtonGroup buttonGroup;
	private final ArrayList<JRadioButtonMenuItem> codeFormats;
	
	private final ContentPaneModel contentPaneModel;

	public CodeFormatMenu(final String text, final ContentPaneModel contentPaneModel) {
		this.setText(text);
		this.contentPaneModel = contentPaneModel;
		this.buttonGroup = new ButtonGroup();
		codeFormats = new ArrayList<JRadioButtonMenuItem>();
		for (final CodeFormat codeFormat : CodeFormat.values()) {
			final JRadioButtonMenuItem item = new JRadioButtonMenuItem(codeFormat
					.getName());
			item.setSelected(false);
			item.setEnabled(codeFormat.isEnabled());
			item.setActionCommand(codeFormat.getName());
			this.buttonGroup.add(item);
			codeFormats.add(item);
		}
		codeFormats.get(codeFormats.size() - 1).setSelected(true);
		init();
	}

	public void init() {
		for (final JRadioButtonMenuItem codeFormat : codeFormats) {
			add(codeFormat);
		}
		this.addItemActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				for (final CodeFormat codeFormat : CodeFormat.values()) {
					if (codeFormat.getName().equals(e.getActionCommand())) {
						contentPaneModel.setCodeFormat(codeFormat);
					}
				}
			}
		});
	}

	public void addItemActionListener(final ActionListener actionListener) {
		for (final JRadioButtonMenuItem codeFormat : codeFormats) {
			codeFormat.addActionListener(actionListener);
		}
	}
}