package cn.lantian.Fivezq;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//棋盘鼠标监听器 
public class wzqListener implements MouseListener {
	private Graphics g;
	// 用来确定下什么棋
	public static int t = 1;
	private WuzqFrame wzq;

	// 构造器，将画布传过来
	public wzqListener(Graphics g, WuzqFrame wzq) {
		this.g = g;
		this.wzq = wzq;
	}

	public void mousePressed(MouseEvent e) {
		// 获得要下的棋子的行和列
		int x1 = Math.round((float) (e.getX() - WuzqFrame.x) / WuzqFrame.size);
		int y1 = Math.round((float) (e.getY() - WuzqFrame.y) / WuzqFrame.size);
		// 判断行列是否出界，及是否放了棋子
		if (x1 >= 0 && x1 < WuzqFrame.n && y1 >= 0 && y1 < WuzqFrame.n && WuzqFrame.s[x1][y1] == 0) {
			// 棋子放在棋盘上后，数组做相应标示
			WuzqFrame.s[x1][y1] = t;
			// 重绘棋盘
			wzq.repaint();
			// 调用QiziPd类中的方法，来判断输赢
			QiziPd.pd(t, x1, y1, g, wzq);
			// 改变棋子颜色
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