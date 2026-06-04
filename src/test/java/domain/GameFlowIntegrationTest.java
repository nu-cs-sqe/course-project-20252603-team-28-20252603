package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameFlowIntegrationTest {

	@Test
	public void standardSetupGameStartsWithWhiteAndExpectedPieces() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertEquals(Color.WHITE, game.currentTurn());

		Assertions.assertEquals(PieceType.ROOK,
			board.pieceAt(Square.of(0, 0)).orElseThrow().type());
		Assertions.assertEquals(PieceType.KNIGHT,
			board.pieceAt(Square.of(1, 0)).orElseThrow().type());
		Assertions.assertEquals(PieceType.KING,
			board.pieceAt(Square.of(4, 0)).orElseThrow().type());
		Assertions.assertEquals(PieceType.KING,
			board.pieceAt(Square.of(4, 7)).orElseThrow().type());

		Assertions.assertEquals(Color.WHITE,
			board.pieceAt(Square.of(0, 0)).orElseThrow().color());
		Assertions.assertEquals(Color.BLACK,
			board.pieceAt(Square.of(0, 7)).orElseThrow().color());
	}

	@Test
	public void multiTurnSequenceAlternatesColorsAndUpdatesBoard() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		game.makeMove(Square.of(1, 0), Square.of(2, 2));
		Assertions.assertEquals(Color.BLACK, game.currentTurn());
		Assertions.assertEquals(PieceType.KNIGHT,
			board.pieceAt(Square.of(2, 2)).orElseThrow().type());
		Assertions.assertEquals(Color.WHITE,
			board.pieceAt(Square.of(2, 2)).orElseThrow().color());

		game.makeMove(Square.of(1, 7), Square.of(2, 5));
		Assertions.assertEquals(Color.WHITE, game.currentTurn());
		Assertions.assertEquals(Color.BLACK,
			board.pieceAt(Square.of(2, 5)).orElseThrow().color());

		game.makeMove(Square.of(4, 1), Square.of(4, 3));
		Assertions.assertEquals(Color.BLACK, game.currentTurn());
	}

	@Test
	public void illegalMoveAttemptsLeaveStateUnchanged() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertThrows(IllegalStateException.class,
			() -> game.makeMove(Square.of(4, 3), Square.of(4, 4)));
		Assertions.assertEquals(Color.WHITE, game.currentTurn());

		Assertions.assertThrows(IllegalStateException.class,
			() -> game.makeMove(Square.of(1, 7), Square.of(2, 5)));
		Assertions.assertEquals(Color.WHITE, game.currentTurn());

		game.makeMove(Square.of(1, 0), Square.of(2, 2));
		Assertions.assertEquals(Color.BLACK, game.currentTurn());
	}

	@Test
	public void promoteWhitePawnToQueen() {
		Board board = new Board();
		board.place(Square.of(4, 7), Piece.of(PieceType.PAWN, Color.WHITE));
		Game game = new Game(board);

		game.promote(Square.of(4, 7), PieceType.QUEEN);

		Piece result = board.pieceAt(Square.of(4, 7)).orElseThrow();
		Assertions.assertEquals(PieceType.QUEEN, result.type());
		Assertions.assertEquals(Color.WHITE, result.color());
	}

	@Test
	public void promoteBlackPawnToKnight() {
		Board board = new Board();
		board.place(Square.of(3, 0), Piece.of(PieceType.PAWN, Color.BLACK));
		Game game = new Game(board);

		game.promote(Square.of(3, 0), PieceType.KNIGHT);

		Piece result = board.pieceAt(Square.of(3, 0)).orElseThrow();
		Assertions.assertEquals(PieceType.KNIGHT, result.type());
		Assertions.assertEquals(Color.BLACK, result.color());
	}

	@Test
	public void promotePawnOffBackRankThrows() {
		Board board = new Board();
		board.place(Square.of(4, 5), Piece.of(PieceType.PAWN, Color.WHITE));
		Game game = new Game(board);

		Assertions.assertThrows(IllegalArgumentException.class,
			() -> game.promote(Square.of(4, 5), PieceType.QUEEN));
	}

	@Test
	public void promoteNonPawnThrows() {
		Board board = new Board();
		board.place(Square.of(4, 7), Piece.of(PieceType.ROOK, Color.WHITE));
		Game game = new Game(board);

		Assertions.assertThrows(IllegalArgumentException.class,
			() -> game.promote(Square.of(4, 7), PieceType.QUEEN));
	}
}

