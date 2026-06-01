package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

	@Test
	public void newGameStartsWithWhiteToMove() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertEquals(Color.WHITE, game.currentTurn());
	}

	@Test
	public void nullBoardThrows() {
		Assertions.assertThrows(NullPointerException.class, () -> new Game(null));
	}

	@Test
	public void standardSetupNeitherKingInCheck() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertFalse(game.isInCheck(Color.WHITE));
		Assertions.assertFalse(game.isInCheck(Color.BLACK));
	}

	@Test
	public void bishopAttacksKingOnDiagonal() {
		Board board = new Board();
		board.place(Square.of(4, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(4, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(7, 3), Piece.of(PieceType.BISHOP, Color.BLACK));
		Game game = new Game(board);

		Assertions.assertTrue(game.isInCheck(Color.WHITE));
		Assertions.assertFalse(game.isInCheck(Color.BLACK));
	}
}
