package algorithm_design.gobang;

import java.util.Random;

/**
 * @author kaimo
 *
 */
public class Robot {

	// private static ChessBoard chess = ChessBoard();
	private static int SEARCH_DEPTH = 2;
	private static int ROBOT_COLOR = ChessFrame.WHITE;
	private static ChessFrame chessframe = new ChessFrame();

	// constructor
	public Robot() {
	};

	public int tempCorrX = -1;
	public int tempCorrY = -1;

	// alpha-beta pruning search
	public int alphaBeta(int depth, int alpha, int beta, int color, int currX, int currY) {
		// the condition to break recursion
		// search depth meet || game over
		if (depth >= SEARCH_DEPTH || chessframe.isEnd(currX, currY, -color)) {
			int score = chessframe.reckon(ROBOT_COLOR) - chessframe.reckon(-ROBOT_COLOR);
			return score;
		}

		// recursively
		// but only in search boundary
		for (int x = ChessFrame.SEARCH[0]; x <= ChessFrame.SEARCH[2]; x++) {
			for (int y = ChessFrame.SEARCH[1]; y <= ChessFrame.SEARCH[3]; y++) {

				if (!chessframe.isEmpty(x, y)) {
					continue;
				}
				chessframe.makeMove(x, y, color);
				
				int score = alphaBeta(depth + 1, alpha, beta, color, x, y);
				chessframe.unMove(x, y);

				
				
				//Max 
				if (depth % 2 == 0) {
					
					if (score > alpha) {
						// update alpha & coordinate
						tempCorrX = x;
						tempCorrY = y;
						alpha = score;
					}

					if (alpha >= beta) {
						// pruning
						return alpha;

					}
				} 
				//Min
				else {
					if (score < beta) {
						// update beta & coordinate
						tempCorrX = x;
						tempCorrY = y;
						beta = score;

					}
					if (alpha >= beta) {
						// pruning
						return beta;

					}
				}

			}
		}

		return depth % 2 == 0 ? alpha : beta;

	}

	/**
	 * get the next step for robot
	 */
	public int[] nextStep(int color) {

		int corr[] = new int[2];
		int scoreMax = -100000000;
		Random random = new Random();

		int score = -alphaBeta(0, -100000000, 100000000, -color, ChessFrame.SEARCH[0], ChessFrame.SEARCH[1]);

		int random_state = random.nextInt();
		if (score > scoreMax || (score == scoreMax && random_state >= 50)) {
			scoreMax = score;
			System.out.println("scoreMax = " + scoreMax);

				corr[0] = tempCorrX;
				corr[1] = tempCorrY;
			
		}

		return corr;
	}
}
