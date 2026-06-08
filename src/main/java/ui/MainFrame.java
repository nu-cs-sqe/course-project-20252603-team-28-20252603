package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.time.Duration;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

		JLabel whiteName = new JLabel(Messages.get("clock.white"));
		JLabel blackName = new JLabel(Messages.get("clock.black"));
		JLabel whiteTime = new JLabel("5:00");
		JLabel blackTime = new JLabel("5:00");
		whiteName.setFont(whiteName.getFont().deriveFont(Font.BOLD, 24f));
		blackName.setFont(blackName.getFont().deriveFont(Font.BOLD, 24f));
		whiteTime.setFont(new Font("SansSerif", Font.BOLD, 24));
		blackTime.setFont(new Font("SansSerif", Font.BOLD, 24));

		JPanel clockBar = new JPanel();
		clockBar.add(whiteName);
		clockBar.add(whiteTime);
		clockBar.add(blackName);
		clockBar.add(blackTime);

		add(clockBar, BorderLayout.NORTH);
		add(new BoardPanel(game, board), BorderLayout.CENTER);

		JLabel statusLabel = new JLabel(statusText(game));
		statusLabel.setHorizontalAlignment(JLabel.CENTER);
		statusLabel.setFont(statusLabel.getFont().deriveFont(20f));
		add(statusLabel, BorderLayout.SOUTH);

		Timer timer = new Timer(1000, e -> {
			if (game.getStatus() != GameStatus.IN_PROGRESS) {
				statusLabel.setText(statusText(game));
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
			whiteTime.setText(format(game.clock().remaining(Color.WHITE)));
			blackTime.setText(format(game.clock().remaining(Color.BLACK)));
			statusLabel.setText(statusText(game));
		});
		game.clock().start(game.currentTurn());
		timer.start();

		setVisible(true);
	}

	private static String statusText(Game game) {
		GameStatus status = game.getStatus();
		if (status == GameStatus.WHITE_WIN) {
			return Messages.get("status.whiteWin");
		}
		if (status == GameStatus.BLACK_WIN) {
			return Messages.get("status.blackWin");
		}
		if (status == GameStatus.STALEMATE) {
			return Messages.get("status.stalemate");
		}
		return game.currentTurn() == Color.WHITE
			? Messages.get("status.whiteToMove")
			: Messages.get("status.blackToMove");
	}

	private static String format(Duration d) {
		long totalSec = Math.max(0, (long) Math.ceil(d.toMillis() / 1000.0));
		return String.format("%d:%02d", totalSec / 60, totalSec % 60);
	}
}
