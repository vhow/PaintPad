package org.vhow.paintpad.drawings;

import org.vhow.paintpad.tools.Brush;

import android.graphics.Canvas;

/**
 * A straight line.
 */
public class StraightLine extends Drawing
{
	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawLine(this.startX, this.startY, this.stopX, this.stopY,
				Brush.getPen());
	}
}
