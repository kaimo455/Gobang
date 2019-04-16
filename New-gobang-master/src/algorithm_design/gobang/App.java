package algorithm_design.gobang;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
		icon1 = new ImageIcon(App.class.getResource("/Images/lose.jpg"));
		icon2 = new ImageIcon(App.class.getResource("/Images/win.jpg"));
	}

	public static void main(String[] args) {
		
		//create a chessFrame
		ChessFrame chessframe = new ChessFrame();
		// basic setting for this chess frame
		chessframe.setTitle("Gobang");
		chessframe.setSize(480, 500);
		chessframe.getContentPane().setBackground(new Color(248, 230, 150));
		chessframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chessframe.setLocationRelativeTo(null);
		chessframe.setResizable(false);
		chessframe.setVisible(true);
		chessframe.init();
		chessframe.repaint();
		// mouse listening class instance
		MouseListener mouselistener = new MouseListener(chessframe);
		chessframe.addMouseListener(mouselistener);
		
	}

}
