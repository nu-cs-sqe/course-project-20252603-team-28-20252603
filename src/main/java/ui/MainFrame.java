package ui;

import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.Timer;

import domain.Board;
import domain.Color;
import domain.Game;
import domain.GameStatus;
import intl.Messages;

// Main window for the chess game.
public class MainFrame extends JFrame {
	public MainFrame(Game game, Board board) {
		setTitle(Messages.get("app.title"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		add(new BoardPanel(game, board));

		Timer timer = new Timer(1000, e -> {
			if (game.getStatus() != GameStatus.IN_PROGRESS) {
				((Timer) e.getSource()).stop();
				return;
			}
			Optional<Color> winner = game.winnerByTimeout();
			if (winner.isPresent()) {
				Color loser = winner.get() == Color.WHITE
					? Color.BLACK : Color.WHITE;
				game.resign(loser);
				((Timer) e.getSource()).stop();
			}
		});
		timer.start();

		setVisible(true);
	}
}
