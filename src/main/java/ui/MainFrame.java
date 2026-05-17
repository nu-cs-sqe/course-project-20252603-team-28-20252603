package ui;

import javax.swing.JFrame;

// Main window for the chess game.
public class MainFrame extends JFrame {
	private static final String WINDOW_TITLE = "CS380 Team 28 - Chess";
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 600;

	public MainFrame() {
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
