package com.gmail.dailyefforts.paintpad.tools;

import android.graphics.Paint;

/**
 * Use Singleton mode to create Brush class
 */
public class Brush extends Paint {
	/**
	 * Generate the instance when the class is loaded
	 */
	private static final Brush sBrush = new Brush();

	/**
	 * Make the constructor private, to stop others to create instance by the
	 * default constructor
	 */
	private Brush() {
	}

	/**
	 * Provide a static method that can be access by others.
	 * 
	 * @return the single instance
	 */
	public static Brush getInstance() {
		return sBrush;
	}

	/**
	 * reset the brush
	 */
	public void reset() {
		sBrush.setAntiAlias(true);
		sBrush.setDither(true);
		sBrush.setColor(0xFF000000);
		sBrush.setStyle(Paint.Style.STROKE);
		sBrush.setStrokeJoin(Paint.Join.ROUND);
		sBrush.setStrokeCap(Paint.Cap.ROUND);
		sBrush.setStrokeWidth(2);
	}
}