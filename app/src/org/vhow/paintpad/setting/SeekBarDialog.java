package org.vhow.paintpad.setting;

import org.vhow.paintpad.tools.Brush;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * Use this class to set the width of the brush.
 */
public class SeekBarDialog extends AlertDialog {
	Context context;
	TextView textView = null;
	SeekBar seekBar = null;

	public SeekBarDialog(Context context) {
		this(context, android.R.style.Theme_Panel);
		this.context = context;
	}

	public SeekBarDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.seekBar = new SeekBar(this.context);
		setSeekBar();

		setView(seekBar);

		super.onCreate(savedInstanceState);
	}

	private void setSeekBar() {
		this.seekBar.setMax(20);
		this.seekBar.setProgress((int) Brush.getPen().getStrokeWidth());
		this.seekBar.setOnSeekBarChangeListener(seekBarListener);
	}

	OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			Brush.getPen().setStrokeWidth(progress);
			SeekBarDialog.this.setTitle(Brush.getPen().getStrokeWidth()
					+ " pixel");
		}
	};

	/**
	 * @param max
	 *            the max width of the brush
	 */
	public void setSeekBarMax(int max) {
		this.seekBar.setMax(max);
	}

	public int getSeekBarMax() {
		return this.seekBar.getMax();
	}
}
