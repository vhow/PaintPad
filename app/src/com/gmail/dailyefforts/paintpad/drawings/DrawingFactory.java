package com.gmail.dailyefforts.paintpad.drawings;

/**
 * Factory class, used to generate drawing.
 */
public class DrawingFactory {
	private Drawing mDrawing = null;

	/**
	 * @param id
	 *            The id of the drawing.
	 * @return The Drawing instance with the id.
	 */
	public Drawing createDrawing(int id) {
		switch (id) {
		case DrawingId.DRAWING_PATHLINE:
			mDrawing = new PathLine();
			break;
		case DrawingId.DRAWING_STRAIGHTLINE:
			mDrawing = new StraightLine();
			break;
		case DrawingId.DRAWING_RECT:
			mDrawing = new Rect();
			break;
		case DrawingId.DRAWING_OVAL:
			mDrawing = new Oval();
			break;
		case DrawingId.DRAWING_CIRCLE:
			mDrawing = new Circle();
			break;
		case DrawingId.DRAWING_POINTS:
			mDrawing = new Points();
			break;
		case DrawingId.DRAWING_ERASER:
			mDrawing = new Eraser();
			break;
		}

		return mDrawing;
	}
}