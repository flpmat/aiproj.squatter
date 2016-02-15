package au.edu.unimelb.ai.squatter;

// TODO: Auto-generated Javadoc
/**
 * The Piece class has two constant values to represent the color. Each piece
 * has an "isEnclosed" value that represents the piece's current state.
 * 
 * @author <Andre Peric Tavares> <aperic>
 * @author <Felipe Matheus Costa Silva> <fcosta1>
 *
 */
public class Piece {

	enum Colour {
		WHITE, BLACK
	};

	Colour colour;
	boolean isEnclosed;

	/**
	 * Instantiates a new piece.
	 *
	 * @param colour
	 * @param isEnclosed
	 */
	public Piece(Colour colour, boolean isEnclosed) {
		this.colour = colour;
		this.isEnclosed = isEnclosed;
	}

	/**
	 * Gets the colour.
	 *
	 * @return the colour
	 */
	public Colour getColour() {
		return colour;
	}

	/**
	 * Sets the colour.
	 *
	 * @param colour
	 *            the new colour
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}

	/**
	 * Checks if is enclosed.
	 *
	 * @return true, if is enclosed
	 */
	public boolean isEnclosed() {
		return isEnclosed;
	}

	/**
	 * Sets the enclosed.
	 *
	 * @param isEnclosed
	 */
	public void setEnclosed(boolean isEnclosed) {
		this.isEnclosed = isEnclosed;
	}

}
