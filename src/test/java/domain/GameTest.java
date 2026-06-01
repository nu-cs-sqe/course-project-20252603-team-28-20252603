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

	@Test
	public void knightAttacksKingViaLMove() {
		Board board = new Board();
		board.place(Square.of(4, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(4, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(3, 2), Piece.of(PieceType.KNIGHT, Color.BLACK));
		Game game = new Game(board);

		Assertions.assertTrue(game.isInCheck(Color.WHITE));
		Assertions.assertFalse(game.isInCheck(Color.BLACK));
	}

	@Test
	public void isInCheckNullColorThrows() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertThrows(NullPointerException.class, () -> game.isInCheck(null));
	}

	@Test
	public void standardSetupNeitherSideInCheckmate() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertFalse(game.isCheckmate(Color.WHITE));
		Assertions.assertFalse(game.isCheckmate(Color.BLACK));
	}

	@Test
	public void backRankCheckmateAgainstWhiteKing() {
		Board board = new Board();
		board.place(Square.of(4, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(4, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(3, 0), Piece.of(PieceType.BISHOP, Color.WHITE));
		board.place(Square.of(3, 1), Piece.of(PieceType.BISHOP, Color.WHITE));
		board.place(Square.of(4, 1), Piece.of(PieceType.BISHOP, Color.WHITE));
		board.place(Square.of(5, 0), Piece.of(PieceType.BISHOP, Color.WHITE));
		board.place(Square.of(7, 3), Piece.of(PieceType.BISHOP, Color.BLACK));
		Game game = new Game(board);

		Assertions.assertTrue(game.isCheckmate(Color.WHITE));
	}

	@Test
	public void inCheckWithEscapeIsNotCheckmate() {
		Board board = new Board();
		board.place(Square.of(4, 4), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(4, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(7, 7), Piece.of(PieceType.BISHOP, Color.BLACK));
		board.place(Square.of(5, 4), Piece.of(PieceType.KNIGHT, Color.WHITE));
		Game game = new Game(board);

		Assertions.assertFalse(game.isCheckmate(Color.WHITE));
	}
}
