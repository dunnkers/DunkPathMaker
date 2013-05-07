package com.dunnkers.util.resource;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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
			final URL url = getResourceClass().getClassLoader()
					.getResource(path);
			/* when loaded from classes */
			if (url == null) {
				return ImageIO.read(new File(path));
			}
			/* when loaded from .jar */
			return ImageIO.read(url);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
