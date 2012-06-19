package org.vhow.paintpad.drawings;

import org.vhow.paintpad.tools.Brush;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Some points.
 */
public class Points extends Drawing {
	Paint pen;

	public Points() {
		pen = new Paint(Brush.getPen());
		pen.setStyle(Paint.Style.FILL);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(stopX, stopY, Brush.getPen().getStrokeWidth() + 1,
				pen);
	}

	@Override
	public void fingerDown(float x, float y, Canvas canvas) {
		canvas.drawCircle(x, y, Brush.getPen().getStrokeWidth() + 1, pen);
	}

	@Override
	public void fingerMove(float x, float y, Canvas canvas) {
		canvas.drawCircle(x, y, Brush.getPen().getStrokeWidth() + 1, pen);
	}
}