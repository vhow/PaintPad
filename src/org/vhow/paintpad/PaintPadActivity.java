package org.vhow.paintpad;

import org.vhow.paintpad.drawings.Drawing;
import org.vhow.paintpad.drawings.DrawingFactory;
import org.vhow.paintpad.drawings.DrawingId;
import org.vhow.paintpad.setting.SettingsActivity;
import org.vhow.paintpad.tools.Brush;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * The main Activity of the application.
 */
public class PaintPadActivity extends Activity implements
		OnSharedPreferenceChangeListener {
	PaintPad mPaintPad;
	Context context = null;
	Drawing drawing = null;
	Paint pen = null;
	DrawingFactory factory = null;
	boolean fullScreen;

	// Define a Dialog id
	private static final int DIALOG_WHAT_TO_DRAW = 1;

	public static final int REQUEST_SETTING = 1;

	// A handle to an instance of SharedPreferences
	SharedPreferences prefs = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mPaintPad = new PaintPad(this);
		this.context = this;
		setContentView(mPaintPad);

		setDefaultDrawing();

		Brush.getPen().reset();

		// Get the handle to the instance of settings
		prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		prefs.registerOnSharedPreferenceChangeListener(this);

		fullScreen = prefs.getBoolean("check_full_screen", false);

		if (fullScreen) {
			makeFullScreen();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Toast.makeText(this,
				getResources().getString(R.string.tip_touch_to_draw),
				Toast.LENGTH_SHORT).show();
	}

	private void makeFullScreen() {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * Set the default drawing
	 */
	private void setDefaultDrawing() {
		factory = new DrawingFactory();
		drawing = factory.createDrawing(DrawingId.DRAWING_PATHLINE);
		mPaintPad.setDrawing(drawing);
	}

	/**
	 * When the up key is pressed, we pop up a dialog for the use to select
	 * shapes.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			showDialog(PaintPadActivity.DIALOG_WHAT_TO_DRAW);
			return true;
		case KeyEvent.KEYCODE_BACK:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			mPaintPad.saveBitmap();
			return true;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			mPaintPad.clearCanvas();
			return true;
		}
		return false;
	}

	/**
	 * @return A dialog contents all the shapes available.
	 */
	private Dialog showWhatToDrawDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				PaintPadActivity.this);

		builder.setTitle(getResources().getString(
				R.string.dialog_title_what_to_draw));

		builder.setSingleChoiceItems(R.array.drawings, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						setWhatToDraw(which);
						dialog.dismiss();
					}
				});

		builder.setNegativeButton(R.string.alert_dialog_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		return alert;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PaintPadActivity.DIALOG_WHAT_TO_DRAW:
			return showWhatToDrawDialog();
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onDestroy() {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("pen_style_key", false);
		editor.commit();
		super.onDestroy();
	}

	/**
	 * In this method, we call the factory method to create an instance of
	 * drawing.
	 * 
	 * @param which
	 *            The drawing's id, identify the current drawing.
	 */
	private void setWhatToDraw(int which) {
		String[] items = getResources().getStringArray(R.array.drawings);

		Toast.makeText(
				PaintPadActivity.this,
				getResources().getString(R.string.tip_current_is_drawing)
						+ items[which], Toast.LENGTH_SHORT).show();

		drawing = factory.createDrawing(which);

		if (drawing != null) {
			mPaintPad.setDrawing(drawing);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.menu_paintpadactivity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_id_what_to_draw:
			showDialog(PaintPadActivity.DIALOG_WHAT_TO_DRAW);
			break;
		case R.id.menu_id_setting:
			startSettingsActivity();
			break;
		case R.id.menu_id_save:
			mPaintPad.saveBitmap();
			break;
		case R.id.menu_id_clear_screen:
			mPaintPad.clearCanvas();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_SETTING:
			System.out.println("result");
			break;
		}
	}

	/**
	 * To start the setting Activity, the provide some functions to setting the
	 * brush.
	 */
	private void startSettingsActivity() {
		Intent intent = new Intent();
		intent.setClass(this.context, SettingsActivity.class);
		startActivityForResult(intent, PaintPadActivity.REQUEST_SETTING);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("pen_style_key")) {
			setPenStyle(sharedPreferences.getBoolean("pen_style_key", false));
		} else if (key.equals("pen_antialias_key")) {
			Brush.getPen().setAntiAlias(
					sharedPreferences.getBoolean(key, false));
		}
	}

	/**
	 * Set the property of the shapes.
	 * 
	 * @param flag
	 *            Fill the shapes or not
	 */
	public void setPenStyle(boolean flag) {
		if (flag) {
			Brush.getPen().setStyle(Style.FILL);
		} else {
			Brush.getPen().setStyle(Style.STROKE);
		}
	}
}