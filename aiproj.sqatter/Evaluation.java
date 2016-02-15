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
 * The Evaluation class implements method to attribute a score to a state of the
 * game, indicating how good it is.
 */
public class Evaluation {

	Board board;
	Square.Colour colour;
	Square.Colour opponentColour;

	int weightDifferenceOfPunctuation;
	int weightDistanceToBorder;
	int weightDistanceToBlackPieces;
	int weightPenalPieces;
	int weightDiagonalConnections;

	Evaluation(Square.Colour colour, Board board) {
		if (colour == Square.Colour.BLACK) {
			opponentColour = Square.Colour.WHITE;
		} else {
			opponentColour = Square.Colour.BLACK;
		}

		this.board = board;
		this.colour = colour;

		this.weightDifferenceOfPunctuation = 1500;
		this.weightDistanceToBorder = 3;
		this.weightDistanceToBlackPieces = 4;
		this.weightPenalPieces = -2;
		this.weightDiagonalConnections = 3;
	}

	/**
	 * Return total score of the state of the game according to the weights of
	 * each strategy.
	 */
	int totalEvaluation() {
		return this.weightDistanceToBorder * (this.distanceToBorder() / 10)
				+ weightDifferenceOfPunctuation * differenceOfPunctuation()
				+ this.weightDistanceToBlackPieces
				* this.distanceToOpponentPieces()
				+ this.weightPenalPieces * penalPieces();
	}

	int differenceOfPunctuation() {
		return board.Punctuation - board.opponentPunctuation;
	}

	/**
	 * Returns an integer inversely proportional to the distance of the player
	 * pieces to the borders of the board.
	 * 
	 * That is, the greater this number is, the closer the pieces (of the player
	 * that called the evaluation) are to the borders.
	 */
	int distanceToBorder() {
		int totalDistance = 0;

		for (Square s : board.squares) {
			if (s.colour == this.opponentColour)
				continue;
			int[] posXY = board
					.convertUnidimensionalPositionToXYPosition(s.position);
			int x = posXY[0];
			int y = posXY[1];

			if (y > board.dimension / 2) {
				totalDistance += (y);
			} else {
				totalDistance += (board.dimension - y);
			}

			if (x > board.dimension / 2) {
				totalDistance += (x);
			} else {
				totalDistance += (board.dimension - x);
			}
		}
		return totalDistance;
	}

	/**
	 * The greater the number returned is, the closer the pieces are to the
	 * opponent pieces.
	 */
	int distanceToOpponentPieces() {
		int totalPoints = 0;
		for (Square s : board.squares) {
			if (s.colour == this.colour) {
				if (s.North().colour == this.opponentColour)
					totalPoints++;
				if (s.South().colour == this.opponentColour)
					totalPoints++;
				if (s.West().colour == this.opponentColour)
					totalPoints++;
				if (s.East().colour == this.opponentColour)
					totalPoints++;
			}
		}
		return totalPoints;

	}

	/**
	 * Returns a penalty associated with regions that have too many (more than
	 * 4) pieces of the colour of the player running the evaluation.
	 */
	int penalPieces() {
		int totalPoints = 0;
		for (Square s : board.squares) {
			if (s.colour == this.colour) {
				int totalNeighbours = 0;

				if (s.North().colour == this.colour)
					totalNeighbours++;
				if (s.South().colour == this.colour)
					totalNeighbours++;
				if (s.West().colour == this.colour)
					totalNeighbours++;
				if (s.East().colour == this.colour)
					totalNeighbours++;
				if (s.NorthEast().colour == this.colour)
					totalNeighbours++;
				if (s.NorthWest().colour == this.colour)
					totalNeighbours++;
				if (s.SouthEast().colour == this.colour)
					totalNeighbours++;
				if (s.SouthWest().colour == this.colour)
					totalNeighbours++;

				if (totalNeighbours > 4) {
					totalPoints++;
				}
			}
		}
		return totalPoints;
	}

	int diagonalConnections() {
		int totalConnections = 0;
		for (Square s : board.squares) {
			if (s.colour == this.colour) {
				if (s.NorthEast().colour == this.colour)
					totalConnections++;
				if (s.NorthWest().colour == this.colour)
					totalConnections++;
				if (s.SouthEast().colour == this.colour)
					totalConnections++;
				if (s.SouthWest().colour == this.colour)
					totalConnections++;
			}
		}
		return totalConnections;
	}

}
