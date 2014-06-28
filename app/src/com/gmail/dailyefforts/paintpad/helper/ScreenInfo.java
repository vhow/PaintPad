package com.gmail.dailyefforts.paintpad.helper;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * ScreenInfo.java Use this class to get the information of the screen.
 */
public class ScreenInfo {
	private Activity mActivity;

	private int mWidth;
	private int mHeight;

	/**
	 * @param activity
	 *            an instance of PaintPadActivity
	 */
	public ScreenInfo(Activity activity) {
		this.mActivity = activity;
		getDisplayMetrics();
	}

	private void getDisplayMetrics() {
		final DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		mWidth = dm.widthPixels;
		mHeight = dm.heightPixels;
	}

	/**
	 * @return the number of pixel in the width of the screen.
	 */
	public int getWidthPixels() {
		return mWidth;
	}

	/**
	 * @return the number of pixel in the height of the screen.
	 */
	public int getHeightPixels() {
		return mHeight;
	}
}