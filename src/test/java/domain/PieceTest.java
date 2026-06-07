package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class PieceTest {

	// stub that inherits candidateMoves without overiding it so the base
	// methods null guards and default empty return actually run in the tests
	private static class StubPiece extends Piece {
		StubPiece(Color color) { super(color); }
		@Override public PieceType type() { return PieceType.PAWN; }
	}

	@Test
	public void createEachWhitePieceTypeExpectMatchingTypeAndColor() {
		for (PieceType type : PieceType.values()) {
			Piece piece = Piece.of(type, Color.WHITE);

			Assertions.assertEquals(type, piece.type());
			Assertions.assertEquals(Color.WHITE, piece.color());
		}
	}

	@Test
	public void createBlackKnightExpectKnightTypeAndBlackColor() {
		Piece piece = Piece.of(PieceType.KNIGHT, Color.BLACK);

		Assertions.assertTrue(piece instanceof Knight);
		Assertions.assertEquals(PieceType.KNIGHT, piece.type());
		Assertions.assertEquals(Color.BLACK, piece.color());
	}

	@Test
	public void createPieceWithNullTypeExpectException() {
		Assertions.assertThrows(
				NullPointerException.class,
				() -> Piece.of(null, Color.WHITE));
	}

	@Test
	public void createPieceWithNullColorExpectException() {
		Assertions.assertThrows(
				NullPointerException.class,
				() -> Piece.of(PieceType.PAWN, null));
	}

	@Test
	public void candidateMovesNullFromThrows() {
		Piece piece = new StubPiece(Color.WHITE);
		Board board = new Board();

		Assertions.assertThrows(NullPointerException.class,
			() -> piece.candidateMoves(null, board));
	}

	@Test
	public void candidateMovesNullBoardThrows() {
		Piece piece = new StubPiece(Color.WHITE);

		Assertions.assertThrows(NullPointerException.class,
			() -> piece.candidateMoves(Square.of(0, 0), null));
	}

	@Test
	public void candidateMovesDefaultReturnsEmpty() {
		Piece piece = new StubPiece(Color.WHITE);
		Board board = new Board();
		Set<Square> moves = piece.candidateMoves(Square.of(0, 0), board);

		Assertions.assertTrue(moves.isEmpty());
	}
}
