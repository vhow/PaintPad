package com.gmail.dailyefforts.paintpad.drawings;

import com.gmail.dailyefforts.paintpad.tools.Brush;

import android.graphics.Canvas;
import android.graphics.RectF;

public class Oval extends Drawing {
	private RectF mRectF = null;

	public Oval() {
		mRectF = new RectF();
	}

	@Override
	public void draw(Canvas canvas) {
		mRectF.left = this.startX;
		mRectF.right = this.stopX;
		mRectF.top = this.startY;
		mRectF.bottom = this.stopY;

		canvas.drawOval(mRectF, Brush.getPen());
	}
}
