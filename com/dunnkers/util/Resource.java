package com.dunnkers.util;

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

	public ImageIcon getIcon(final String name) {
		try {
			return new ImageIcon(ImageIO.read(getResourceClass().getClassLoader()
					.getResource(name)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
