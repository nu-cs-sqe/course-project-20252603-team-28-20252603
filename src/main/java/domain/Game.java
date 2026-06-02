package domain;

import java.util.Objects;
import java.util.Optional;

public final class Game {
	private final Board board;
	private final ChessClock clock;
	private Color currentTurn;

	public Game(Board board) {
		Objects.requireNonNull(board);
		this.board = board;
		this.clock = null;
		this.currentTurn = Color.WHITE;
	}

	public Game(Board board, ChessClock clock) {
		Objects.requireNonNull(board);
		Objects.requireNonNull(clock);
		this.board = board;
		this.clock = clock;
		this.currentTurn = Color.WHITE;
		clock.start(Color.WHITE);
	}

	public Color currentTurn() {
		return currentTurn;
	}

	public Board board() {
		return board;
	}

	public ChessClock clock() {
		return clock;
	}

	public Optional<Color> winnerByTimeout() {
		if (clock == null) {
			return Optional.empty();
		}
		clock.tick();
		if (clock.isExpired(Color.WHITE)) {
			return Optional.of(Color.BLACK);
		}
		if (clock.isExpired(Color.BLACK)) {
			return Optional.of(Color.WHITE);
		}
		return Optional.empty();
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
