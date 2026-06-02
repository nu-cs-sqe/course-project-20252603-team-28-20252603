package ui;

import javax.swing.JFrame;

import domain.Board;
import intl.Messages;

// Main window for the chess game.
public class MainFrame extends JFrame {
	public MainFrame(Board board) {
		setTitle(Messages.get("app.title"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		add(new BoardPanel(board));
		setVisible(true);
	}
}