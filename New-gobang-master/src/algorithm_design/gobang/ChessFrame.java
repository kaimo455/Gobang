package algorithm_design.gobang;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author kaimo
 * @author Quan Liu
 * @author Xiaoqian Xu
 * @author Tianqi Xie
 * @author Haochen Wei
 *
 */
public class ChessFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	// color value and empty value, player-black, robot-white
	public static int EMPTY = 0, BLACK = 1, WHITE = -1;
	// left-top coordinate
	public static int x = 30, y = 50;
	// set ChessBoard size and cell size
	public static int CHESS_SIZE = 15, CELL_SIZE = 30;
	// matrix to store all chess coordinates
	public static int[][] s = new int[CHESS_SIZE][CHESS_SIZE];
	// level = 1, 2, 3
	public static int LEVEL = 1;
	
	//initiate boundary
	public static int[] BOUNDARY = {0, 0, 0, 0};
	private int[] NULLBOUNDARY = {0, 0, 0, 0};
	public static int[] SEARCH = {0, 0, 0, 0};
	
	// scorebaord
	public static HashMap <String, Integer> toScore1 = new HashMap<>();
	public static HashMap <String, Integer> toScore2 = new HashMap<>();
	public static HashMap <String, Integer> toScore3 = new HashMap<>();
	public static HashMap <String, Integer> toScore4 = new HashMap<>();
	public static HashMap <String, Integer> toScore5 = new HashMap<>();

	// two buffer to solve screen flashes
	private Image iBuffer;  
	private Graphics gBuffer; 
	
	// constructor
	public ChessFrame(){
		// initialize scorebaord
		toScore1.put("___a__", 20);
		toScore1.put("__a__", 20);
		toScore2.put("_a_a__", 120);
		toScore2.put("__a_a_", 120);
		toScore2.put("__aa__", 120);
		toScore3.put("_aa_a_", 720);
		toScore3.put("_a_aa_", 720);
		toScore3.put("_aaa__", 720);
		toScore3.put("__aaa_", 720);
		toScore3.put("a_aaa", 720);
		toScore3.put("aaa_a", 720);
		toScore3.put("_aaaa", 720);
		toScore3.put("aaaa_", 720);
		toScore3.put("aa_aa", 720);
		toScore4.put("_aaaa_", 4320);
		toScore5.put("aaaaa", 50000);
	}

	// initialize chessboard
	public void init() {
		// initialized all coordinates to zero
		for(int i = 0; i < ChessFrame.CHESS_SIZE; i++) {
			for (int j = 0; j < ChessFrame.CHESS_SIZE; j++) {
				this.s[i][j] = EMPTY;
			}
		}
		// always robot move firsts
		this.makeMove(7, 7, WHITE);
		// turn to player move
		MouseListener.SWITCH = BLACK;
	}
	
	// draw ChessBoard
	public void paint(Graphics g) {
		
		// initialize paint
		super.paintComponents(g);
		//Graphics2D g2 = (Graphics2D)g; 
		//g2.setStroke(new BasicStroke(1.5f));
		
		// draw vertical and horizontal line
		for (int i = 0; i < CHESS_SIZE; i++) {
			for (int j = 0; j < CHESS_SIZE; j++) {
				g.drawLine(x + i * CELL_SIZE, y,
						x + i * CELL_SIZE, y + (CHESS_SIZE-1) * CELL_SIZE);
				g.drawLine(x, y + i * CELL_SIZE,
						x + (CHESS_SIZE-1) * CELL_SIZE, y + i * CELL_SIZE);
			}
		}
		
		// draw chess
		for (int i = 0; i < CHESS_SIZE; i++) {
			for (int j = 0; j < CHESS_SIZE; j++) {
				if (s[i][j] == BLACK) {
					// set default painting color to BLACK
					g.setColor(Color.BLACK);
					// draw black chess
					g.fillOval(x + i * CELL_SIZE - CELL_SIZE/2,
							y + j * CELL_SIZE - CELL_SIZE/2,
							CELL_SIZE, CELL_SIZE);
				} else if(s[i][j] == WHITE) {
					// set default painting color to WHITE
					g.setColor(Color.WHITE);
					// draw white chess
					g.fillOval(x + i * CELL_SIZE - CELL_SIZE/2,
							y + j * CELL_SIZE - CELL_SIZE/2,
							CELL_SIZE, CELL_SIZE);
				}
			}
		}	
	}
	
	@Override
	public void update(Graphics g) {
		 //initialize buffer
	    if(iBuffer==null)  {  
	       iBuffer=createImage(this.getSize().width,this.getSize().height);  
	       gBuffer=iBuffer.getGraphics();  
	    }  
       // set gBuffer the same as graphics we want to draw on the back end
       gBuffer.setColor(getBackground());  
       gBuffer.fillRect(0,0,this.getSize().width,this.getSize().height);  
       // repaint the graphic 
       gBuffer.setColor (getForeground());
       paint(gBuffer);
       //repaint the image
       g.drawImage(iBuffer,0,0,this);  
	}  


	// check this if this position already exist chess
	public boolean isEmpty(int x, int y) {
		return ChessFrame.s[x][y] == EMPTY;
	}
	
	// make move
	public void makeMove(int x, int y, int color) {
		ChessFrame.s[x][y] = color;
	}
	
	// undo move
	public void unMove(int x, int y) {
		ChessFrame.s[x][y] = ChessFrame.EMPTY;
	}

	// method to check (x, y) if out of boundary
	public boolean isLegal(int x, int y) {

		return (x >= 0 && y >= 0 &&
				x < ChessFrame.CHESS_SIZE &&
				y < ChessFrame.CHESS_SIZE);
	}

	//  count all color chess in +/-(dx, dy) direction line, basicly it just used for isEnd()
	public int countNum(int x, int y, int dx, int dy, int color) {
	
		int count = 1;	// current position itself
		
		// direction #1
		int nextX = x + dx;
		int nextY = y + dy;
		
		while (isLegal(nextX, nextY) &&
				ChessFrame.s[nextX][nextY] == color) {
			count = count + 1;
			nextX = nextX + dx;
			nextY = nextY + dy;
		}
		
		// direction #2
		nextX = x - dx;
		nextY = y - dy;
		while (isLegal(nextX, nextY) &&
				ChessFrame.s[nextX][nextY] == color) {
			count = count + 1;
			nextX = nextX - dx;
			nextY = nextY - dy;
		}
		
		return count;
	}
	
	// check if the color is win in this position
	public boolean isEnd(int x, int y, int color) {
		
		int dx[] = {1, 0, 1, 1};
		int dy[] = {0, 1, 1, -1};
		
		for (int i = 0; i < 4; i++) {
			int count = countNum(x, y, dx[i], dy[i], color);
			if (count >= 5) {
				return true;
			}
		}
		return false;
	}

	//update BOUNDARY when we place chess at (x, y) / input (x, y)
	public void updateBoundary(int x, int y){
		if (Arrays.equals(BOUNDARY, NULLBOUNDARY)) {
    		BOUNDARY[0] = x ;
    		BOUNDARY[1] = y ;
    		BOUNDARY[2] = x ;
    		BOUNDARY[3] = y ;
    	}else{
    		if (x < BOUNDARY[0]) {
    			BOUNDARY[0] = x;
    		}
    		if (x > BOUNDARY[2]) {
    			BOUNDARY[2] = x;
    		}
    		if (y < BOUNDARY[1]) {
    			BOUNDARY[1] = y;
    		}
    		if (y > BOUNDARY[3]) {
    			BOUNDARY[3] = y;
    		}
    	}
		// also we update search boundary
		ChessFrame.SEARCH = this.getSearchArea();
	}

	// get the search area according current boundary
	public int[] getSearchArea(){

		int[] ret = new int[4];

		ret[0] = BOUNDARY[0] - 3;
		ret[1] = BOUNDARY[1] - 3;
		ret[2] = BOUNDARY[2] + 3;
		ret[3] = BOUNDARY[3] + 3;

		// check if the search area is legal
		if (ret[0] < 0) {
			ret[0] = 0;
		}
		if (ret[2] > ChessFrame.CHESS_SIZE - 1) {
			ret[2] = ChessFrame.CHESS_SIZE - 1;
		}
		if (ret[1] < 0) {
			ret[1] = 0;
		}
		if (ret[3] > ChessFrame.CHESS_SIZE - 1) {
			ret[3] = ChessFrame.CHESS_SIZE - 1;
		}
    	return ret;
	}

	// evaluating function for level 1
	public int reckon_v1(int color){
		int dx[] = {1, 0, 1, 1};
		int dy[] = {0, 1, 1, -1};
		int score = 0;
		
		// traversal each coordinate
		for (int x = SEARCH[0]; x <= SEARCH[2]; x++) {
			for (int y = SEARCH[1]; y <= SEARCH[3]; y++) {
				
				// skip the opponent's color
				if (ChessFrame.s[x][y] != color) { continue; }
				// a variable to store the count of continuous chess
				int recordPad[][] = new int[2][100];				
				
				// traversal each direction lines
				for (int i = 0; i < 4; i++) {
					// flag to store if dead
					int flag1 = 0, flag2 = 0;
					// current count
					int count = 1;	
					//    direction #1    //
					int nextX = x + dx[i];	// get next (x,y)
					int nextY = y + dy[i];	// get next (x,y)
					// next (x, y) is legal and move on in same direction
					while (isLegal(nextX, nextY) &&
							ChessFrame.s[nextX][nextY] == color) {
						count = count + 1;
						nextX = nextX + dx[i];
						nextY = nextY + dy[i];
					}
					if (isLegal(nextX, nextY) &&
							ChessFrame.s[nextX][nextY] == ChessFrame.EMPTY) {
						flag1 = 1;
					}
					//    direction #2    //
					nextX = x - dx[i];
					nextY = y - dy[i];
					while (isLegal(nextX, nextY) &&
							ChessFrame.s[nextX][nextY] == color) {
						count = count + 1;
						nextX = nextX - dx[i];
						nextY = nextY - dy[i];
					}
					if (isLegal(nextX, nextY) &&
							ChessFrame.s[nextX][nextY] == ChessFrame.EMPTY) {
						flag2 = 1;
					}
					// i.e. at least one flag=1, alive
					if (flag1 + flag2 > 0) {
						// if +/- direction promising - dead
						// if + & - direction promising - alive
						++recordPad[flag1 + flag2 - 1][count];
					}
				}
				
				/*
				 * calculate score
				 * several situations:
				 * 		#1: 5							win
				 * 		#2: 4-2; 4-1x2; 4-1x1+3-2x1;	winable
				 * 		#3: 3-2x2						winable
				 * 		#4: 3-2x1+3-1x1					control
				 * 		#5: 4-1x1						control
				 * 		#6: 3-2x1						
				 * 		#7: 3-1x1
				 * 		#8: 2-2x2
				 * 		#9: 2-2x1
				 * 		#10: 2-1x1
				 */
				if (recordPad[0][5] > 0 || recordPad[1][5] > 0) {
					score = Math.max(score, 100000);
				}else if (recordPad[1][4] > 0||
						recordPad[0][4] > 1||
						recordPad[0][4] > 0 && recordPad[1][3] > 0) {
					score = Math.max(score, 10000);
				}else if (recordPad[1][3] > 1) {
					score = Math.max(score, 5000);
				}else if (recordPad[1][3] > 0 && recordPad[0][3] > 0) {
					score = Math.max(score, 1000);
				}else if (recordPad[0][4] > 0) {
					score = Math.max(score, 500);
				}else if (recordPad[1][3] > 0) {
					score = Math.max(score, 200);
				}else if (recordPad[0][3] > 0) {
					score = Math.max(score, 100);
				}else if (recordPad[1][2] > 1) {
					score = Math.max(score, 50);
				}else if (recordPad[1][2] > 0) {
					score = Math.max(score, 10);
				}else if (recordPad[0][2] > 0) {
					score = Math.max(score, 5);
				}else {
					score = Math.max(score, 1);
				}
				
				
			}
		}
		
		return score;
	}

	// evaluating function for level 2
	public int reckon_v2(int color) {
		int dx[] = {1, 0, 1, 1};
		int dy[] = {0, 1, 1, -1};
		int score = 0;
		// we only traversal boundary area
		for (int x = SEARCH[0]; x <= SEARCH[2]; x++) {
			for (int y = SEARCH[1]; y <= SEARCH[3]; y++) {
				// skip the opponent's color
				if (ChessFrame.s[x][y] != color) { continue; }			
				// traversal each direction lines, there are 4 lines
				for (int i = 0; i < 4; i++) {
					StringBuilder input = new StringBuilder();
					String str1 = "a";	// for direction #1
					String str2 = "";	// for direction #2
					int count = 1;
					// direction #1
					int nextX = x + dx[i]; // get next (x,y)
					int nextY = y + dy[i]; // get next (x,y)
					// we only exam next four chess
					while(isLegal(nextX, nextY) &&
						ChessFrame.s[nextX][nextY] != -color &&
						count < 5){
						if (ChessFrame.s[nextX][nextY] == color) {
							str1 += "a";
						} else {
							str1 += "_";
						}
						nextX = nextX + dx[i];
						nextY = nextY + dy[i];
						count++;
					}
					// str1 looks like "aa_a_" with length of 5


					// direction #2
					nextX = x - dx[i];
					nextY = y - dy[i];
					count = 1;
					// we only exam next four chess
					while(isLegal(nextX, nextY) &&
						ChessFrame.s[nextX][nextY] != -color &&
						count < 5){
						if (ChessFrame.s[nextX][nextY] == color) {
							str2 += "a";
						} else {
							str2 += "_";
						}
						nextX = nextX - dx[i];
						nextY = nextY - dy[i];
						count++;
					}
					// str2 looks like "_a_a" with length of 4

					// construct entire string
					input.append(str2);
					input = input.reverse();
					input.append(str1);

					int a = 0;

					for (String key : toScore5.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore5.get(key);
							a = 1;
						}
					}
					if (a == 1) {
						continue;
					}

					for (String key : toScore4.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore4.get(key);
							a = 1;
						}
					}
					if (a == 1) {
						continue;
					}

					for (String key : toScore3.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore3.get(key);
						}
					}
					if (a == 1) {
						continue;
					}

					for (String key : toScore2.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore2.get(key);
						}
					}
					if (a == 1) {
						continue;
					}

					for (String key : toScore1.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore1.get(key);
						}
					}
					if (a == 1) {
						continue;
					}

				}
				
			}
		}
		
		return score;
	}

	// evaluating function for level 3
	public int reckon_v3(int color) {
		int dx[] = {1, 0, 1, 1};
		int dy[] = {0, 1, 1, -1};
		int score = 0;
		// we only traversal boundary area
		for (int x = SEARCH[0]; x <= SEARCH[2]; x++) {
			for (int y = SEARCH[1]; y <= SEARCH[3]; y++) {
				// skip the opponent's color
				if (ChessFrame.s[x][y] != color) { continue; }			
				// traversal each direction lines, there are 4 lines
				for (int i = 0; i < 4; i++) {
					StringBuilder input = new StringBuilder();
					String str1 = "a";	// for direction #1
					String str2 = "";	// for direction #2
					int count = 1;
					// direction #1
					int nextX = x + dx[i]; // get next (x,y)
					int nextY = y + dy[i]; // get next (x,y)
					// we only exam next four chess
					while(isLegal(nextX, nextY) &&
						ChessFrame.s[nextX][nextY] != -color &&
						count < 5){
						if (ChessFrame.s[nextX][nextY] == color) {
							str1 += "a";
						} else {
							str1 += "_";
						}
						nextX = nextX + dx[i];
						nextY = nextY + dy[i];
						count++;
					}
					// str1 looks like "aa_a_" with length of 5


					// direction #2
					nextX = x - dx[i];
					nextY = y - dy[i];
					count = 1;
					// we only exam next four chess
					while(isLegal(nextX, nextY) &&
						ChessFrame.s[nextX][nextY] != -color &&
						count < 5){
						if (ChessFrame.s[nextX][nextY] == color) {
							str2 += "a";
						} else {
							str2 += "_";
						}
						nextX = nextX - dx[i];
						nextY = nextY - dy[i];
						count++;
					}
					// str2 looks like "_a_a" with length of 4

					// construct entire string
					input.append(str2);
					input = input.reverse();
					input.append(str1);

					int a = 0;

					for (String key : toScore5.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore5.get(key);
							a = 1;
						}
					}
					if (a == 1) {
						continue;
					}

					for (String key : toScore4.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore4.get(key);
							a = 1;
						}
					}
					if (a == 1) {
						continue;
					}

					for (String key : toScore3.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore3.get(key);
						}
					}
					if (a == 1) {
						continue;
					}

					for (String key : toScore2.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore2.get(key);
						}
					}
					if (a == 1) {
						continue;
					}

					for (String key : toScore1.keySet()) {
						if (input.toString().contains(key)) {
							score += toScore1.get(key);
						}
					}
					if (a == 1) {
						continue;
					}

				}
				
			}
		}
		
		return score;
	}












}

