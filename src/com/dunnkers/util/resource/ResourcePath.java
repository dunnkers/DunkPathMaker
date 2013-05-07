package com.dunnkers.util.resource;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * @author Dunnkers
 */
public class ResourcePath {
	
	private final Resource resource;
	private final String path;
	
	public ResourcePath(final Resource resource, final String path) {
		this.resource = resource;
		this.path = path;
	}
	
	public ImageIcon getIcon() {
		return resource.getIcon(path);
	}
	
	public Image getImage() {
		return resource.getImage(path);
	}
}
