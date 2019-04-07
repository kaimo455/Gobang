package cn.lantian.Fivezq;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class WuzqFrame extends JFrame {
	/**
	 * 五子棋
	 * 
	 * @兰天
	 */
	// this is the main method
	public static void main(String[] args) {
		WuzqFrame cf = new WuzqFrame();
		cf.init();
	}

	public void init() {
		initialize();// initialization
		// basic settings
		this.setTitle("Chess");
		this.setSize(520, 535);
		this.setDefaultCloseOperation(3);
		// center the interface
		this.setLocationRelativeTo(null);
		// user cannot adjust the size of the chessboard
		this.setResizable(false);
		this.setVisible(true);
		// get the canvas
		Graphics g = this.getGraphics();
		// new a mouselistener class
		wzqListener wzql = new wzqListener(g, this);
		// use mouse listener method
		this.addMouseListener(wzql);
	}

	// paint chessboard method
	public void paint(Graphics g) {
		// use super method
		super.paint(g);
		// draw the chessboard
		for (int i = 0; i < n + 1; i++) {
			g.drawLine(x + i * size, y, x + i * size, y + n * size);
		}
		for (int i = 0; i < n + 1; i++) {
			g.drawLine(x, y + i * size, x + n * size, y + i * size);
		}
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (s[i][j] == 1) {
					g.fillOval(x + i * WuzqFrame.size - WuzqFrame.size / 2, y + j * WuzqFrame.size - WuzqFrame.size / 2,
							WuzqFrame.qsize, WuzqFrame.qsize);
				} else if (s[i][j] == -1) {
					// change the color into white
					g.setColor(Color.WHITE);
					g.fillOval(x + i * WuzqFrame.size - WuzqFrame.size / 2, y + j * WuzqFrame.size - WuzqFrame.size / 2,
							WuzqFrame.qsize, WuzqFrame.qsize);
					// define the black color as default
					g.setColor(Color.BLACK);
				}

	}

	// define the coordinate of the starting point
	public static int x = 30, y = 50;
	// define the number of chess and the size of grids
	public static int n = 15, size = 30, qsize = 30;
	// new an two dimensional array to store the chess
	public static int[][] s = new int[n][n];

	// initialize method. set all numbers zero
	public void initialize() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				s[i][j] = 0;
	}
}