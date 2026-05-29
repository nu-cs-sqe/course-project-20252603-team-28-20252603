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

	public void makeMove(Square from, Square to) {
		Piece piece = board.pieceAt(from)
			.orElseThrow(() -> new IllegalStateException("No piece at source square"));
		if (piece.color() != currentTurn) {
			throw new IllegalStateException("Not your turn");
		}
		board.move(from, to);
		currentTurn = currentTurn.opposite();
	}
}
