package com.dunnkers.awt;

import java.awt.Color;

/**
 * 
 * @author Dunnkers
 */
public class Graphic {

	public static Color setAlpha(final Color color, final int alpha) {
		return new Color(color.getRed(),
				color.getGreen(),
				color.getBlue(),
				alpha);
	}
}
