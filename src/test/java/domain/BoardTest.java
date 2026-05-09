package domain;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
	private static final Square WHITE_KNIGHT_START = Square.of(1, 0);

	@Test
	public void newBoardExpectNoOccupiedSquares() {
		Board board = new Board();

		Assertions.assertTrue(board.occupiedSquaresOf(Color.WHITE).isEmpty());
		Assertions.assertTrue(board.occupiedSquaresOf(Color.BLACK).isEmpty());
	}

	@Test
	public void emptySquareExpectNoPiece() {
		Board board = new Board();

		Assertions.assertTrue(board.pieceAt(Square.of(0, 0)).isEmpty());
	}

	@Test
	public void placePieceOnEmptySquareExpectPieceAtSquare() {
		Board board = new Board();
		Piece knight = whiteKnight();

		board.place(WHITE_KNIGHT_START, knight);

		Assertions.assertEquals(Optional.of(knight), board.pieceAt(WHITE_KNIGHT_START));
	}

	@Test
	public void removeOccupiedSquareExpectPieceAndSquareBecomesEmpty() {
		Board board = new Board();
		Piece knight = whiteKnight();
		board.place(WHITE_KNIGHT_START, knight);

		Optional<Piece> removedPiece = board.remove(WHITE_KNIGHT_START);

		Assertions.assertEquals(Optional.of(knight), removedPiece);
		Assertions.assertTrue(board.pieceAt(WHITE_KNIGHT_START).isEmpty());
	}

	@Test
	public void standardSetupExpectThirtyTwoOccupiedSquares() {
		Board board = Board.standardSetup();

		Assertions.assertEquals(16, board.occupiedSquaresOf(Color.WHITE).size());
		Assertions.assertEquals(16, board.occupiedSquaresOf(Color.BLACK).size());
	}

	@Test
	public void standardSetupExpectCorrectBackRankPieces() {
		Board board = Board.standardSetup();

		assertPiece(board, Square.of(0, 0), PieceType.ROOK, Color.WHITE);
		assertPiece(board, Square.of(1, 0), PieceType.KNIGHT, Color.WHITE);
		assertPiece(board, Square.of(2, 0), PieceType.BISHOP, Color.WHITE);
		assertPiece(board, Square.of(3, 0), PieceType.QUEEN, Color.WHITE);
		assertPiece(board, Square.of(4, 0), PieceType.KING, Color.WHITE);
		assertPiece(board, Square.of(0, 7), PieceType.ROOK, Color.BLACK);
		assertPiece(board, Square.of(1, 7), PieceType.KNIGHT, Color.BLACK);
		assertPiece(board, Square.of(2, 7), PieceType.BISHOP, Color.BLACK);
		assertPiece(board, Square.of(3, 7), PieceType.QUEEN, Color.BLACK);
		assertPiece(board, Square.of(4, 7), PieceType.KING, Color.BLACK);
	}

	@Test
	public void standardSetupExpectCorrectPawnRanks() {
		Board board = Board.standardSetup();

		for (int file = 0; file < 8; file++) {
			assertPiece(board, Square.of(file, 1), PieceType.PAWN, Color.WHITE);
			assertPiece(board, Square.of(file, 6), PieceType.PAWN, Color.BLACK);
		}
	}

	private Piece whiteKnight() {
		return Piece.of(PieceType.KNIGHT, Color.WHITE);
	}

	private void assertPiece(Board board, Square square, PieceType type, Color color) {
		Optional<Piece> actualPiece = board.pieceAt(square);

		Assertions.assertTrue(actualPiece.isPresent());
		Assertions.assertEquals(type, actualPiece.get().type());
		Assertions.assertEquals(color, actualPiece.get().color());
	}
}
