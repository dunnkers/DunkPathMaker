package com.dunnkers.pathmaker.ui;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.dunnkers.pathmaker.ui.worldmap.WorldMapController;
import com.dunnkers.pathmaker.util.CodeFormat;

public abstract class ToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final WorldMapController worldMapController;

	private final JButton undo;
	private final JButton redo;
	private final JButton clear;
	private final JTextArea generateTextArea;
	private final JScrollPane generateScrollPane;
	private final JButton generate;

	public ToolBar(final String name,
			final WorldMapController worldMapControllr,
			final Component parentComponent) {
		this.worldMapController = worldMapControllr;
		{
			this.undo = new JButton("Undo");
			this.undo.setEnabled(false);
			this.undo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					worldMapController.undo();
				}
			});
		}
		{
			this.redo = new JButton("Redo");
			this.redo.setEnabled(false);
			this.redo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					worldMapController.redo();
				}
			});
		}
		{
			this.clear = new JButton("Clear");
			this.getClear().setEnabled(false);
			this.getClear().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					worldMapController.clearTiles();
				}
			});
		}
		{
			this.generateTextArea = new JTextArea("No code generated", 20, 30);
			this.generateScrollPane = new JScrollPane(generateTextArea);

			this.generate = new JButton("Generate",
					UIManager.getIcon("FileView.fileIcon"));
			this.generate.setEnabled(false);
			this.generate.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					final String code = worldMapController
							.getCode(getCodeFormat());
					
					final StringSelection selection = new StringSelection(code);
					final Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					clipboard.setContents(selection, selection);
					
					generateTextArea.setText(code);
					final Object message = new Object[] {
							"Code",
							generateScrollPane,
							"Copied to clipboard!" };
					JOptionPane.showMessageDialog(parentComponent, message,
							"Generated Code", JOptionPane.PLAIN_MESSAGE);
				}
			});
		}
		this.setName(name);
		this.setFloatable(false);
		init();
	}

	public void init() {
		add(undo);
		add(redo);
		add(getClear());
		add(Box.createHorizontalGlue());
		add(generate);
	}

	public abstract CodeFormat getCodeFormat();

	public JButton getClear() {
		return clear;
	}

	public JButton getUndo() {
		return undo;
	}

	public JButton getRedo() {
		return redo;
	}

	public JButton getGenerate() {
		return generate;
	}
}