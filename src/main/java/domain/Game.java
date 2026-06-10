package domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import java.util.Objects;
import java.util.Optional;

public final class Game {
	private final Board board;
	private final ChessClock clock;
	private Color currentTurn;
	private GameStatus status;
	private LastMove lastMove;

	public Game(Board board) {
		Objects.requireNonNull(board);
		this.board = board;
		this.clock = null;
		this.currentTurn = Color.WHITE;
		this.status = GameStatus.IN_PROGRESS;
		this.lastMove = null;
	}

	public Game(Board board, ChessClock clock) {
		Objects.requireNonNull(board);
		Objects.requireNonNull(clock);
		this.board = board;
		this.clock = clock;
		this.currentTurn = Color.WHITE;
		this.status = GameStatus.IN_PROGRESS;
		this.lastMove = null;
		clock.start(Color.WHITE);
	}

	public Color currentTurn() {
		return currentTurn;
	}

	Board board() {
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

	public Set<Square> legalMovesFrom(Square from) {
		Objects.requireNonNull(from);
		Optional<Piece> piece = board.pieceAt(from);
		if (piece.isEmpty()) {
			return Collections.emptySet();
		}
		Color color = piece.get().color();
		Set<Square> moves = new HashSet<>();
		for (Square to : piece.get().candidateMoves(from, board)) {
			Board simulated = board.copy();
			simulated.move(from, to);
			if (!isInCheckOn(simulated, color)) {
				moves.add(to);
			}
		}
		addLegalEnPassantMoves(from, piece.get(), moves);
		return Collections.unmodifiableSet(moves);
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
		return playerHasNoLegalMoves(color);
	}

	public boolean isStalemate(Color color) {
		Objects.requireNonNull(color);
		if (isInCheck(color)) {
			return false;
		}
		return playerHasNoLegalMoves(color);
	}

	boolean playerHasNoLegalMoves(Color color) {
		Objects.requireNonNull(color);
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

	public boolean canPromote(Square square) {
		return board.pieceAt(square)
			.filter(p -> p.type() == PieceType.PAWN)
			.map(p -> (p.color() == Color.WHITE && square.rank() == 7)
				|| (p.color() == Color.BLACK && square.rank() == 0))
			.orElse(false);
	}

	public void promote(Square square, PieceType newType) {
		if (!canPromote(square)) {
			throw new IllegalArgumentException(
				"square does not hold a promotable pawn");
		}
		if (newType == PieceType.KING || newType == PieceType.PAWN) {
			throw new IllegalArgumentException("cannot promote to king or pawn");
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
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);
		Piece piece = board.pieceAt(from)
			.orElseThrow(() -> new IllegalStateException("No piece at source square"));
		if (piece.color() != currentTurn) {
			throw new IllegalStateException("Not your turn");
		}
		boolean enPassantMove = isEnPassantMove(from, to, piece);
		Board simulated = board.copy();
		if (enPassantMove) {
			applyEnPassant(simulated, from, to);
		} else {
			simulated.move(from, to);
		}
		if (isInCheckOn(simulated, currentTurn)) {
			throw new IllegalStateException("Move would leave own king in check");
		}
		if (enPassantMove) {
			applyEnPassant(board, from, to);
		} else {
			board.move(from, to);
		}
		lastMove = new LastMove(piece.type(), piece.color(), from, to);
		Color moved = currentTurn;
		currentTurn = currentTurn.opposite();
		if (clock != null) {
			clock.completeTurn(moved, currentTurn);
		}
		if (isCheckmate(currentTurn)) {
			status = currentTurn == Color.WHITE
				? GameStatus.BLACK_WIN
				: GameStatus.WHITE_WIN;
		} else if (isStalemate(currentTurn)) {
			status = GameStatus.STALEMATE;
		}
	}

	private void addLegalEnPassantMoves(Square from, Piece piece, Set<Square> moves) {
		if (piece.type() != PieceType.PAWN
				|| lastMove == null
				|| !isEnPassantAvailable(from, piece)) {
			return;
		}
		Square to = enPassantDestination(from, piece.color());
		Board simulated = board.copy();
		applyEnPassant(simulated, from, to);
		if (!isInCheckOn(simulated, piece.color())) {
			moves.add(to);
		}
	}

	private boolean isEnPassantAvailable(Square from, Piece piece) {
		return lastMove.type == PieceType.PAWN
				&& lastMove.color != piece.color()
				&& Math.abs(lastMove.to.rank() - lastMove.from.rank()) == 2
				&& lastMove.to.rank() == from.rank()
				&& Math.abs(lastMove.to.file() - from.file()) == 1;
	}

	private boolean isEnPassantMove(Square from, Square to, Piece piece) {
		return piece.type() == PieceType.PAWN
				&& lastMove != null
				&& board.pieceAt(to).isEmpty()
				&& isEnPassantAvailable(from, piece)
				&& to.equals(enPassantDestination(from, piece.color()));
	}

	private Square enPassantDestination(Square from, Color color) {
		int rankDelta = color == Color.WHITE ? 1 : -1;
		return Square.of(lastMove.to.file(), from.rank() + rankDelta);
	}

	private void applyEnPassant(Board targetBoard, Square from, Square to) {
		targetBoard.remove(Square.of(to.file(), from.rank()));
		targetBoard.move(from, to);
	}

	private static final class LastMove {
		private final PieceType type;
		private final Color color;
		private final Square from;
		private final Square to;

		private LastMove(PieceType type, Color color, Square from, Square to) {
			this.type = type;
			this.color = color;
			this.from = from;
			this.to = to;
		}
	}
}
