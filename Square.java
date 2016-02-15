package au.edu.unimelb.ai.squatter;

// TODO: Auto-generated Javadoc
/**
 * The Class Square. Each Square object will be eventually associated to one
 * object of type Piece.
 * @author <Andre Peric Tavares> <aperic>
 * @author <Felipe Matheus Costa Silva> <fcosta1>
 */
public class Square {

	int x, y; // Position of square on the board.
	Piece pieceOnTop;

	/**
	 * Instantiates a new square.
	 *
	 * @param x
	 * @param y
	 * @param pieceOnTop
	 */
	public Square(int x, int y, Piece pieceOnTop) {
		this.pieceOnTop = pieceOnTop;
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x
	 *            the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y
	 *            the new y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the piece on top.
	 *
	 * @return the piece on top
	 */
	public Piece getPieceOnTop() {
		return pieceOnTop;
	}

	/**
	 * Sets the piece on top.
	 *
	 * @param pieceOnTop
	 */
	public void setPieceOnTop(Piece pieceOnTop) {
		this.pieceOnTop = pieceOnTop;
	}

}
