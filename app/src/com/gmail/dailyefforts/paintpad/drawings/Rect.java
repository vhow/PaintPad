package com.gmail.dailyefforts.paintpad.drawings;

import com.gmail.dailyefforts.paintpad.tools.Brush;

import android.graphics.Canvas;

/**
 * A rectangle.
 */
public class Rect extends Drawing {
	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(this.startX, this.startY, this.stopX, this.stopY,
				Brush.getPen());
	}
}
