package com.dunnkers.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * @author Dunnkers
 */
public class Resource {
	
	private final Class<?> resourceClass;
	
	public Resource(Class<?> resourceClass) {
		this.resourceClass = resourceClass;
	}

	private Class<?> getResourceClass() {
		return resourceClass;
	}

	public ImageIcon getIcon(final String path) {
		return new ImageIcon(getImage(path));
	}

	public Image getImage(final String path) {
		try {
			/* when loaded from .jar */
			final Image image = ImageIO.read(getResourceClass().getClassLoader()
					.getResource(path));
			return image;
		} catch (IllegalArgumentException e) {
			/* when loaded from classes */
			try {
				return ImageIO.read(new File(path));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
