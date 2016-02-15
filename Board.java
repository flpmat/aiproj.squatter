package au.edu.unimelb.ai.squatter;

// TODO: Auto-generated Javadoc
/**
 * The Board class has an array of squares in which the pieces are placed.
 * @author <Andre Peric Tavares> <aperic>
 * @author <Felipe Matheus Costa Silva> <fcosta1>
 *
 */
public class Board {
	
	int dimension;
    Square[][] squares; 
	
	/**
	 * Instantiates a new board.
	 *
	 * @param dimension
	 */
	public Board(int dimension) {
		this.dimension = dimension;
		this.squares = new Square[dimension][dimension];
	}
	
	/**
	 * Gets the dimension.
	 *
	 * @return the dimension
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * Sets the dimension.
	 *
	 * @param dimension the new dimension
	 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	/**
	 * Gets the board.
	 *
	 * @return the board
	 */
	public Square[][] getBoard() {
		return squares;
	}
	
	/**
	 * Sets the board.
	 *
	 * @param squares the new board
	 */
	public void setBoard(Square[][] squares) {
		this.squares = squares;
	}
	
	/** Verify whether provided coordinates represents
	 * a valid position for the given board.
	 * @param x
	 * @param y
	 * @return True when it represents a valid position, false otherwise.
	 */
	public boolean isInsideBoard(int x, int y){
		if (x <= dimension - 1 && y <= dimension - 1 && x >= 0 && y >= 0) return true;
		return false;
	}


	
	
}
