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
		try {
			final Image image = ImageIO.read(getResourceClass().getClassLoader()
					.getResource(path));
			System.out.println("loadin icon! fom liek dat..");
			return new ImageIcon(image);
		} catch (IllegalArgumentException e) {
			System.out.println("loadin icon! fom IllegalArgumentException");
            return new ImageIcon(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Image getImage(final String path) {
		try {
			final Image image = ImageIO.read(getResourceClass().getClassLoader()
					.getResource(path));
			System.out.println("loadin image! fom liek dat..");
			return image;
		} catch (IllegalArgumentException e) {
			System.out.println("loadin image! fom IllegalArgumentException");
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
	
	/*public static ImageIcon icon(String path) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(ImageIO.read(DunkPathMaker.class.getClassLoader().getResource(path)));
        } catch (IllegalArgumentException e) {
        } catch (IOException e) {
        }

        if (icon == null) {
        	System.out.println("doin dis shid manuluy ofc.");
            icon = new ImageIcon(path);
        }

        return icon;
    }*/
}
