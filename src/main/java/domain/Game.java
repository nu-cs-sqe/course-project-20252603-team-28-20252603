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

	public Game(Board board) {
		Objects.requireNonNull(board);
		this.board = board;
		this.clock = null;
		this.currentTurn = Color.WHITE;
		this.status = GameStatus.IN_PROGRESS;
	}

	public Game(Board board, ChessClock clock) {
		Objects.requireNonNull(board);
		Objects.requireNonNull(clock);
		this.board = board;
		this.clock = clock;
		this.currentTurn = Color.WHITE;
		this.status = GameStatus.IN_PROGRESS;
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

	public boolean isInsufficientMaterial() {
		Set<Square> nonKingSquares = nonKingSquares();
		if (nonKingSquares.isEmpty()) {
			return true;
		}
		if (nonKingSquares.size() == 1) {
			PieceType type = board.pieceAt(nonKingSquares.iterator().next())
					.orElseThrow()
					.type();
			return type == PieceType.BISHOP || type == PieceType.KNIGHT;
		}
		return hasOnlySameColorBishops(nonKingSquares);
	}

	private Set<Square> nonKingSquares() {
		Set<Square> squares = new HashSet<>();
		for (Square square : occupiedSquares()) {
			Piece piece = board.pieceAt(square).orElseThrow();
			if (piece.type() != PieceType.KING) {
				squares.add(square);
			}
		}
		return squares;
	}

	private boolean hasOnlySameColorBishops(Set<Square> squares) {
		Boolean firstSquareColor = null;
		for (Square square : squares) {
			Piece piece = board.pieceAt(square).orElseThrow();
			if (piece.type() != PieceType.BISHOP) {
				return false;
			}
			boolean squareColor = isLightSquare(square);
			if (firstSquareColor == null) {
				firstSquareColor = squareColor;
			} else if (firstSquareColor != squareColor) {
				return false;
			}
		}
		return true;
	}

	private boolean isLightSquare(Square square) {
		return (square.file() + square.rank()) % 2 == 0;
	}

	private Set<Square> occupiedSquares() {
		Set<Square> occupied = new HashSet<>();
		occupied.addAll(board.occupiedSquaresOf(Color.WHITE));
		occupied.addAll(board.occupiedSquaresOf(Color.BLACK));
		return occupied;
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
		Board simulated = board.copy();
		simulated.move(from, to);
		if (isInCheckOn(simulated, currentTurn)) {
			throw new IllegalStateException("Move would leave own king in check");
		}
		board.move(from, to);
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
}
