package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import domain.Board;
import intl.Messages;

// Main window for the chess game.
public class MainFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 560;
	private static final int DEFAULT_HEIGHT = 600;

	public MainFrame(Board board) {
		setTitle(Messages.get("app.title"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		getContentPane().add(new BoardPanel(board), BorderLayout.CENTER);
		setVisible(true);
	}
}
