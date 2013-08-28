package com.dunnkers.pathmaker.ui.menu;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.dunnkers.pathmaker.Configuration;

public class HelpMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private final JLabel aboutTitle;
	private final JLabel aboutVersion;
	private final JLabel aboutNote;
	private final JLabel copyrightNote;

	private final JMenuItem help;
	private final JMenuItem forums;
	private final JMenuItem about;

	public HelpMenu(final String text, final Component parentComponent) {

		this.aboutTitle = new JLabel(Configuration.APPLICATION_TITLE,
				SwingConstants.CENTER);
		final Font aboutTitleFont = new Font(aboutTitle.getFont().getName(),
				aboutTitle.getFont().getStyle(),
				aboutTitle.getFont().getSize() + 2);
		this.aboutTitle.setFont(aboutTitleFont);
		this.aboutVersion = new JLabel("Version "
				+ String.valueOf(Configuration.APPLICATION_VERSION),
				SwingConstants.CENTER);
		final Font aboutVersionFont = new Font(aboutVersion.getFont().getName(),
				aboutVersion.getFont().getStyle(),
				aboutVersion.getFont().getSize() - 2);
		this.aboutVersion.setFont(aboutVersionFont);
		this.aboutNote = new JLabel("This is open-source software built by Dunnkers.");
		this.copyrightNote = new JLabel("(c) Copyright "
				+ Configuration.APPLICATION_TITLE
				+ " contributors and others, 2013. All rights reserved.");

		{
			this.help = new JMenuItem("Online Wiki...");
			this.help.setIcon(Configuration.ICON_GLOBE.getIcon());
			this.help.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(Configuration.LINK_WIKI));
					} catch (URISyntaxException | IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		this.forums = new JMenuItem("Visit Forums...");
		this.forums.setEnabled(false);
		{
			this.about = new JMenuItem("About "
					+ Configuration.APPLICATION_TITLE);
			this.about.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					final Object message = new Object[] {
							aboutTitle,
							aboutVersion,
							Box.createHorizontalGlue(),
							aboutNote,
							copyrightNote };
					JOptionPane.showMessageDialog(parentComponent, message,
							"About " + Configuration.APPLICATION_TITLE,
							JOptionPane.PLAIN_MESSAGE);
				}
			});
			this.about.setIcon(Configuration.ICON_INFO.getIcon());
		}
		this.setText(text);
		init();
	}

	public void init() {
		add(help);
		add(forums);
		addSeparator();
		add(about);
	}
}