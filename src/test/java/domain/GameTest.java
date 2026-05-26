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
	public void whiteMovesKnightAndTurnFlipsToBlack() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		game.makeMove(Square.of(1, 0), Square.of(2, 2));

		Piece movedPiece = board.pieceAt(Square.of(2, 2)).orElseThrow();
		Assertions.assertEquals(PieceType.KNIGHT, movedPiece.type());
		Assertions.assertEquals(Color.WHITE, movedPiece.color());
		Assertions.assertTrue(board.pieceAt(Square.of(1, 0)).isEmpty());
		Assertions.assertEquals(Color.BLACK, game.currentTurn());
	}

	@Test
	public void movingFromEmptySquareThrows() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertThrows(IllegalStateException.class,
			() -> game.makeMove(Square.of(4, 3), Square.of(4, 4)));
	}
}
