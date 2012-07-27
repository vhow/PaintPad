package org.vhow.paintpad.drawings;

import org.vhow.paintpad.tools.Brush;

import android.graphics.Canvas;
import android.graphics.RectF;

public class Oval extends Drawing {
	RectF rectF = null;

	public Oval() {
		rectF = new RectF();
	}

	@Override
	public void draw(Canvas canvas) {
		rectF.left = this.startX;
		rectF.right = this.stopX;
		rectF.top = this.startY;
		rectF.bottom = this.stopY;

		canvas.drawOval(rectF, Brush.getPen());
	}
}
