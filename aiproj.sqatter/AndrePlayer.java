package aiproj.squatter.andre;
/*
 * COMP30024_2015_SM1: Artificial Intelligence
 * @author <Andre Peric Tavares> <aperic> <706525>
 * @author <Felipe Matheus Costa Silva> <fcosta1> <706279>
 * Date: 25/05/15
 */

import java.io.PrintStream;
import java.util.Random;

import aiproj.squatter.*;

/*
 * Implements Player.
 * We assume that the reader is familiar with the interface
 * described in the project specification.
 */
public class AndrePlayer implements Player, Piece {

	Board board;
	Square.Colour colour;
	Square.Colour oponentColour;
	int interfaceColour;
	Evaluation eval;
	FloodFill flood;
	AlphaBetaPruning alphabetap;

	public AndrePlayer() {
	}

	@Override
	public int init(int n, int p) {
		this.interfaceColour = p;

		Square.Colour col;
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

		this.initOponentColour();

		this.alphabetap = new AlphaBetaPruning(this.eval, this.board);

		return 0;
	}

	@Override
	public Move makeMove() {
		int positionOfBestMove;

		if (board.turn < 10) {
			positionOfBestMove = alphabetap.alphabeta_function(1, true,
					Integer.MIN_VALUE, Integer.MAX_VALUE)[1];
		} else {
			positionOfBestMove = alphabetap.alphabeta_function(5, true,
					Integer.MIN_VALUE, Integer.MAX_VALUE)[1];
		}

		int[] arr = board
				.convertUnidimensionalPositionToXYPosition(positionOfBestMove);
		int row = arr[0];
		int column = arr[1];

		Move move = new Move();
		move.Row = row;
		move.Col = column;
		move.P = interfaceColour;
		board.turn++;

		new Action(false, this.colour, this.oponentColour,
				(short) positionOfBestMove, this.flood, this.board);
		return move;
	}

	@Override
	public int opponentMove(Move m) {
		int position = board.convertXYPositionToUnidimensional(m.Row, m.Col);
		if (position < board.minPosition || position > board.maxPosition
				|| board.squares[position].colour != Square.Colour.EMPTY) {
			return -1;
		} else {
			new Action(true, this.oponentColour, this.colour, (short) position,
					this.flood, this.board);
			return 0;
		}
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

	void initOponentColour() {
		if (this.colour == Square.Colour.BLACK) {
			this.oponentColour = Square.Colour.WHITE;
		} else {
			this.oponentColour = Square.Colour.BLACK;
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

}
