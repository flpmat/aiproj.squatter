package aiproj.squatter.andre;
/*
 * COMP30024_2015_SM1: Artificial Intelligence
 * @author <Andre Peric Tavares> <aperic> <706525>
 * @author <Felipe Matheus Costa Silva> <fcosta1> <706279>
 * Date: 25/05/15
 */

import java.util.ArrayList;

import aiproj.squatter.*;

/*
 * Encapsulates an move on the game,
 * modifying the board and making it
 * possible to undo the action.
 */
public class Action {
	boolean oponentMove;
	Square.Colour colourOfMove;
	Square.Colour colourNotMoved;
	short position;

	FloodFill flood;
	Board board;

	int numberOfOponentPointsBeforeAction;
	int numberOfPointsBeforeAction;
	int numberOfOccupiedSquaresBeforeAction;
	ArrayList<Square> capturedSquares;

	/**
	 * Action represents movings by both players. When Action is an play by the
	 * opponent player, the parameter PlayerMovingIsPlayerThatCalledAction
	 * should be set as false.
	 */
	Action(boolean oponentMove, Square.Colour colourOfMove,
			Square.Colour colourNotMoved, short position, FloodFill flood,
			Board board) {
		this.oponentMove = oponentMove;
		this.colourOfMove = colourOfMove;
		this.colourNotMoved = colourNotMoved;
		this.position = position;

		this.flood = flood;
		this.board = board;

		this.numberOfOponentPointsBeforeAction = board.opponentPunctuation;
		this.numberOfPointsBeforeAction = board.Punctuation;
		this.numberOfOccupiedSquaresBeforeAction = board.numberOfOcuppiedSquares;

		applyAction();

	}

	/*
	 * Executes the move that the action represents, updating the board and
	 * punctuation.
	 */
	void applyAction() {
		applyMoveToBoardAndReplaceCapturedPieces();
		updateNumberOfOccupiedSquares();
		updatePunctuation();
	}

	/*
	 * Reverts Action.
	 */
	void undoApplyAction() {
		undoAppyMoveToBoardAndVerifyCapturedPieces();
		undoUpdateNumberOfOccupiedSquares();
		undoUpdatePunctuation();
	}

	/*
	 * Modify number of occupied squares on the board according to the list of
	 * captures
	 */
	void updateNumberOfOccupiedSquares() {
		board.numberOfOcuppiedSquares += 1;
		for (Square s : capturedSquares) {
			// we count empty squares, but not opponent squares
			if (s.colour == Square.Colour.EMPTY) {
				board.numberOfOcuppiedSquares += 1;
			}
		}
	}

	/*
	 * Modify number of occupied squares on the board according to the list of
	 * captures and considering who has moved the piece.
	 */
	void updatePunctuation() {
		// handles the tricky case in which the move
		// encloses a region that has squares that were
		// already enclosed.
		for (Square s : capturedSquares) {
			if (s.colour == Square.Colour.DEAD) {
				if (!oponentMove) {
					board.opponentPunctuation -= 1;
				} else {
					board.Punctuation -= 1;
				}
			}
		}

		// for debugging purposes
		// System.out.println("size of capture:" + capturedSquares.size());
		// System.out.println("list of captured pieces:");
		// for (Square s : capturedSquares){
		// System.out.println(s + " ");
		// }

		if (!oponentMove) {
			board.Punctuation += capturedSquares.size();
		} else {
			board.opponentPunctuation += capturedSquares.size();
		}
	}

	/*
	 * Reverts punctuation.
	 */
	void undoUpdatePunctuation() {
		board.Punctuation = this.numberOfPointsBeforeAction;
		board.opponentPunctuation = numberOfOponentPointsBeforeAction;
	}

	/*
	 * Make move, replacing the empty square by the new one, and replace
	 * captured pieces by DEAD squares.
	 */
	void applyMoveToBoardAndReplaceCapturedPieces() {
		board.squares[position].colour = colourOfMove;
		this.capturedSquares = getListOfCaptures();
		for (Square s : capturedSquares) {
			// System.out.println("replacing piece in" +
			// board.convertUnidimensionalPositionToXYPosition(s.position)[0] +
			// board.convertUnidimensionalPositionToXYPosition(s.position)[1] );
			short pos = s.position;
			board.squares[pos] = new Square(Square.Colour.DEAD, pos, board);
		}
	}

	void undoAppyMoveToBoardAndVerifyCapturedPieces() {
		board.squares[position].colour = Square.Colour.EMPTY;
		for (Square s : capturedSquares) {
			board.squares[s.position] = s;
		}
	}

	void undoUpdateNumberOfOccupiedSquares() {
		board.numberOfOcuppiedSquares = this.numberOfOccupiedSquaresBeforeAction;
	}

	/*
	 * Run flood fill starting on the position of the North, South, Left and
	 * West neighbors, returning the list of captured pieces if there was any
	 * capture. Otherwise, return an empty list.
	 */
	ArrayList<Square> getListOfCaptures() {
		boolean there_is_some_same_colour_neighbor = false;
		Square[] all_neighbours = board.squares[position].getNeighbours();
		for (int i = 0; i < 8; i++) {
			if (all_neighbours[i].colour == colourOfMove)
				there_is_some_same_colour_neighbor = true;
		}
		if (!there_is_some_same_colour_neighbor)
			return new ArrayList<Square>();
		Square[] neighbours = board.squares[position]
				.getNorthSouthEastWestNeighbours();
		for (int i = 0; i < 4; i++) { // TODO: remove magic number
			ArrayList<Square> captured = flood.floodfill(neighbours[i], board,
					colourOfMove, colourNotMoved);
			if (captured != null)
				return captured;
		}
		return new ArrayList<Square>();
	}

}
