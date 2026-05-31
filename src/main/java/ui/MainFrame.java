package ui;

import javax.swing.JFrame;

import intl.Messages;

// main window for the chess game
public class MainFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 600;

	public MainFrame() {
		setTitle(Messages.get("app.title"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
