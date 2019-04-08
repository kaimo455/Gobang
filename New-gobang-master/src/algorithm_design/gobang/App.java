package algorithm_design.gobang;

public class App {

	public static void main(String[] args) {
		ChessFrame chessframe = new ChessFrame();
		chessframe.init();
		// basic setting for this chess frame 
		chessframe.setTitle("Gobang");
		chessframe.setSize(480, 500);
		chessframe.setDefaultCloseOperation(3);
		chessframe.setLocationRelativeTo(null);
		chessframe.setResizable(false);
		chessframe.setVisible(true);
		// mouse listening class instance
		MouseListener mouselistener = new MouseListener(chessframe);
		chessframe.addMouseListener(mouselistener);
	}

}
