package org.vhow.paintpad.drawings;

import org.vhow.paintpad.tools.Brush;

import android.graphics.Canvas;

/**
 * A circle.
 */
public class Circle extends Drawing {
	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(this.startX + (this.stopX - this.startX) / 2,
				this.startY + (this.stopY - this.startY) / 2,
				Math.abs(this.startX - this.stopX) / 2, Brush.getPen());
	}
}
