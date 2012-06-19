package org.vhow.paintpad.drawings;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * An earser, drawing the track line with the color of the bitmap's background
 * color.
 */
public class Eraser extends Drawing {
	Path mPath = null;
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	Paint eraser;

	public Eraser() {
		mPath = new Path();
		eraser = new Paint();
		eraser.setColor(Color.WHITE);
		eraser.setStrokeWidth(5f);
	}

	@Override
	public void draw(Canvas canvas) {
		System.out.println("drawing earser");
		canvas.drawPath(this.mPath, eraser);
	}

	@Override
	public void fingerDown(float x, float y, Canvas canvas) {
		mPath.reset();
		mPath.moveTo(x, y);
		this.mX = x;
		this.mY = y;
	}

	@Override
	public void fingerMove(float x, float y, Canvas canvas) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
		this.draw(canvas);
	}

	@Override
	public void fingerUp(float x, float y, Canvas canvas) {
		mPath.lineTo(mX, mY);
		this.draw(canvas);
		mPath.reset();
	}
}
