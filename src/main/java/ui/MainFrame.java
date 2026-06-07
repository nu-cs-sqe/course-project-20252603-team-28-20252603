package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Board;
import domain.Color;
import domain.Game;
import domain.GameStatus;
import intl.Messages;

// Main window for the chess game.
public class MainFrame extends JFrame {
	private BoardPanel boardPanel;
	private JLabel statusLabel;
	private Game game;

	public MainFrame(Game game, Board board) {
		setTitle(Messages.get("app.title"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);

		this.game = game;
		boardPanel = new BoardPanel(game, board, this::updateStatus);
		add(boardPanel, BorderLayout.CENTER);

		JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton restartBtn = new JButton(Messages.get("app.restart"));
		restartBtn.addActionListener(e -> restart());
		topBar.add(restartBtn);
		add(topBar, BorderLayout.NORTH);

		statusLabel = new JLabel(statusText());
		statusLabel.setHorizontalAlignment(JLabel.CENTER);
		statusLabel.setFont(statusLabel.getFont().deriveFont(20f));
		add(statusLabel, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void restart() {
		Board board = Board.standardSetup();
		game = new Game(board);
		remove(boardPanel);
		boardPanel = new BoardPanel(game, board, this::updateStatus);
		add(boardPanel, BorderLayout.CENTER);
		updateStatus();
		revalidate();
		repaint();
	}

	private void updateStatus() {
		statusLabel.setText(statusText());
	}

	private String statusText() {
		GameStatus status = game.getStatus();
		if (status == GameStatus.WHITE_WIN) {
			return Messages.get("status.whiteWin");
		}
		if (status == GameStatus.BLACK_WIN) {
			return Messages.get("status.blackWin");
		}
		return game.currentTurn() == Color.WHITE
			? Messages.get("status.whiteToMove")
			: Messages.get("status.blackToMove");
	}
}
