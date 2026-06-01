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

	public boolean isInCheck(Color color) {
		Objects.requireNonNull(color);
		return isInCheckOn(board, color);
	}

	public boolean isCheckmate(Color color) {
		Objects.requireNonNull(color);
		if (!isInCheck(color)) {
			return false;
		}
		for (Square from : board.occupiedSquaresOf(color)) {
			Piece piece = board.pieceAt(from).orElseThrow();
			for (Square to : piece.candidateMoves(from, board)) {
				Board copy = board.copy();
				copy.move(from, to);
				if (!isInCheckOn(copy, color)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isStalemate(Color color) {
		Objects.requireNonNull(color);
		if (isInCheck(color)) {
			return false;
		}
		for (Square from : board.occupiedSquaresOf(color)) {
			Piece piece = board.pieceAt(from).orElseThrow();
			for (Square to : piece.candidateMoves(from, board)) {
				Board copy = board.copy();
				copy.move(from, to);
				if (!isInCheckOn(copy, color)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isInCheckOn(Board boardSnapshot, Color color) {
		Square kingSquare = boardSnapshot.findKing(color);
		Color opponent = color.opposite();
		for (Square square : boardSnapshot.occupiedSquaresOf(opponent)) {
			Piece piece = boardSnapshot.pieceAt(square).orElseThrow();
			if (piece.candidateMoves(square, boardSnapshot).contains(kingSquare)) {
				return true;
			}
		}
		return false;
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
