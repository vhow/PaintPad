package org.vhow.paintpad.drawings;

import android.graphics.Canvas;

/**
 * Abstract shape. All the shapes in this application extends this class.
 */
public abstract class Drawing
{
	public float startX;
	public float startY;
	public float stopX;
	public float stopY;

	public void reset()
	{
		this.startX = 0;
		this.startY = 0;
		this.stopX = 0;
		this.stopY = 0;
	}

	/**
	 * A abstract method, that all the shapes must implement.
	 * 
	 * @param canvas
	 *            A canvas to draw on.
	 */
	public abstract void draw(Canvas canvas);

	public void fingerDown(float x, float y, Canvas canvas)
	{
		this.reset();
		this.startX = x;
		this.startY = y;
	}

	public void fingerMove(float x, float y, Canvas canvas)
	{
		this.stopX = x;
		this.stopY = y;
	}

	public void fingerUp(float x, float y, Canvas canvas)
	{
		this.stopX = x;
		this.stopY = y;

		this.draw(canvas);
		this.reset();
	}
}
