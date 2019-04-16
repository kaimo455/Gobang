package algorithm_design.gobang;
import java.util.Random;

/**
 * @author kaimo
 * @author Quan Liu
 * @author Xiaoqian Xu
 * @author Tianqi Xie
 * @author Haochen Wei
 *
 */
public class Robot {
	
//	private static ChessBoard chess = ChessBoard();
	public static int SEARCH_DEPTH = 1;
	private static int ROBOT_COLOR = ChessFrame.WHITE;
	private static ChessFrame chessframe = new ChessFrame();
	// two temporary variables to store alpha&beta value coordinates for level 3 
	public int tempCorrX = -1;
	public int tempCorrY = -1;
	// constructor
	public Robot() {};
	
	
	//--------------------------------//
	// 			   level 1			  //
	//--------------------------------//
	public int alphaBeta_v1(int depth, int alpha, int beta, int color, int currX, int currY) {
		// the condition to break recursion
		// search depth meet || game over
		if (depth >= SEARCH_DEPTH || chessframe.isEnd(currX, currY, -color)) {
			int score = chessframe.reckon_v1(ROBOT_COLOR) - chessframe.reckon_v1(-ROBOT_COLOR);
			if (depth % 2 == 0) {
				score = -score;
			}
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
				int score = -alphaBeta_v1(depth+1, -beta, -alpha, -color, x, y);
				chessframe.unMove(x, y);
				
				// pruning
				if (score >= beta) {
					return beta;
				}
				// update alpha
				if (score > alpha) {
					alpha = score;
				}
				
			}
		}
		
		return alpha;
	}
	public int[] nextStep_v1(int color) {
		int corr[] = new int[2];
		int scoreMax = -100000000;
		Random random = new Random();

		// we begin with search boundary
		for (int x = ChessFrame.SEARCH[0]; x <= ChessFrame.SEARCH[2]; x++) {
			for (int y = ChessFrame.SEARCH[1]; y <= ChessFrame.SEARCH[3]; y++) {
				
				if (!chessframe.isEmpty(x, y)) {
					continue;
				}
				chessframe.makeMove(x, y, color);
				int score = -alphaBeta_v1(0, -100000000, 100000000, -color, x, y);
				int random_state = random.nextInt();
				if (score > scoreMax ||
						(score == scoreMax && random_state >= 50)) {
					scoreMax = score;
					corr[0] = x;
					corr[1] = y;
				}
				chessframe.unMove(x, y);
			}
		}
		
		return corr;
	}
	
	
	//--------------------------------//
	// 			   level 2			  //
	//--------------------------------//
	public int alphaBeta_v2(int depth, int alpha, int beta, int color, int currX, int currY) {
		// the condition to break recursion
		// search depth meet || game over
		if (depth >= SEARCH_DEPTH || chessframe.isEnd(currX, currY, -color)) {
			int score = chessframe.reckon_v2(ROBOT_COLOR) - chessframe.reckon_v2(-ROBOT_COLOR);
			if (depth % 2 == 0) {
				score = -score;
			}
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
				int score = -alphaBeta_v2(depth+1, -beta, -alpha, -color, x, y);
				chessframe.unMove(x, y);
				
				// pruning
				if (score >= beta) {
					return beta;
				}
				// update alpha
				if (score > alpha) {
					alpha = score;
				}
				
			}
		}
		
		return alpha;
	}
	public int[] nextStep_v2(int color) {
		
		int corr[] = new int[2];
		int scoreMax = -100000000;
		Random random = new Random();

		// we begin with search boundary
		for (int x = ChessFrame.SEARCH[0]; x <= ChessFrame.SEARCH[2]; x++) {
			for (int y = ChessFrame.SEARCH[1]; y <= ChessFrame.SEARCH[3]; y++) {
				
				if (!chessframe.isEmpty(x, y)) {
					continue;
				}
				chessframe.makeMove(x, y, color);
				int score = -alphaBeta_v2(0, -100000000, 100000000, -color, x, y);
				int random_state = random.nextInt();
				if (score > scoreMax ||
						(score == scoreMax && random_state >= 50)) {
					scoreMax = score;
					corr[0] = x;
					corr[1] = y;
				}
				chessframe.unMove(x, y);
			}
		}
		
		return corr;
	}


	//--------------------------------//
	// 			   level 3			  //
	//--------------------------------//
	public int alphaBeta_v3(int depth, int alpha, int beta, int color, int currX, int currY) {
		// the condition to break recursion
		// search depth meet || game over
		if (depth >= SEARCH_DEPTH || chessframe.isEnd(currX, currY, -color)) {
			int score = chessframe.reckon_v3(ROBOT_COLOR) - chessframe.reckon_v3(-ROBOT_COLOR);
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
				
				int score = alphaBeta_v3(depth + 1, alpha, beta, color, x, y);
				chessframe.unMove(x, y);

				
				
				//Max layer
				if (depth % 2 == 0) {
					
					if (score > alpha) {
						// update alpha & coordinate
						tempCorrX = x;
						tempCorrY = y;
						alpha = score;
					}

					if (alpha >= beta) {
						return alpha;

					}
				} 
				//Min layer
				else {
					if (score < beta) {
						// update beta & coordinate
						tempCorrX = x;
						tempCorrY = y;
						beta = score;

					}
					if (alpha >= beta) {
						return beta;

					}
				}

			}
		}

		return depth % 2 == 0 ? alpha : beta;
	}
	public int[] nextStep_v3(int color) {
		int corr[] = new int[2];
		int scoreMax = -100000000;
		Random random = new Random();
		int score = -alphaBeta_v3(0, -100000000, 100000000, -color, ChessFrame.SEARCH[0], ChessFrame.SEARCH[1]);
		int random_state = random.nextInt();
		if (score > scoreMax || (score == scoreMax && random_state >= 50)) {
			scoreMax = score;
			corr[0] = tempCorrX;
			corr[1] = tempCorrY;
			
		}
		return corr;
	}
	
	
}
