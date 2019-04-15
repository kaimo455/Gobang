package algorithm_design.gobang;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

/**
 * @author kaimo
 *
 */
public class MouseListener implements java.awt.event.MouseListener {

	// member variables
	private ChessFrame chessframe;
	// set robot
	public static Robot robot = new Robot();
	// switch
	public static int SWITCH; // 1 - black move; -1 - white move  

	// constructor
	public MouseListener(ChessFrame chessframe) {
		this.chessframe = chessframe;
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (SWITCH == 1) {
			// player move

			// capture the mouse current coordinate
			int x = Math.round((float) (e.getX() - ChessFrame.x) / ChessFrame.CELL_SIZE);
			int y = Math.round((float) (e.getY() - ChessFrame.y) / ChessFrame.CELL_SIZE);
			// player move
			if (this.chessframe.isLegal(x, y) && ChessFrame.s[x][y] == ChessFrame.EMPTY) {
				this.chessframe.makeMove(x, y, ChessFrame.BLACK);
				this.chessframe.updateBoundary(x, y);
				this.chessframe.repaint();
				if (this.chessframe.isEnd(x, y, ChessFrame.BLACK)) {
					System.out.println("player wins!");
					App app= new App();
					JOptionPane.showMessageDialog(null, "Congratulations,You Win!","",JOptionPane.INFORMATION_MESSAGE,app.icon2);
					this.chessframe.init();
					this.chessframe.repaint();
				} else {
					SWITCH = -1;
				}
			}

			// switch to robot move

		}

		if (SWITCH == -1) {
			// robot move
			int robotXY[] = robot.nextStep(ChessFrame.WHITE);
			if (this.chessframe.isLegal(robotXY[0], robotXY[1])) {
				this.chessframe.makeMove(robotXY[0], robotXY[1], ChessFrame.WHITE);
			}
			this.chessframe.updateBoundary(robotXY[0], robotXY[1]);
			if (this.chessframe.isEnd(robotXY[0], robotXY[1], ChessFrame.WHITE)) {
				System.out.println("robot wins!");
				App app = new App();
				JOptionPane.showMessageDialog(null, "Sorry, you lose.","", JOptionPane.INFORMATION_MESSAGE,app.icon1);
				this.chessframe.init();
				this.chessframe.repaint();
			}

			// switch to player move
			SWITCH = 1;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
