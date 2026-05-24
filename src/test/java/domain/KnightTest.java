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
	public void candidateMovesFromCenterExpectEightMoves() {
		Board board = new Board();
		Knight knight = new Knight(Color.WHITE);
		Square from = Square.of(3, 3);

		Set<Square> moves = knight.candidateMoves(from, board);

		Assertions.assertEquals(Set.of(
				Square.of(4, 5),
				Square.of(5, 4),
				Square.of(5, 2),
				Square.of(4, 1),
				Square.of(2, 1),
				Square.of(1, 2),
				Square.of(1, 4),
				Square.of(2, 5)), moves);
	}

	@Test
	public void candidateMovesWithFriendlyPieceExpectBlockedSquareExcluded() {
		Board board = new Board();
		Knight knight = new Knight(Color.WHITE);
		board.place(Square.of(4, 5), Piece.of(PieceType.PAWN, Color.WHITE));

		Set<Square> moves = knight.candidateMoves(Square.of(3, 3), board);

		Assertions.assertFalse(moves.contains(Square.of(4, 5)));
	}

	@Test
	public void candidateMovesWithOpponentPieceExpectCaptureSquareIncluded() {
		Board board = new Board();
		Knight knight = new Knight(Color.WHITE);
		board.place(Square.of(4, 5), Piece.of(PieceType.PAWN, Color.BLACK));

		Set<Square> moves = knight.candidateMoves(Square.of(3, 3), board);

		Assertions.assertTrue(moves.contains(Square.of(4, 5)));
	}

	@Test
	public void candidateMovesWithNullSourceExpectException() {
		Knight knight = new Knight(Color.WHITE);

		Assertions.assertThrows(
				NullPointerException.class,
				() -> knight.candidateMoves(null, new Board()));
	}

	@Test
	public void candidateMovesWithNullBoardExpectException() {
		Knight knight = new Knight(Color.WHITE);

		Assertions.assertThrows(
				NullPointerException.class,
				() -> knight.candidateMoves(Square.of(3, 3), null));
	}
}
