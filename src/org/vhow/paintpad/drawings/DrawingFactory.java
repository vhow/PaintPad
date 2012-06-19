package org.vhow.paintpad.drawings;

/**
 * Factory class, used to generate drawing.
 */
public class DrawingFactory {
	Drawing drawing = null;

	/**
	 * @param id
	 *            The id of the drawing.
	 * @return The Drawing instance with the id.
	 */
	public Drawing createDrawing(int id) {
		switch (id) {
		case DrawingId.DRAWING_PATHLINE:
			drawing = new PathLine();
			break;
		case DrawingId.DRAWING_STRAIGHTLINE:
			drawing = new StraightLine();
			break;
		case DrawingId.DRAWING_RECT:
			drawing = new Rect();
			break;
		case DrawingId.DRAWING_OVAL:
			drawing = new Oval();
			break;
		case DrawingId.DRAWING_CIRCLE:
			drawing = new Circle();
			break;
		case DrawingId.DRAWING_POINTS:
			drawing = new Points();
			break;
		case DrawingId.DRAWING_ERASER:
			drawing = new Eraser();
			break;
		}

		return drawing;
	}
}