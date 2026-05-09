package domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Board {
	private static final int BOARD_SIZE = 8;
	private static final int WHITE_BACK_RANK = 0;
	private static final int WHITE_PAWN_RANK = 1;
	private static final int BLACK_PAWN_RANK = 6;
	private static final int BLACK_BACK_RANK = 7;

	private final Map<Square, Piece> pieces;

	public Board() {
		pieces = new HashMap<>();
	}

	private Board(Map<Square, Piece> pieces) {
		this.pieces = new HashMap<>(pieces);
	}

	public Optional<Piece> pieceAt(Square square) {
		return Optional.ofNullable(pieces.get(Objects.requireNonNull(square)));
	}

	public void place(Square square, Piece piece) {
		pieces.put(Objects.requireNonNull(square), Objects.requireNonNull(piece));
	}

	public Optional<Piece> remove(Square square) {
		return Optional.ofNullable(pieces.remove(Objects.requireNonNull(square)));
	}

	public void move(Square from, Square to) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);

		Piece movingPiece = pieces.remove(from);
		if (movingPiece == null) {
			throw new IllegalStateException("No piece at source square");
		}
		pieces.put(to, movingPiece);
	}

	public Square findKing(Color color) {
		Objects.requireNonNull(color);

		for (Map.Entry<Square, Piece> entry : pieces.entrySet()) {
			Piece piece = entry.getValue();
			if (piece.color() == color && piece.type() == PieceType.KING) {
				return entry.getKey();
			}
		}
		throw new IllegalStateException("No king found for color: " + color);
	}

	public Set<Square> occupiedSquaresOf(Color color) {
		Objects.requireNonNull(color);

		Set<Square> occupiedSquares = new HashSet<>();
		for (Map.Entry<Square, Piece> entry : pieces.entrySet()) {
			if (entry.getValue().color() == color) {
				occupiedSquares.add(entry.getKey());
			}
		}
		return Collections.unmodifiableSet(occupiedSquares);
	}

	public Board copy() {
		return new Board(pieces);
	}

	public static Board standardSetup() {
		Board board = new Board();
		placeBackRank(board, Color.WHITE, WHITE_BACK_RANK);
		placePawnRank(board, Color.WHITE, WHITE_PAWN_RANK);
		placePawnRank(board, Color.BLACK, BLACK_PAWN_RANK);
		placeBackRank(board, Color.BLACK, BLACK_BACK_RANK);
		return board;
	}

	private static void placeBackRank(Board board, Color color, int rank) {
		board.place(Square.of(0, rank), Piece.of(PieceType.ROOK, color));
		board.place(Square.of(1, rank), Piece.of(PieceType.KNIGHT, color));
		board.place(Square.of(2, rank), Piece.of(PieceType.BISHOP, color));
		board.place(Square.of(3, rank), Piece.of(PieceType.QUEEN, color));
		board.place(Square.of(4, rank), Piece.of(PieceType.KING, color));
		board.place(Square.of(5, rank), Piece.of(PieceType.BISHOP, color));
		board.place(Square.of(6, rank), Piece.of(PieceType.KNIGHT, color));
		board.place(Square.of(7, rank), Piece.of(PieceType.ROOK, color));
	}

	private static void placePawnRank(Board board, Color color, int rank) {
		for (int file = 0; file < BOARD_SIZE; file++) {
			board.place(Square.of(file, rank), Piece.of(PieceType.PAWN, color));
		}
	}
}
