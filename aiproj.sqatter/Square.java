package aiproj.squatter.andre;
/*
 * COMP30024_2015_SM1: Artificial Intelligence
 * @author <Andre Peric Tavares> <aperic> <706525>
 * @author <Felipe Matheus Costa Silva> <fcosta1> <706279>
 * Date: 25/05/15
 */

import aiproj.squatter.Piece;

/**
 * A square represents a position on the board. It can be either white, black,
 * invalid, empty or dead (that is, captured).
 */
public class Square implements Piece {
	Colour colour;
	short position;
	Board board;

	/**
	 * We opt for using enum instead of the provided int Pieces. It is probably
	 * safer and more semantically coherent.
	 */
	public static enum Colour {
		WHITE, BLACK, INVALID, EMPTY, DEAD
	};

	public Square(Colour colour, short position, Board board) {
		this.colour = colour;
		this.position = position;
		this.board = board;
	}

	public String toString() {
		String piece = "";
		switch (this.colour) {
		case BLACK:
			piece = "B";
			break;
		case WHITE:
			piece = "W";
			break;
		case INVALID:
			piece = "i";
			break;
		case DEAD:
			piece = "-";
			break;
		case EMPTY:
			piece = "+";
			break;

		default:
			break;
		}
		return piece;
	}

	public Square[] getNorthSouthEastWestNeighbours() {
		Square[] neighbours = new Square[4];

		neighbours[0] = North();
		neighbours[1] = South();
		neighbours[2] = East();
		neighbours[3] = West();

		return neighbours;
	}

	public Square[] getNeighbours() {
		Square[] neighbours = new Square[8];

		neighbours[0] = North();
		neighbours[1] = South();
		neighbours[2] = East();
		neighbours[3] = West();
		neighbours[4] = NorthEast();
		neighbours[5] = NorthWest();
		neighbours[6] = SouthEast();
		neighbours[7] = SouthWest();

		return neighbours;
	}

	public Square North() {
		return board.squares[position - (board.dimension + 1)];
	}

	public Square South() {
		return board.squares[position + (board.dimension + 1)];
	}

	public Square East() {
		return board.squares[position + 1];
	}

	public Square West() {
		return board.squares[position - 1];
	}

	public Square NorthEast() {
		return board.squares[position + 2];
	}

	public Square NorthWest() {
		return board.squares[position - 2];
	}

	public Square SouthEast() {
		return board.squares[position + 2];
	}

	public Square SouthWest() {
		return board.squares[position - 2];
	}

}
