package com.gmail.dailyefforts.paintpad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gmail.dailyefforts.paintpad.drawings.Drawing;
import com.gmail.dailyefforts.paintpad.drawings.Eraser;
import com.gmail.dailyefforts.paintpad.helper.ScreenInfo;

/**
 * This is the main View class.
 */
public class PaintPad extends View {
	private static final String TIME_FORMAT = "yyyy-MM-dd_HH.mm.ss";
	private static final String SUFFIX_NAME = ".png";
	private float mX;
	private float mY;

	private Bitmap mBitmap;
	private Canvas mCanvas;

	private boolean mIsMoving;

	private Drawing mDrawing;

	private Context mContext;
	private Bitmap mPen;

	/**
	 * Set the shape that is drawing.
	 * 
	 * @param drawing
	 *            Which shape to drawing current.
	 */
	public void setDrawing(Drawing drawing) {
		mDrawing = drawing;
	}

	public PaintPad(Context context) {
		super(context);
		mContext = context;

		// Get the information about the screen.
		final ScreenInfo screenInfo = new ScreenInfo((Activity) context);

		/**
		 * Create a bitmap with the size of the screen.
		 */
		mBitmap = Bitmap.createBitmap(screenInfo.getWidthPixels(),
				screenInfo.getHeightPixels(), Bitmap.Config.ARGB_8888);

		mCanvas = new Canvas(mBitmap);

		// Set the background color
		mCanvas.drawColor(getResources().getColor(R.color.color_default_bg));

		mIsMoving = false;

		mPen = BitmapFactory.decodeResource(getResources(), R.drawable.pen);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawBitmap(mBitmap, 0, 0, null);

		// Call the drawing's draw() method.
		if (mDrawing != null && mIsMoving == true) {
			mDrawing.draw(canvas);
		}

		if (!(mDrawing instanceof Eraser)) {
			canvas.drawBitmap(mPen, mX, mY - mPen.getHeight(), null);
		}
	}
	
	@Override
	public boolean performClick() {
		return super.performClick();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final float x = event.getX();
		final float y = event.getY();

		final int action = event.getAction() & MotionEvent.ACTION_MASK;

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			fingerDown(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			fingerMove(x, y);
			break;
		case MotionEvent.ACTION_UP:
			fingerUp(x, y);
			break;
		default:
			break;
		}
		invalidate();

		return true;
	}

	/**
	 * Handles the action of finger up.
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 */
	private void fingerUp(float x, float y) {
		mX = 0;
		mY = 0;
		mDrawing.fingerUp(x, y, mCanvas);
		mIsMoving = false;
	}

	/**
	 * Handles the action of finger Move.
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 */
	private void fingerMove(float x, float y) {
		mX = x;
		mY = y;
		mIsMoving = true;
		mDrawing.fingerMove(x, y, mCanvas);
	}

	/**
	 * Handles the action of finger down.
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 */
	private void fingerDown(float x, float y) {
		mIsMoving = false;
		mDrawing.fingerDown(x, y, mCanvas);
	}

	/**
	 * Check the sdcard is available or not.
	 */
	public void saveBitmap() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			saveToSdcard();
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Toast.makeText(mContext,
					getResources().getString(R.string.tip_sdcard_is_read_only),
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(
					mContext,
					getResources().getString(
							R.string.tip_sdcard_is_not_available),
					Toast.LENGTH_SHORT).show();
		}
	}

	public void changeBgColor(int color) {
		mCanvas.drawColor(color);
		invalidate();
	}

	/**
	 * Clear the drawing in the canvas.
	 */
	public void clearCanvas() {
		mCanvas.drawColor(getResources().getColor(R.color.color_default_bg));
		invalidate();
	}

	/**
	 * Save the bitmap to sdcard.
	 */
	private void saveToSdcard() {
		final File sdcard_path = Environment.getExternalStorageDirectory();
		String myFloder = getResources().getString(
				R.string.folder_name_in_sdcard);
		File paintpad = new File(sdcard_path + "/" + myFloder + "/");
		// TODO use public pic dir
		try {
			if (!paintpad.exists()) {
				paintpad.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		final SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT,
				Locale.getDefault());
		final Date date = Calendar.getInstance().getTime();

		// Get formatted time stamp
		String timeStamp = format.format(date);

		String fullPath = sdcard_path + File.separator + myFloder
				+ File.separator + timeStamp + SUFFIX_NAME;
		try {
			Toast.makeText(mContext,
					getResources().getString(R.string.tip_save_to) + fullPath,
					Toast.LENGTH_LONG).show();
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100,
					new FileOutputStream(fullPath));
		} catch (FileNotFoundException e) {
			Toast.makeText(
					mContext,
					getResources().getString(R.string.tip_sava_failed)
							+ fullPath, Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
}
