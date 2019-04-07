package cn.lantian.Fivezq;

import java.awt.Graphics;

import javax.swing.JOptionPane;

//判断胜负 
public class QiziPd {
	// 将一些重要参数的值传过来jkshkjdhkawdjlkagg
	public static int[][] s = WuzqFrame.s;
	private static int n = WuzqFrame.n;
	// 构建两个移动数组（暂且这么说）
	private static int[] a = { -1, -1, 0, 1, 1, 1, 0, -1 };
	private static int[] b = { 0, 1, 1, 1, 0, -1, -1, -1 };

	// 判断方法，z为棋子颜色的标志，x y 为下的棋子的行与列
	public static void pd(int z, int x, int y, Graphics g, WuzqFrame wzq) {
		// 四个方向横、竖、左斜、右斜
		for (int i = 0; i < 4; i++) {
			// 记录在一条直线上的棋子数(不包括下的棋子)
			int temp = 0;
			// 里面以棋子为界，又分两个方向
			for (int j = 0; j < 2; j++) {
				int x1 = x, y1 = y;
				// 判断同一直线上的棋子数
				while (temp < 4) {
					// 算出那条直线上的棋子的坐标
					x1 = x1 + a[i + 4 * j];
					y1 = y1 + b[i + 4 * j];
					// 如果在棋盘内，继续循环，否则退出循环
					if (x1 < 0 || x1 >= n || y1 < 0 || y1 >= n)
						break;
					else {
						// 如果这条直线上有自己颜色的棋子，则temp++，否则退出
						if (s[x1][y1] == z) {
							temp++;
						} else
							break;
					}
				}
				// 如果连着的棋子数为4（没有包括自己，总数是五）
				if (temp == 4) {
					// 弹出谁赢了的提示框
					if (z == 1) {
						JOptionPane.showMessageDialog(null, "黑棋赢了，再来一盘吧！");
					} else {
						JOptionPane.showMessageDialog(null, "白棋赢了，再来一盘吧！");
					}
					// 初始数组
					wzq.chushi();
					// 重绘棋盘
					wzq.repaint();
					// 先下的 棋子为黑子，t改为白子，后面的语句会改过来
					wzqListener.t = -1;
					break;
				}
			}
		}

	}

}