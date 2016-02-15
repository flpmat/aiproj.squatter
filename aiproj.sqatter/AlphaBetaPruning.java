package aiproj.squatter.andre;
/*
 * COMP30024_2015_SM1: Artificial Intelligence
 * @author <Andre Peric Tavares> <aperic> <706525>
 * @author <Felipe Matheus Costa Silva> <fcosta1> <706279>
 * Date: 25/05/15
 */

import java.util.ArrayList;

/*
 * Implements the Alpha Beta Pruning algorithm.
 */
public class AlphaBetaPruning {

	Evaluation eval;
	Board board;
	Square.Colour colour;
	Square.Colour opponentColour;

	AlphaBetaPruning(Evaluation eval, Board board) {
		this.eval = eval;
		this.board = board;
		this.colour = eval.colour;
		this.opponentColour = eval.opponentColour;
	}

	/**
	 * The parameters are just like the usual Alpha Beta Pruning algorithm.
	 * 
	 * Returns an array {score, bestPosition}. score is v in Norvig's Artificial
	 * Intelligence: A Modern Approach pseudocode.
	 * 
	 * bestPosition is the position of the best move using our unidimensional
	 * representation of the board.
	 */
	public int[] alphabeta_function(int depth, boolean maximizingPlayer,
			int alpha, int beta) {

		int score = Integer.MIN_VALUE;
		int bestPosition = -1;

		if (depth == 0
				|| board.numberOfOcuppiedSquares >= board.dimension
						* board.dimension) {
			score = eval.totalEvaluation();
			return new int[] { score, bestPosition };
		} else {
			for (Square s : board.squares) {
				if (s.colour == Square.Colour.EMPTY) {
					if (maximizingPlayer) {
						Action action = new Action(false, colour,
								opponentColour, s.position, new FloodFill(),
								board);
						score = alphabeta_function(depth - 1, false, alpha,
								beta)[0];
						if (score > alpha) {
							alpha = score;
							bestPosition = s.position;
						}
						action.undoApplyAction();
					} else {
						Action action = new Action(true, opponentColour,
								colour, s.position, new FloodFill(), board);
						score = alphabeta_function(depth - 1, true, alpha, beta)
								[0];
						if (score < beta) {
							beta = score;
							bestPosition = s.position;
						}
						action.undoApplyAction();
					}

					if (alpha >= beta)
						break;
				}

			}

			if (maximizingPlayer) {
				return new int[] { alpha, bestPosition };
			} else {
				return new int[] { beta, bestPosition };
			}

		}

	}

}
