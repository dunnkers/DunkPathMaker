package com.dunnkers.awt;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * 
 * @author Dunnkers
 */
public class AwtMath {

	public static Rectangle getRectangle(final Point pointOne,
			final Point pointTwo) {
		final int diffX = pointOne.x - pointTwo.x;
		final int diffY = pointOne.y - pointTwo.y;
		final int x = diffX < 0 ? pointOne.x : pointTwo.x;
		final int y = diffY < 0 ? pointOne.y : pointTwo.y;
		final int width = Math.abs(diffX);
		final int height = Math.abs(diffY);
		return new Rectangle(x, y, width, height);
	}
}
