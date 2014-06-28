package com.gmail.dailyefforts.paintpad.drawings;

import com.gmail.dailyefforts.paintpad.tools.Brush;

import android.graphics.Canvas;
import android.graphics.Path;

/**
 * Track the finger's movement on the screen.
 */
public class PathLine extends Drawing {
	private Path mPath;
	private float mX;
	private float mY;
	private static final float TOUCH_TOLERANCE = 4;

	public PathLine() {
		mPath = new Path();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawPath(mPath, Brush.getInstance());
	}

	@Override
	public void fingerDown(float x, float y, Canvas canvas) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
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
		draw(canvas);
	}

	@Override
	public void fingerUp(float x, float y, Canvas canvas) {
		mPath.lineTo(mX, mY);
		draw(canvas);
		mPath.reset();
	}
}
