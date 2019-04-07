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
	// 入口主函数
	public static void main(String[] args) {
		WuzqFrame wzq = new WuzqFrame();
		wzq.init();
	}

	public void init() {
		chushi();// 初始化棋盘
		// 基本设置
		this.setTitle("五子棋");
		this.setSize(520, 535);
		this.setDefaultCloseOperation(3);
		// 设置居中
		this.setLocationRelativeTo(null);
		// 用户不可调整大小
		this.setResizable(false);
		this.setVisible(true);
		// 获取窗体的画布
		Graphics g = this.getGraphics();
		// 定义鼠标监听类
		wzqListener wzql = new wzqListener(g, this);
		// 添加鼠标监听
		this.addMouseListener(wzql);
	}

	// 对窗体进行重绘
	public void paint(Graphics g) {
		// 调用父类的方法，super
		super.paint(g);
		// 画棋盘
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
					// 将颜色改为白色
					g.setColor(Color.WHITE);
					g.fillOval(x + i * WuzqFrame.size - WuzqFrame.size / 2, y + j * WuzqFrame.size - WuzqFrame.size / 2,
							WuzqFrame.qsize, WuzqFrame.qsize);
					// 定义黑色为初始色
					g.setColor(Color.BLACK);
				}

	}

	// 定义起始直线坐标
	public static int x = 30, y = 50;
	// 棋子的多少及格子大小
	public static int n = 15, size = 30, qsize = 30;
	// 定义一个数组来存储棋子
	public static int[][] s = new int[n][n];

	// 初始方法，数组全赋为0
	public void chushi() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				s[i][j] = 0;
	}
}