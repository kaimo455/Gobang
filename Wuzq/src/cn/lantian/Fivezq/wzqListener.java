package cn.lantian.Fivezq;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Listener for mouse action
public class wzqListener implements MouseListener {
	private Graphics g;
	// Initialization
	public static int t = 1;
	private WuzqFrame wzq;

	// Chess graph
	public wzqListener(Graphics g, WuzqFrame wzq) {
		this.g = g;
		this.wzq = wzq;
	}

	public void mousePressed(MouseEvent e) {
		// get the coordinate of the chess
		int x1 = Math.round((float) (e.getX() - WuzqFrame.x) / WuzqFrame.size);
		int y1 = Math.round((float) (e.getY() - WuzqFrame.y) / WuzqFrame.size);
		// determine whether other chess locates on this coordinate or whether the coordinate is out of bound
		if (x1 >= 0 && x1 < WuzqFrame.n && y1 >= 0 && y1 < WuzqFrame.n && WuzqFrame.s[x1][y1] == 0) {
			// identify the coordinate of chess on the chess graph 
			WuzqFrame.s[x1][y1] = t;
			// repaint the chess graph
			wzq.repaint();
			// decide whether any player win the game
			QiziPd.pd(t, x1, y1, g, wzq);
			// change the color of the chess
			t = -t;
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