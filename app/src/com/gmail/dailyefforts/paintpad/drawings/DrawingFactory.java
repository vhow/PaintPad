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
	public Drawing createDrawing(Drawings drawing) {
		if (drawing == null) {
			return null;
		}
		switch (drawing) {
		case PATHLINE:
			mDrawing = new PathLine();
			break;
		case STRAIGHTLINE:
			mDrawing = new StraightLine();
			break;
		case RECT:
			mDrawing = new Rect();
			break;
		case OVAL:
			mDrawing = new Oval();
			break;
		case CIRCLE:
			mDrawing = new Circle();
			break;
		case POINTS:
			mDrawing = new Points();
			break;
		case ERASER:
			mDrawing = new Eraser();
			break;
		default:
			break;
		}

		return mDrawing;
	}
}