package com.gmail.dailyefforts.paintpad.setting;

import com.gmail.dailyefforts.paintpad.tools.Brush;

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
	private static final int MAX_WIDTH = 20;
	private Context mContext;
	private SeekBar mSeekBar;

	public SeekBarDialog(Context context) {
		this(context, android.R.style.Theme_Panel);
		mContext = context;
	}

	public SeekBarDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mSeekBar = new SeekBar(mContext);
		setSeekBar();

		setView(mSeekBar);

		super.onCreate(savedInstanceState);
	}

	private void setSeekBar() {
		mSeekBar.setMax(MAX_WIDTH);
		mSeekBar.setProgress((int) Brush.getInstance().getStrokeWidth());
		mSeekBar.setOnSeekBarChangeListener(seekBarListener);
	}

	OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			Brush.getInstance().setStrokeWidth(progress);
			SeekBarDialog.this.setTitle(Brush.getInstance().getStrokeWidth()
					+ " pixel");
		}
	};

	/**
	 * @param max
	 *            the max width of the brush
	 */
	public void setSeekBarMax(int max) {
		mSeekBar.setMax(max);
	}

	public int getSeekBarMax() {
		return mSeekBar.getMax();
	}
}
