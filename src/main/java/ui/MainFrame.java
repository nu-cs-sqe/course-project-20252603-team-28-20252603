package ui;

import javax.swing.JFrame;

import domain.Board;
import domain.Game;
import intl.Messages;

// Main window for the chess game.
public class MainFrame extends JFrame {
	public MainFrame(Game game, Board board) {
		setTitle(Messages.get("app.title"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		add(new BoardPanel(game, board));
		setVisible(true);
	}
}
