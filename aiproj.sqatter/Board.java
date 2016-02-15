package aiproj.squatter.andre;
/*
 * COMP30024_2015_SM1: Artificial Intelligence
 * @author <Andre Peric Tavares> <aperic> <706525>
 * @author <Felipe Matheus Costa Silva> <fcosta1> <706279>
 * Date: 25/05/15
 */

import aiproj.squatter.*;

/**
 * Board holds the state of the game. The representation of the board is
 * unidimensional (check README for details).
 */
public class Board {

	int dimension;
	int numberOfOcuppiedSquares;
	int Punctuation;
	int opponentPunctuation;
	int turn;
	int maxPosition;
	int minPosition;

	Square[] squares;

	public Board(int dimension) {
		this.turn = 1;
		this.dimension = dimension;
		squares = new Square[((dimension + 2) * (dimension + 1)) + 1];
		this.numberOfOcuppiedSquares = 0;
		this.Punctuation = 0;
		this.opponentPunctuation = 0;

		maxPosition = convertXYPositionToUnidimensional(dimension - 1,
				dimension - 1);
		minPosition = convertXYPositionToUnidimensional(0, 0);

		short pos = 0;
		while (pos <= dimension) {
			squares[pos] = new Square(Square.Colour.INVALID, pos, this);
			pos++;
		}
		for (int i = 0; i < dimension; i++) {
			squares[pos] = new Square(Square.Colour.INVALID, pos, this);
			pos += 1;
			for (int j = 0; j < dimension; j++) {
				squares[pos + j] = new Square(Square.Colour.EMPTY,
						(short) (pos + j), this);
			}
			pos += dimension;
		}
		while (pos < squares.length) {
			squares[pos] = new Square(Square.Colour.INVALID, pos, this);
			pos++;
		}

	}

	public void printBoard() {
		int j = 1;
		for (int i = 0; i < squares.length; i++) {
			if (squares[i].colour != Square.Colour.INVALID) {
				if (j / (dimension) == 1) {
					System.out.println(squares[i]);
					j = 0;
				} else {
					System.out.print(squares[i] + " ");
				}
				j++;
			}
		}
		System.out.println();
	}

	public int[] convertUnidimensionalPositionToXYPosition(int unidimen_pos) {
		int row = (unidimen_pos / (dimension + 1)) - 1;
		int column = (unidimen_pos % (dimension + 1)) - 1;
		int[] coordinates = new int[2];
		coordinates[0] = row;
		coordinates[1] = column;
		return coordinates;
	}

	public int convertXYPositionToUnidimensional(int row, int column) {
		return (row + 1) * (dimension + 1) + 1 + column;
	}

}
