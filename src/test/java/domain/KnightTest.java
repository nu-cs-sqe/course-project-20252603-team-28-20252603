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
	public void candidateMovesFromCenterOfEmptyBoardReturnsEightSquares() {
		Knight knight = new Knight(Color.WHITE);
		Board board = new Board();
		Square d4 = Square.of(3, 3);
		board.place(d4, knight);

		Set<Square> moves = knight.candidateMoves(d4, board);

		Assertions.assertEquals(8, moves.size());
	}
}
