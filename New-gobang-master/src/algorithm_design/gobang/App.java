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
		icon1 = new ImageIcon("./data/lose.jpg");
		icon2 = new ImageIcon("./data/win.jpg");
	}

	public static void main(String[] args) {
		// choose difficulty
		Object[] possibleValues = { "Rookie", "Master","Veteran" }; 
		Object selectedValue = JOptionPane.showInputDialog(null, "Choose difficulty", "Welcome", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
		if (selectedValue.toString().compareTo("Rookie") == 0) ChessFrame.LEVEL = 1;
		if (selectedValue.toString().compareTo("Master") == 0) ChessFrame.LEVEL = 2;
		if (selectedValue.toString().compareTo("Veteran") == 0) ChessFrame.LEVEL = 3;
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
		// mouse listening class instance
		MouseListener mouselistener = new MouseListener(chessframe);
		chessframe.addMouseListener(mouselistener);
		
	}

}
