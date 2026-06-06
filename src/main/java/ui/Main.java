package ui;

import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import domain.Board;
import domain.Game;
import intl.Messages;

public final class Main {
	private Main() {
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			selectLocale();
			Board board = Board.standardSetup();
			Game game = new Game(board);
			new MainFrame(game, board);
		});
	}

	private static void selectLocale() {
		String[] options = {"English", "한국어", "中文"};
		int choice = JOptionPane.showOptionDialog(
			null,
			"Select language / 언어 선택 / 选择语言",
			"CS380 Team 28 - Chess",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]
		);
		if (choice == 1) {
			Messages.setLocale(Locale.KOREAN);
		} else if (choice == 2) {
			Messages.setLocale(Locale.CHINESE);
		} else {
			Messages.setLocale(Locale.ENGLISH);
		}
	}
}
