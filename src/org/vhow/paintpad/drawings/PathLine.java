package org.vhow.paintpad.drawings;

import org.vhow.paintpad.tools.Brush;

import android.graphics.Canvas;
import android.graphics.Path;

/**
 * Track the finger's movement on the screen.
 */
public class PathLine extends Drawing
{
	Path mPath = null;
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;

	public PathLine()
	{
		mPath = new Path();
	}

	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawPath(this.mPath, Brush.getPen());
	}

	@Override
	public void fingerDown(float x, float y, Canvas canvas)
	{
		mPath.reset();
		mPath.moveTo(x, y);
		this.mX = x;
		this.mY = y;
	}

	@Override
	public void fingerMove(float x, float y, Canvas canvas)
	{
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);

		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
		{
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
		this.draw(canvas);
	}

	@Override
	public void fingerUp(float x, float y, Canvas canvas)
	{
		mPath.lineTo(mX, mY);
		this.draw(canvas);
		mPath.reset();
	}
}
