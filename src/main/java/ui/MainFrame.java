package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.Board;
import domain.Game;
import intl.Messages;

// Main window for the chess game.
public class MainFrame extends JFrame {
	private BoardPanel boardPanel;

	public MainFrame(Game game, Board board) {
		setTitle(Messages.get("app.title"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);

		boardPanel = new BoardPanel(game, board);
		add(boardPanel, BorderLayout.CENTER);

		JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton restartBtn = new JButton(Messages.get("app.restart"));
		restartBtn.addActionListener(e -> restart());
		topBar.add(restartBtn);
		add(topBar, BorderLayout.NORTH);

		setVisible(true);
	}

	private void restart() {
		Board board = Board.standardSetup();
		Game game = new Game(board);
		remove(boardPanel);
		boardPanel = new BoardPanel(game, board);
		add(boardPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
