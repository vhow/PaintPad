package com.gmail.dailyefforts.paintpad.drawings;

import com.gmail.dailyefforts.paintpad.tools.Brush;

import android.graphics.Canvas;

/**
 * A straight line.
 */
public class StraightLine extends Drawing {
	@Override
	public void draw(Canvas canvas) {
		assert (canvas != null);
		canvas.drawLine(this.startX, this.startY, this.stopX, this.stopY,
				Brush.getInstance());
	}
}
