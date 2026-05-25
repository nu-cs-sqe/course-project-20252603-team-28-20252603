package domain;

import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KnightTest {
	@Test
	public void constructorWithWhiteColorExpectWhiteKnight() {
		Knight knight = new Knight(Color.WHITE);
		Color expectedColor = Color.WHITE;
		PieceType expectedType = PieceType.KNIGHT;
		Assertions.assertEquals(expectedColor, knight.color());
		Assertions.assertEquals(expectedType, knight.type());
	}

	@Test
	public void knightAtCenterReturnsEightMoveCandidates() {
		Knight knight = new Knight(Color.WHITE);
		Set<Square> candidates = knight.moveCandidates(Square.of(4, 4));

		Set<Square> expected = Set.of(
			Square.of(5, 6), Square.of(5, 2),
			Square.of(3, 6), Square.of(3, 2),
			Square.of(6, 5), Square.of(6, 3),
			Square.of(2, 5), Square.of(2, 3));
		Assertions.assertEquals(expected, candidates);
	}

	@Test
	public void knightAtCornerA1ReturnsTwoMoveCandidates() {
		Knight knight = new Knight(Color.WHITE);
		Set<Square> candidates = knight.moveCandidates(Square.of(0, 0));

		Set<Square> expected = Set.of(Square.of(1, 2), Square.of(2, 1));
		Assertions.assertEquals(expected, candidates);
	}

	@Test
	public void knightAtCornerH8ReturnsTwoMoveCandidates() {
		Knight knight = new Knight(Color.WHITE);
		Set<Square> candidates = knight.moveCandidates(Square.of(7, 7));

		Set<Square> expected = Set.of(Square.of(6, 5), Square.of(5, 6));
		Assertions.assertEquals(expected, candidates);
	}

	@Test
	public void knightAtEdgeAFileReturnsFourMoveCandidates() {
		Knight knight = new Knight(Color.WHITE);
		Set<Square> candidates = knight.moveCandidates(Square.of(0, 4));

		Set<Square> expected = Set.of(
			Square.of(1, 6), Square.of(1, 2),
			Square.of(2, 5), Square.of(2, 3));
		Assertions.assertEquals(expected, candidates);
	}


}
