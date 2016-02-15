package aiproj.squatter.andre;
/*
 * COMP30024_2015_SM1: Artificial Intelligence
 * @author <Andre Peric Tavares> <aperic> <706525>
 * @author <Felipe Matheus Costa Silva> <fcosta1> <706279>
 * Date: 25/05/15
 */

import java.util.ArrayList;

import aiproj.squatter.*;

/**
 * This class implements a variation of the FloodFill algorithm. It is used to
 * detect captures.
 */
public class FloodFill implements Piece {

	/**
	 * Returns null or the list of captured pieces.
	 */
	ArrayList<Square> floodfill(Square square_origin, Board board,
			Square.Colour colourOfMove, Square.Colour oponentColour) {

		ArrayList<Square> queue = new ArrayList<Square>();
		ArrayList<Square> visited = new ArrayList<Square>();

		boolean[] visited_board = new boolean[(board.dimension + 2)
				* (board.dimension + 1) + 1];

		if (square_origin.colour != Square.Colour.EMPTY
				&& square_origin.colour != oponentColour)
			return null;

		queue.add(square_origin);
		visited.add(square_origin);
		visited_board[square_origin.position] = true;

		while (!queue.isEmpty()) {
			Square current_square = queue.remove(0);
			if (!addToQueueIfSquareIsNotWallNorOwnPiece(current_square.North(),
					queue, visited_board, visited, colourOfMove))
				return null;
			if (!addToQueueIfSquareIsNotWallNorOwnPiece(current_square.South(),
					queue, visited_board, visited, colourOfMove))
				return null;
			if (!addToQueueIfSquareIsNotWallNorOwnPiece(current_square.East(),
					queue, visited_board, visited, colourOfMove))
				return null;
			if (!addToQueueIfSquareIsNotWallNorOwnPiece(current_square.West(),
					queue, visited_board, visited, colourOfMove))
				return null;
		}

		// the enclosed territory must contain at least one cell that was free
		// or occupied by the other player
		for (Square s : visited) {
			if (s.colour == Square.Colour.EMPTY || s.colour == oponentColour)
				return visited;
		}
		return null;
	}

	/**
	 * If the value returned is false, then a square on the border of the board
	 * was reached, which means that the region is not enclosed.
	 * 
	 * If returned value is true, then the analysed square was successfully
	 * added to the queue or ignored (in that case that it has the same colour
	 * of the player).
	 */
	boolean addToQueueIfSquareIsNotWallNorOwnPiece(Square s,
			ArrayList<Square> queue, boolean[] visited_board,
			ArrayList<Square> visited, Square.Colour colourOfMove) {
		if (visited_board[s.position])
			return true;

		if (s.colour == Square.Colour.INVALID) {
			return false;
		}

		if (s.colour == colourOfMove) {
			return true;
		}

		visited.add(s);
		visited_board[s.position] = true;
		queue.add(s);
		return true;
	}

}
