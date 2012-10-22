package org.vhow.paintpad.helper;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * ScreenInfo.java Use this class to get the information of the screen.
 */
public class ScreenInfo {
	private Activity activity;

	private int widthPixels;
	private int heightPixels;

	/**
	 * @param activity
	 *            an instance of PaintPadActivity
	 */
	public ScreenInfo(Activity activity) {
		this.activity = activity;
		getDisplayMetrics();
	}

	private void getDisplayMetrics() {
		DisplayMetrics dm = new DisplayMetrics();

		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		this.widthPixels = dm.widthPixels;
		this.heightPixels = dm.heightPixels;
	}

	/**
	 * @return the number of pixel in the width of the screen.
	 */
	public int getWidthPixels() {
		return widthPixels;
	}

	/**
	 * @return the number of pixel in the height of the screen.
	 */
	public int getHeightPixels() {
		return heightPixels;
	}
}