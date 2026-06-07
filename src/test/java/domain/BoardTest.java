package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

public class BoardTest {
	private static final Square WHITE_KNIGHT_START = Square.of(1, 0);
	private static final Square WHITE_KNIGHT_TARGET = Square.of(2, 2);

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
		Piece knight = getMockedWhiteKnight();

		board.place(WHITE_KNIGHT_START, knight);

		Assertions.assertEquals(Optional.of(knight), board.pieceAt(WHITE_KNIGHT_START));
	}

	@Test
	public void placePieceWithNullSquareExpectException() {
		Board board = new Board();
		Piece knight = getMockedWhiteKnight();

		Assertions.assertThrows(
				NullPointerException.class,
				() -> board.place(null, knight)
		);
	}

	@Test
	public void placeNullPieceExpectException() {
		Board board = new Board();

		Assertions.assertThrows(
				NullPointerException.class,
				() -> board.place(WHITE_KNIGHT_START, null)
		);
	}

	@Test
	public void removeOccupiedSquareExpectPieceAndSquareBecomesEmpty() {
		Board board = new Board();
		Piece knight = getMockedWhiteKnight();
		board.place(WHITE_KNIGHT_START, knight);

		Optional<Piece> removedPiece = board.remove(WHITE_KNIGHT_START);

		Assertions.assertEquals(Optional.of(knight), removedPiece);
		Assertions.assertTrue(board.pieceAt(WHITE_KNIGHT_START).isEmpty());
	}

	@Test
	public void removeEmptySquareExpectEmptyOptional() {
		Board board = new Board();

		Optional<Piece> removedPiece = board.remove(WHITE_KNIGHT_START);

		Assertions.assertTrue(removedPiece.isEmpty());
	}

	@Test
	public void movePieceToEmptySquareExpectPieceAtDestination() {
		Board board = new Board();
		Piece knight = getMockedWhiteKnight();
		board.place(WHITE_KNIGHT_START, knight);

		board.move(WHITE_KNIGHT_START, WHITE_KNIGHT_TARGET);

		Assertions.assertTrue(board.pieceAt(WHITE_KNIGHT_START).isEmpty());
		Assertions.assertEquals(Optional.of(knight), board.pieceAt(WHITE_KNIGHT_TARGET));
	}

	@Test
	public void movePieceToOccupiedSquareExpectCaptureAtDestination() {
		Board board = new Board();
		Piece mockedWhiteKnight = getMockedWhiteKnight();
		board.place(WHITE_KNIGHT_START, mockedWhiteKnight);
		board.place(WHITE_KNIGHT_TARGET, getMockedBlackKnight());

		board.move(WHITE_KNIGHT_START, WHITE_KNIGHT_TARGET);

		Assertions.assertTrue(board.pieceAt(WHITE_KNIGHT_START).isEmpty());
		Assertions.assertEquals(
				Optional.of(mockedWhiteKnight), board.pieceAt(WHITE_KNIGHT_TARGET));
	}

	@Test
	public void moveFromEmptySquareExpectException() {
		Board board = new Board();

		Assertions.assertThrows(
				IllegalStateException.class,
				() -> board.move(WHITE_KNIGHT_START, WHITE_KNIGHT_TARGET)
		);
	}

	@Test
	public void occupiedSquaresOfColorExpectOnlyRequestedColor() {
		Board board = new Board();
		board.place(WHITE_KNIGHT_START, getMockedWhiteKnight());
		board.place(WHITE_KNIGHT_TARGET, getMockedBlackKnight());

		Set<Square> whiteSquares = board.occupiedSquaresOf(Color.WHITE);
		Set<Square> blackSquares = board.occupiedSquaresOf(Color.BLACK);

		Assertions.assertEquals(Set.of(WHITE_KNIGHT_START), whiteSquares);
		Assertions.assertEquals(Set.of(WHITE_KNIGHT_TARGET), blackSquares);
	}

	@Test
	public void copyBoardThenChangeOriginalExpectCopyUnchanged() {
		Board originalBoard = new Board();
		Piece mockedWhiteKnight = getMockedWhiteKnight();
		originalBoard.place(WHITE_KNIGHT_START, mockedWhiteKnight);

		Board copiedBoard = originalBoard.copy();
		originalBoard.remove(WHITE_KNIGHT_START);

		Assertions.assertEquals(
				Optional.of(mockedWhiteKnight),
				copiedBoard.pieceAt(WHITE_KNIGHT_START)
		);
	}

	@Test
	public void findKingInStandardSetupExpectKingSquares() {
		Board board = Board.standardSetup();

		Assertions.assertEquals(Square.of(4, 0), board.findKing(Color.WHITE));
		Assertions.assertEquals(Square.of(4, 7), board.findKing(Color.BLACK));
	}

	@Test
	public void findKingOnEmptyBoardExpectException() {
		Board board = new Board();

		Assertions.assertThrows(
				IllegalStateException.class,
				() -> board.findKing(Color.WHITE)
		);
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

	private Piece getMockedWhiteKnight() {
		Piece mockedKnight = EasyMock.createMock(Knight.class);
		EasyMock.expect(mockedKnight.color()).andStubReturn(Color.WHITE);
		EasyMock.expect(mockedKnight.type()).andStubReturn(PieceType.KNIGHT);
		EasyMock.replay(mockedKnight);
		return mockedKnight;
	}

	private Piece getMockedBlackKnight() {
		Piece mockedKnight = EasyMock.createMock(Knight.class);
		EasyMock.expect(mockedKnight.color()).andStubReturn(Color.BLACK);
		EasyMock.expect(mockedKnight.type()).andStubReturn(PieceType.KNIGHT);
		EasyMock.replay(mockedKnight);
		return mockedKnight;
	}

	private void assertPiece(Board board, Square square, PieceType type, Color color) {
		Optional<Piece> actualPiece = board.pieceAt(square);

		Assertions.assertTrue(actualPiece.isPresent());
		Assertions.assertEquals(type, actualPiece.get().type());
		Assertions.assertEquals(color, actualPiece.get().color());
	}
}
