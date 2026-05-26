package domain;

import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BishopTest {
	@Test
	public void constructorWithWhiteColorExpectWhiteBishop() {
		Bishop bishop = new Bishop(Color.WHITE);

		Assertions.assertEquals(Color.WHITE, bishop.color());
		Assertions.assertEquals(PieceType.BISHOP, bishop.type());
	}

	@Test
	public void candidateMovesFromCenterExpectAllDiagonals() {
		Bishop bishop = new Bishop(Color.WHITE);
		Board board = new Board();

		Set<Square> moves = bishop.candidateMoves(Square.of(3, 3), board);

		Assertions.assertEquals(Set.of(
				Square.of(0, 0),
				Square.of(1, 1),
				Square.of(2, 2),
				Square.of(4, 4),
				Square.of(5, 5),
				Square.of(6, 6),
				Square.of(7, 7),
				Square.of(0, 6),
				Square.of(1, 5),
				Square.of(2, 4),
				Square.of(4, 2),
				Square.of(5, 1),
				Square.of(6, 0)), moves);
	}
}
