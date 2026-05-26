package domain;

import java.util.Objects;

public final class Game {
	private final Board board;
	private Color currentTurn;

	public Game(Board board) {
		Objects.requireNonNull(board);
		this.board = board;
		this.currentTurn = Color.WHITE;
	}

	public Color currentTurn() {
		return currentTurn;
	}

	public Board board() {
		return board;
	}
}