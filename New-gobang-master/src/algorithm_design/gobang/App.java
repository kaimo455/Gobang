package algorithm_design.gobang;

import java.awt.Color;

import javax.swing.ImageIcon;

/**
 * @author kaimo
 * @author Quan Liu
 * @author Xiaoqian Xu
 * @author Tianqi Xie
 * @author Haochen Wei
 *
 */

public class App {
	public ImageIcon icon1;
	public ImageIcon icon2;
	
	public App(){
		icon1 = new ImageIcon("./data/lose.jpg");
		icon2 = new ImageIcon("./data/win.jpg");
	}

	public static void main(String[] args) {
		ChessFrame chessframe = new ChessFrame();
		// basic setting for this chess frame 
		chessframe.setTitle("Gobang");
		chessframe.setSize(480, 500);
		chessframe.getContentPane().setBackground(new Color(248, 230, 150));
		chessframe.setDefaultCloseOperation(3);
		chessframe.setLocationRelativeTo(null);
		chessframe.setResizable(false);
		chessframe.setVisible(true);
		chessframe.init();
		// mouse listening class instance
		MouseListener mouselistener = new MouseListener(chessframe);
		chessframe.addMouseListener(mouselistener);
		
	}

}
