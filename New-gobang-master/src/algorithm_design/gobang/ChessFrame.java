package algorithm_design.gobang;

import javax.swing.JFrame;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * @author kaimo
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
	
	public static HashMap <String, Integer> toScore1 = new HashMap<>();
	public static HashMap <String, Integer> toScore2= new HashMap<>();
	public static HashMap <String, Integer> toScore3 = new HashMap<>();
	public static HashMap <String, Integer> toScore4 = new HashMap<>();
	public static HashMap <String, Integer> toScore5 = new HashMap<>();
	
	// two buffer to solve screen flashes
	private Image iBuffer;  
	private Graphics gBuffer; 

	
	
	public ChessFrame() throws HeadlessException {
		super();
		
		
		//toScore1.put("a__", 1);
		toScore1.put("___a__", 20);
		toScore1.put("__a__", 20);
		// 眠二
		//toScore2.put("aa___", 110);
		toScore2.put("_a_a__", 120);
		//toScore2.put("___aa", 110);
		toScore2.put("__a_a_", 120);
		//toScore2.put("a__a_", 90);
		//toScore2.put("_a__a", 90);
		//toScore2.put("a___a", 80);

		// 活二"_aa___"
		toScore2.put("__aa__", 120);
		//toScore2.put("_a_a_", 500);
		//toScore2.put("_a__a_", 400);
		//toScore2.put("_aa__", 500);
		//toScore2.put("__aa_", 500);

		// 眠三
//		toScore3.put("a_a_a", 1000);
//		toScore3.put("aa__a", 1000);
//		toScore3.put("_aa_a", 1100);
//		toScore3.put("a_aa_", 1100);
//		toScore3.put("_a_aa", 1100);
//		toScore3.put("aa_a_", 1100);
//		toScore3.put("aaa__", 1100);

		// 跳活三
		toScore3.put("_aa_a_", 720);
		toScore3.put("_a_aa_", 720);

		// 活三 
		toScore3.put("_aaa__", 720);
		toScore3.put("__aaa_", 720);

		// 冲四
		toScore3.put("a_aaa", 720);
		toScore3.put("aaa_a", 720);
		toScore3.put("_aaaa", 720);
		toScore3.put("aaaa_", 720);
		toScore3.put("aa_aa", 720);

		// 活四
		toScore4.put("_aaaa_", 4320);

		// 连五
		toScore5.put("aaaaa", 50000);
		
		
	}

	public void init() {
		// initialized all coordinates to zero
		for(int i = 0; i < ChessFrame.CHESS_SIZE; i++) {
			for (int j = 0; j < ChessFrame.CHESS_SIZE; j++) {
				this.s[i][j] = EMPTY;
			}
		}
		// always robot move first
		this.makeMove(7, 7, WHITE);
		// turn to player move
		MouseListener.SWITCH = 1;
	}
	
	/**
	 * draw ChessBoard
	 */
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


	public boolean isEmpty(int x, int y) {
		return ChessFrame.s[x][y] == EMPTY;
	}
	
	public void makeMove(int x, int y, int color) {
		ChessFrame.s[x][y] = color;
	}
	
	public void unMove(int x, int y) {
		ChessFrame.s[x][y] = ChessFrame.EMPTY;
	}

	/**
	 * method to check (x, y) if out of boundary
	 */
	public boolean isLegal(int x, int y) {

		return (x > 0 && y > 0 &&
				x < ChessFrame.CHESS_SIZE &&
				y < ChessFrame.CHESS_SIZE);
	}
	
	
	/**
	 *  count all color chess in +/-(dx, dy) direction line
	 */
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

	public int reckon(int color) {
		int dx[] = { 1, 0, 1, 1 };
		int dy[] = { 0, 1, 1, -1 };
		int score = 0;
		
		

		// traversal each coordinate
		for (int x = 0; x < ChessFrame.CHESS_SIZE; x++) {
			for (int y = 0; y < ChessFrame.CHESS_SIZE; y++) {

				// skip the opponent's color
				if (ChessFrame.s[x][y] != color) {
					continue;
				}
				// a variable to store the count of continuous chess
				int recordPad[][] = new int[2][100];
			
				// traversal each direction lines
				for (int i = 0; i < 4; i++) {
					  StringBuilder input = new StringBuilder();
						String str1 = "a";
						String str2 = "";

					int count = 1;
					int num = 0;
					// direction #1 //
					int nextX = x + dx[i]; // get next (x,y)
					int nextY = y + dy[i]; // get next (x,y)
				
					while (isLegal(nextX, nextY) && ChessFrame.s[nextX][nextY] != -color && count < 5) {
						// 判断6个棋，用str标记棋型，a为子，_为空
						if (ChessFrame.s[nextX][nextY] == color) {
							str1 += "a";
							num++;
						} else {
							str1 += "_";
						}
						nextX = nextX + dx[i];
						nextY = nextY + dy[i];
						count++;
					}
                    count = 1;
					nextX = x - dx[i];
					nextY = y - dy[i];
					while (isLegal(nextX, nextY) && ChessFrame.s[nextX][nextY] != -color && count < 5) {
						// 判断6个棋，用str标记棋型，a为子，_为空
						if (ChessFrame.s[nextX][nextY] == color) {
							str2 += "a";
							num++;
						} else {
							str2 += "_";
						}
						nextX = nextX - dx[i];
						nextY = nextY - dy[i];
						count++;
					}
                input.append(str2);
                input = input.reverse();
                input.append(str1);

                
                if(num>0){
                    int a = 0;
                    
                	for(String key : toScore5.keySet()){
        				if (input.toString().contains(key) ) {
        					//System.out.println("input = "+input.toString());
        					//score = Math.max(toScore5.get(key), score);
        					score += toScore5.get(key);
        					a = 1;
        					
        				}	
    				}
                	if (a == 1) continue;
                	
               
                	for(String key : toScore4.keySet()){
        				if (input.toString().contains(key) ) {
        					//System.out.println("input = "+input.toString());
        					//score = Math.max(toScore4.get(key), score);
        					score += toScore4.get(key);
        					a = 1;
        		
        				}	
    				}	
                	if (a == 1) continue;
                	
                	for(String key : toScore3.keySet()){
        				if (input.toString().contains(key) ) {
        					//System.out.println("input = "+input.toString());
        					//score = Math.max(toScore3.get(key), score);
        					score += toScore3.get(key);
        					a = 1;
        					
        				}	
    				}	
                	if (a == 1) continue;
                	
               
                	for(String key : toScore2.keySet()){
        				if (input.toString().contains(key) ) {
        					//System.out.println("input = "+input.toString());
        					//score = Math.max(toScore2.get(key), score);
        					score += toScore2.get(key);
        					a = 1;
        					
        				}	
    				}	
                	if (a == 1) continue;
                	
                	for(String key : toScore1.keySet()){
        				if (input.toString().contains(key) ) {
        					//System.out.println("input = "+input.toString());
        					//score = Math.max(toScore1.get(key), score);
        					score += toScore1.get(key);
        					a = 1;
        					
        				}	
    				}	
                	if (a == 1) continue;
                	
                }else {
                	continue;
                }
                

				}
				
				

			}
		}

		return score;
	}


	
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









}

