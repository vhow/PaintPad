package org.vhow.paintpad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.vhow.paintpad.drawings.Drawing;
import org.vhow.paintpad.drawings.Eraser;
import org.vhow.paintpad.helper.ScreenInfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * This is our main View class.
 */
public class PaintPad extends View {
	float tempX, tempY;
	private Bitmap bitmap = null;
	private Canvas paper = null;
	boolean isMoving = false;
	Drawing drawing = null;
	int bgcolor;

	Context context;

	/**
	 * Set the shape that is drawing.
	 * 
	 * @param drawing
	 *            Which shape to drawing current.
	 */
	public void setDrawing(Drawing drawing) {
		this.drawing = drawing;
	}

	public PaintPad(Context context) {
		super(context);
		this.context = context;
		// Get the information about the screen.
		ScreenInfo screenInfo = new ScreenInfo((Activity) context);

		/**
		 * Create a bitmap with the size of the screen.
		 */
		bitmap = Bitmap.createBitmap(screenInfo.getWidthPixels(),
				screenInfo.getHeightPixels(), Bitmap.Config.ARGB_8888);

		paper = new Canvas(this.bitmap);

		// Set the background color
		paper.drawColor(getResources().getColor(R.color.color_default_bg));

		this.isMoving = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Draw the bitmap
		canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.DITHER_FLAG));

		// Call the drawing's draw() method.
		if (drawing != null && this.isMoving == true) {
			drawing.draw(canvas);
		}

		if (!(drawing instanceof Eraser)) {
			// Drawing a brush icon in this view.
			Bitmap pen = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.pen);
			canvas.drawBitmap(pen, this.tempX, this.tempY - pen.getHeight(),
					new Paint(Paint.DITHER_FLAG));
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			fingerDown(x, y);
			reDraw();
			break;
		case MotionEvent.ACTION_MOVE:
			fingerMove(x, y);
			reDraw();
			break;
		case MotionEvent.ACTION_UP:
			fingerUp(x, y);
			reDraw();
			break;
		}

		return true;
	}

	/**
	 * Refresh the view, the view's onDraw() method will be called.
	 */
	private void reDraw() {
		invalidate();
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
		this.tempX = 0;
		this.tempY = 0;

		drawing.fingerUp(x, y, paper);
		this.isMoving = false;
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
		this.tempX = x;
		this.tempY = y;
		this.isMoving = true;

		drawing.fingerMove(x, y, paper);
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
		this.isMoving = false;
		drawing.fingerDown(x, y, paper);
	}

	/**
	 * Check the sdcard is available or not.
	 */
	public void saveBitmap() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			saveToSdcard();
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Toast.makeText(this.context,
					getResources().getString(R.string.tip_sdcard_is_read_only),
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(
					this.context,
					getResources().getString(
							R.string.tip_sdcard_is_not_available),
					Toast.LENGTH_LONG).show();
		}
	}

	public void changeBgColor(int color) {
		this.paper.drawColor(color);
		this.reDraw();
	}

	/**
	 * Clear the drawing in the canvas.
	 */
	public void clearCanvas() {
		this.paper.drawColor(getResources().getColor(R.color.color_default_bg));
		this.reDraw();
	}

	/**
	 * Save the bitmap to sdcard.
	 */
	private void saveToSdcard() {
		File sdcard_path = Environment.getExternalStorageDirectory();
		String myFloder = getResources().getString(
				R.string.folder_name_in_sdcard);
		File paintpad = new File(sdcard_path + "/" + myFloder + "/");
		try {
			if (!paintpad.exists()) {
				paintpad.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String timeStamp = (DateFormat.format("yyyy.MM.dd.hh.mm.ss",
				new java.util.Date())).toString();
		String suffixName = ".png";

		String fullPath = "";
		fullPath = sdcard_path + "/" + myFloder + "/" + timeStamp + suffixName;
		try {
			Toast.makeText(this.context,
					getResources().getString(R.string.tip_save_to) + fullPath,
					Toast.LENGTH_LONG).show();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100,
					new FileOutputStream(fullPath));
		} catch (FileNotFoundException e) {
			Toast.makeText(
					this.context,
					getResources().getString(R.string.tip_sava_failed)
							+ fullPath, Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
}
