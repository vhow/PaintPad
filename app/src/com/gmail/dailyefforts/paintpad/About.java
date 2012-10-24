package com.gmail.dailyefforts.paintpad;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * Display some information about this application.
 */
public class About extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			About.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
