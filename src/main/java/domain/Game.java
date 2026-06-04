package domain;

import java.util.Objects;

public final class Game {
	private final Board board;
	private Color currentTurn;
	private GameStatus status;

	public Game(Board board) {
		Objects.requireNonNull(board);
		this.board = board;
		this.currentTurn = Color.WHITE;
		this.status = GameStatus.IN_PROGRESS;
	}

	public Color currentTurn() {
		return currentTurn;
	}

	Board board() {
		return board;
	}

	public boolean isInCheck(Color color) {
		Objects.requireNonNull(color);
		Square kingSquare = board.findKing(color);
		Color opponent = color.opposite();
		for (Square square : board.occupiedSquaresOf(opponent)) {
			Piece piece = board.pieceAt(square).orElseThrow();
			if (piece.candidateMoves(square, board).contains(kingSquare)) {
				return true;
			}
		}
		return false;
	}

	public boolean canPromote(Square square) {
		return board.pieceAt(square)
			.filter(p -> p.type() == PieceType.PAWN)
			.map(p -> (p.color() == Color.WHITE && square.rank() == 7)
				|| (p.color() == Color.BLACK && square.rank() == 0))
			.orElse(false);
	}

	public void promote(Square square, PieceType newType) {
		if (!canPromote(square)) {
			throw new IllegalArgumentException("square does not hold a promotable pawn");
		}
		Piece piece = board.pieceAt(square).orElseThrow();
		board.place(square, Piece.of(newType, piece.color()));
	}

	public GameStatus getStatus() {
		return status;
	}

	public void resign(Color resigningColor) {
		Objects.requireNonNull(resigningColor);
		if (resigningColor == Color.WHITE) {
			this.status = GameStatus.BLACK_WIN;
		} else {
			this.status = GameStatus.WHITE_WIN;
		}
	}

	public void makeMove(Square from, Square to) {
		if (status != GameStatus.IN_PROGRESS) {
			throw new IllegalStateException("Game is not in progress");
		}
		Piece piece = board.pieceAt(from)
			.orElseThrow(() -> new IllegalStateException("No piece at source square"));
		if (piece.color() != currentTurn) {
			throw new IllegalStateException("Not your turn");
		}
		board.move(from, to);
		currentTurn = currentTurn.opposite();
	}
}
