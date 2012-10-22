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
	private Context mContext;
	private TextView mTextView = null;
	private SeekBar mSeekBar = null;

	public SeekBarDialog(Context context) {
		this(context, android.R.style.Theme_Panel);
		this.mContext = context;
	}

	public SeekBarDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.mSeekBar = new SeekBar(this.mContext);
		setSeekBar();

		setView(mSeekBar);

		super.onCreate(savedInstanceState);
	}

	private void setSeekBar() {
		this.mSeekBar.setMax(20);
		this.mSeekBar.setProgress((int) Brush.getPen().getStrokeWidth());
		this.mSeekBar.setOnSeekBarChangeListener(seekBarListener);
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
		this.mSeekBar.setMax(max);
	}

	public int getSeekBarMax() {
		return this.mSeekBar.getMax();
	}
}
