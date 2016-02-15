package au.edu.unimelb.ai.squatter;
/* Implements a variation of the Flood fill algorithm
 * to detect enclosed regions in a given representation
 * of a squatter game and display the players' score.
 *
 * Please check comments.txt for algorithm explanation.
 *
 * COMP30024_2015_SM1: Artificial Intelligence
 * @author <Andre Peric Tavares> <aperic>
 * @author <Felipe Matheus Costa Silva> <fcosta1>
 * Date: 29/03/15
 */

import java.util.ArrayList;
import java.util.Scanner;

/** Read input from stdin that represents an squatter game,
 * print the winner's colour (or "Draw") and the scores.
 */
public class main {

	public static void main(String[] args) throws Exception {
			Board board = createBoard();
			ArrayList<ArrayList<Square>> enclosed_regions = createArrayOfEnclosedRegions(board);
			countPoints(board, enclosed_regions);
	}
	
	
	/** Read input from stdin that represents an squatter game.
	 * The first input line is the dimension of the board.
	 * Each following line represents a board row and should have
	 * "+", "-", "W" or "B" symbols separated by whitespace.
	 * These symbols represent, respectively, a empty position,
	 * an enclosed piece, a white piece and a black piece.
	 * 
	 * If the input is invalid (that is, the first line is not
	 * a number and any following lines have a symbol other than
	 * "+", "-", "W" or "B" enclosed by whitespace, if the
	 * number of symbols or lines differs from the board dimension.
	 * @return An object of type Board that represents the state of the game.
	 * @throws Exception
	 */
	public static Board createBoard() throws Exception{
		
		Scanner scanner = new Scanner(System.in);

		if (!scanner.hasNextInt() || !scanner.hasNext()){
			System.out.println("Error");
			System.exit(0);
		}

			int dimension = scanner.nextInt();
			scanner.nextLine();

			Board board = new Board(dimension);

			for (int x = 0; x < dimension; x++) {
				String line = scanner.nextLine();

				String[] splitted_line = line.split("\\s");
				if (splitted_line.length != dimension){
					scanner.close();
					System.out.println("Error");
					System.exit(0);
				}
				for (int y = 0; y < splitted_line.length; y++) {
						switch (splitted_line[y]) {
						case "+":
							System.out.println("None"); // Game has not ended.
							System.exit(0);
							break;
						case "W":
							board.squares[x][y] = new Square(x, y, new Piece(
									Piece.Colour.WHITE, false));
							break;
						case "B":
							board.squares[x][y] = new Square(x, y, new Piece(
									Piece.Colour.BLACK, false));
							break;
						case "-":
							board.squares[x][y] = new Square(x, y, new Piece(
									null, true));
							break;
						default: System.out.println("Error");
								 System.exit(0);
						}
				}
			}

			while (scanner.hasNextLine()){
				if (!scanner.nextLine().equals("")){
					scanner.close();
					System.out.println("Error");
					System.exit(0);
				}
			}

			// Uncomment next block and comment the previous one if 
			// no empty line is expected.
			// Block discarded to adjust to examples posted on LMS,
			// which had extra newlines.
//			if (scanner.hasNextLine()){
//				throw new Exception(
//						"Number of lines and dimension don't match.");
//			}

			scanner.close();

			return board;
		
	}

	/** Insert all enclosed regions in a list.
	 * @param board
	 * @return  ArrayList of ArrayLists, each one containing 
	 * square objects representing the position 
	 * of the enclosed pieces of the same region
	 */
	public static ArrayList<ArrayList<Square>> createArrayOfEnclosedRegions(Board board) {

		ArrayList<ArrayList<Square>> list_of_enclosed_regions = new ArrayList<ArrayList<Square>>();
		ArrayList<Square> unchecked_enclosed_pieces = new ArrayList<Square>();

		// Insert all enclosed squares in an ArrayList.
		for (int i = 0; i < board.dimension; i++) {
			for (int j = 0; j < board.dimension; j++) {
				Piece piece = board.squares[i][j].getPieceOnTop();
				if (piece.isEnclosed()) {
					unchecked_enclosed_pieces.add(board.squares[i][j]);
				}
			}
		}

		while (!unchecked_enclosed_pieces.isEmpty()) {
			ArrayList<Square> pieces_in_enclosed_region = new ArrayList<Square>();
			ArrayList<Square> queue = new ArrayList<Square>();

			Square location = unchecked_enclosed_pieces.get(0);
			queue.add(location);

			while (!queue.isEmpty()) {
				Square current_location = queue.get(0);
				queue.remove(0);
				pieces_in_enclosed_region.add(current_location);
				unchecked_enclosed_pieces.remove(current_location);
				addToQueueIfEnclosed(board, current_location.getX(), current_location.getY() + 1, pieces_in_enclosed_region, queue);
				addToQueueIfEnclosed(board, current_location.getX(), current_location.getY() - 1, pieces_in_enclosed_region, queue);
				addToQueueIfEnclosed(board, current_location.getX() - 1, current_location.getY(), pieces_in_enclosed_region, queue);
				addToQueueIfEnclosed(board, current_location.getX() + 1, current_location.getY(), pieces_in_enclosed_region, queue);
				addToQueueIfEnclosed(board, current_location.getX() - 1, current_location.getY() - 1, pieces_in_enclosed_region, queue);
				addToQueueIfEnclosed(board, current_location.getX() - 1, current_location.getY() + 1, pieces_in_enclosed_region, queue);
				addToQueueIfEnclosed(board, current_location.getX() + 1, current_location.getY() + 1, pieces_in_enclosed_region, queue);
				addToQueueIfEnclosed(board, current_location.getX() + 1, current_location.getY() - 1, pieces_in_enclosed_region, queue);
			}

			list_of_enclosed_regions.add(pieces_in_enclosed_region);

		}
		return list_of_enclosed_regions;
	}
	
		
	/** Count each player's score and print it.
	 * @param board
	 * @param list_of_enclosed_regions
	 */
	public static void countPoints(Board board,
			ArrayList<ArrayList<Square>> list_of_enclosed_regions) {

		int white_score = 0;
		int black_score = 0;

		for (int i = 0; i < list_of_enclosed_regions.size(); i++) {
			Square square = list_of_enclosed_regions.get(i).get(0);

			if (board.squares[square.x - 1][square.y].pieceOnTop.colour == Piece.Colour.WHITE) {
				white_score += list_of_enclosed_regions.get(i).size();
			} else {
				black_score += list_of_enclosed_regions.get(i).size();

			}
		}
	
		if (white_score > black_score){
			System.out.println("White");
		}
		else if (white_score < black_score){
			System.out.println("Black");
		}
		else {
			System.out.println("Draw");
		}
		
		System.out.println("White: " + white_score);
		System.out.println("Black: " + black_score);

	}

			
	/** Verify if position at (x,y) contains a square that is
	 * enclosed and has not been visited yet.
	 * @param board
	 * @param current_location
	 * @param pieces_in_enclosed_region
	 * @param queue
	 */
	public static void addToQueueIfEnclosed(Board board, int x, int y, ArrayList<Square> pieces_in_enclosed_region, ArrayList<Square> queue){
		if (board.isInsideBoard(x, y)){
			Square square_at_location = board.squares[x][y];
			Piece piece_at_location = board.squares[x][y].getPieceOnTop();
			if (piece_at_location.isEnclosed()
					&& !(queue.contains(square_at_location) || pieces_in_enclosed_region
							.contains(square_at_location))) {
				queue.add(square_at_location);
			}
		}
	}
}