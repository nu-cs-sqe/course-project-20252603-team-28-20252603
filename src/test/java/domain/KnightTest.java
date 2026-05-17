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

	@Test
	public void candidateMovesFromCornerA1ReturnsTwoSquares() {
		Knight knight = new Knight(Color.WHITE);
		Board board = new Board();
		Square a1 = Square.of(0, 0);
		board.place(a1, knight);

		Set<Square> moves = knight.candidateMoves(a1, board);

		Assertions.assertEquals(2, moves.size());
		Assertions.assertTrue(moves.contains(Square.of(1, 2)));
		Assertions.assertTrue(moves.contains(Square.of(2, 1)));
	}

	@Test
	public void candidateMovesFromEdgeA4ReturnsFourSquares() {
		Knight knight = new Knight(Color.WHITE);
		Board board = new Board();
		Square a4 = Square.of(0, 3);
		board.place(a4, knight);

		Set<Square> moves = knight.candidateMoves(a4, board);

		Assertions.assertEquals(4, moves.size());
		Assertions.assertTrue(moves.contains(Square.of(1, 1)));
		Assertions.assertTrue(moves.contains(Square.of(1, 5)));
		Assertions.assertTrue(moves.contains(Square.of(2, 2)));
		Assertions.assertTrue(moves.contains(Square.of(2, 4)));
	}

	@Test
	public void candidateMovesExcludesSquaresWithFriendlyPieces() {
		Knight knight = new Knight(Color.WHITE);
		Pawn friendlyPawn = new Pawn(Color.WHITE);
		Board board = new Board();
		Square d4 = Square.of(3, 3);
		Square e6 = Square.of(4, 5);
		board.place(d4, knight);
		board.place(e6, friendlyPawn);

		Set<Square> moves = knight.candidateMoves(d4, board);

		Assertions.assertEquals(7, moves.size());
		Assertions.assertFalse(moves.contains(e6));
	}
}
