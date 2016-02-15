package aiproj.squatter.andre;
/*
 * COMP30024_2015_SM1: Artificial Intelligence
 * @author <Andre Peric Tavares> <aperic> <706525>
 * @author <Felipe Matheus Costa Silva> <fcosta1> <706279>
 * Date: 25/05/15
 */

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import aiproj.squatter.*;

/**
 * Random player used for testing.
 * 
 * Code is messy!
 *
 */
public class RandomPlayer implements Player, Piece {

	Board board;
	Square.Colour colour;
	Square.Colour oponentColour;
	int interfaceColour;
	Evaluation eval;
	FloodFill flood;
	Random rand;
	int max;
	int min;
	ArrayList<Action> a_actions;

	public RandomPlayer() {
	}

	@Override
	public int init(int n, int p) {
		Square.Colour col;
		this.interfaceColour = p;
		this.flood = new FloodFill();

		if (p == Piece.BLACK) {
			col = Square.Colour.BLACK;
		} else {
			col = Square.Colour.WHITE;
		}
		if (setPieceColour(col) < 0)
			return -1;

		this.board = new Board(n);
		this.eval = new Evaluation(colour, this.board);
		this.rand = new Random();

		this.initOponentColour();

		max = board.convertXYPositionToUnidimensional(board.dimension - 1,
				board.dimension - 1);
		min = board.convertXYPositionToUnidimensional(0, 0);
		a_actions = new ArrayList<Action>();
		return 0;
	}

	@Override
	public Move makeMove() {
		int randomNum;

		// generates numbers while it is not a position that has an empty square
		while (true) {
			randomNum = rand.nextInt((max - min) + 1) + min;
			// if(board.squares[randomNum].colour != Square.Colour.BLACK) break;
			if (board.squares[randomNum].colour == Square.Colour.EMPTY)
				break;
		}

		int[] arr = board.convertUnidimensionalPositionToXYPosition(randomNum);
		int row = arr[0];
		int column = arr[1];

		Move move = new Move();
		move.Row = row;
		move.Col = column;
		move.P = interfaceColour;

		a_actions.add(0, new Action(false, this.colour, this.oponentColour,
				(short) randomNum, this.flood, this.board));
		return move;
	}

	@Override
	public int opponentMove(Move m) {
		int position = board.convertXYPositionToUnidimensional(m.Row, m.Col);
		a_actions.add(0, new Action(true, this.oponentColour, this.colour,
				(short) position, this.flood, this.board));
		// System.out.println("reading of board after oponent move:");
		// printBoard(System.out);
		return 0;
	}

	@Override
	public int getWinner() {
		if (board.numberOfOcuppiedSquares < board.dimension * board.dimension) {
			return Piece.EMPTY;
		} else {
			if (board.Punctuation == board.opponentPunctuation) {
				return convertSquareColourToInterfaceFormat(Square.Colour.DEAD);
			}
			if (board.Punctuation > board.opponentPunctuation) {
				return convertSquareColourToInterfaceFormat(colour);
			} else {
				return convertSquareColourToInterfaceFormat(oponentColour);
			}
		}
	}

	@Override
	public void printBoard(PrintStream output) {
		board.printBoard();
	}

	int setPieceColour(Square.Colour col) {
		switch (col) {
		case BLACK:
			this.colour = Square.Colour.BLACK;
			return 0;
		case WHITE:
			this.colour = Square.Colour.WHITE;
			return 0;
		default:
			return -1;
		}
	}

	int convertSquareColourToInterfaceFormat(Square.Colour col) {
		switch (col) {
		case BLACK:
			return Piece.BLACK;
		case WHITE:
			return Piece.WHITE;
		case DEAD:
			return Piece.DEAD;
		case EMPTY:
			return Piece.EMPTY;
		case INVALID:
			return Piece.INVALID;
		default:
			return Piece.INVALID;
		}
	}

	Square.Colour convertColourToSquareFormat(int col) {
		switch (col) {
		case Piece.BLACK:
			return Square.Colour.BLACK;
		case Piece.WHITE:
			return Square.Colour.WHITE;
		default:
			return Square.Colour.INVALID;
		}
	}

	void initOponentColour() {
		if (this.colour == Square.Colour.BLACK) {
			this.oponentColour = Square.Colour.WHITE;
		} else {
			this.oponentColour = Square.Colour.BLACK;
		}
	}

}
