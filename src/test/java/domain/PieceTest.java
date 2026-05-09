package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceTest {
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
}
