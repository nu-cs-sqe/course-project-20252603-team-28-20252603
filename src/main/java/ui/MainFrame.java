package ui;

import java.util.Optional;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		setLayout(new BorderLayout());

		JLabel whiteTime = new JLabel("White: 5:00");
		JLabel blackTime = new JLabel("Black: 5:00");
		whiteTime.setFont(new Font("SansSerif", Font.BOLD, 24));
		blackTime.setFont(new Font("SansSerif", Font.BOLD, 24));

		JPanel clockBar = new JPanel();
		clockBar.add(whiteTime);
		clockBar.add(blackTime);

		add(clockBar, BorderLayout.NORTH);
		add(new BoardPanel(game, board), BorderLayout.CENTER);

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
