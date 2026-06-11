package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameCheckAndCheckmateIntegrationTest {
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
	public void standardSetupNeitherSideInCheckmate() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertFalse(game.isCheckmate(Color.WHITE));
		Assertions.assertFalse(game.isCheckmate(Color.BLACK));
	}

	@Test
	public void backRankCheckmateAgainstWhiteKing() {
		Board board = new Board();
		board.place(Square.of(7, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(5, 1), Piece.of(PieceType.PAWN, Color.WHITE));
		board.place(Square.of(6, 1), Piece.of(PieceType.PAWN, Color.WHITE));
		board.place(Square.of(7, 1), Piece.of(PieceType.PAWN, Color.WHITE));
		board.place(Square.of(0, 0), Piece.of(PieceType.ROOK, Color.BLACK));
		board.place(Square.of(4, 4), Piece.of(PieceType.KING, Color.BLACK));
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

	@Test
	public void standardSetupNeitherSideInStalemate() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertFalse(game.isStalemate(Color.WHITE));
		Assertions.assertFalse(game.isStalemate(Color.BLACK));
	}

	@Test
	public void stalematePositionWithBlockedKingNotInCheck() {
		Board board = new Board();
		board.place(Square.of(0, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(7, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(1, 2), Piece.of(PieceType.QUEEN, Color.BLACK));
		Game game = new Game(board);

		Assertions.assertTrue(game.isStalemate(Color.WHITE));
	}

	@Test
	public void whiteKingCheckmatedCheckIfStalemateReturnsFalse() {
		Board board = new Board();
		board.place(Square.of(4, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(0, 0), Piece.of(PieceType.ROOK, Color.BLACK));
		board.place(Square.of(4, 2), Piece.of(PieceType.KING, Color.BLACK));
		Game game = new Game(board);

		Assertions.assertTrue(game.isCheckmate(Color.WHITE));
		Assertions.assertFalse(game.isStalemate(Color.WHITE));
	}

	@Test
	public void kingNotInCheckWithNoOtherLegalMoves() {
		Board board = new Board();
		board.place(Square.of(0, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(7, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(1, 2), Piece.of(PieceType.BISHOP, Color.BLACK));
		board.place(Square.of(2, 1), Piece.of(PieceType.BISHOP, Color.BLACK));
		board.place(Square.of(3, 2), Piece.of(PieceType.KNIGHT, Color.BLACK));
		Game game = new Game(board);

		Assertions.assertTrue(game.playerHasNoLegalMoves(Color.WHITE));
	}

	@Test
	public void standardSetupBelowFiftyMoveThreshold() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertFalse(game.isFiftyMoveRule());
	}

	@Test
	public void oneHundredHalfMovesWithoutPawnMoveOrCapture() {
		Game game = generate99HalfMoveGame();
		Assertions.assertFalse(game.isFiftyMoveRule());
		game.makeMove(Square.of(4, 4), Square.of(3, 6));
		Assertions.assertTrue(game.isFiftyMoveRule());
	}

	@Test
	public void oneHundredHalfMovesPawnMoveReset() {
		Game game = generate99HalfMoveGame();
		Assertions.assertFalse(game.isFiftyMoveRule());
		game.makeMove(Square.of(5, 5), Square.of(5, 4));
		Assertions.assertFalse(game.isFiftyMoveRule());
	}

	@Test
	public void oneHundredHalfMovesCaptureReset() {
		Game game = generate99HalfMoveGame();
		Assertions.assertFalse(game.isFiftyMoveRule());
		game.makeMove(Square.of(4, 4), Square.of(2, 3));
		Assertions.assertFalse(game.isFiftyMoveRule());
	}

	@Test
	public void kingNotInCheckWithOneLegalMove() {
		Board board = new Board();
		board.place(Square.of(0, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(7, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(1, 3), Piece.of(PieceType.QUEEN, Color.BLACK));
		Game game = new Game(board);

		Assertions.assertFalse(game.playerHasNoLegalMoves(Color.WHITE));
	}

	@Test
	public void backRankMateMoveUpdatesStatusToWhiteWin() {
		Board board = new Board();
		board.place(Square.of(7, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(5, 6), Piece.of(PieceType.PAWN, Color.BLACK));
		board.place(Square.of(6, 6), Piece.of(PieceType.PAWN, Color.BLACK));
		board.place(Square.of(7, 6), Piece.of(PieceType.PAWN, Color.BLACK));
		board.place(Square.of(0, 0), Piece.of(PieceType.ROOK, Color.WHITE));
		board.place(Square.of(4, 0), Piece.of(PieceType.KING, Color.WHITE));
		Game game = new Game(board);

		game.makeMove(Square.of(0, 0), Square.of(0, 7));

		Assertions.assertEquals(GameStatus.WHITE_WIN, game.getStatus());
	}

	@Test
	public void backRankMateMoveUpdatesStatusToBlackWin() {
		Board board = new Board();
		board.place(Square.of(7, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(5, 1), Piece.of(PieceType.PAWN, Color.WHITE));
		board.place(Square.of(6, 1), Piece.of(PieceType.PAWN, Color.WHITE));
		board.place(Square.of(7, 1), Piece.of(PieceType.PAWN, Color.WHITE));
		board.place(Square.of(0, 7), Piece.of(PieceType.ROOK, Color.BLACK));
		board.place(Square.of(4, 7), Piece.of(PieceType.KING, Color.BLACK));
		Game game = new Game(board);

		game.makeMove(Square.of(5, 1), Square.of(5, 2));
		game.makeMove(Square.of(0, 7), Square.of(0, 0));

		Assertions.assertEquals(GameStatus.BLACK_WIN, game.getStatus());
	}

	@Test
	public void stalemateMoveUpdatesStatusToStalemate() {
		Board board = new Board();
		board.place(Square.of(0, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(2, 5), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(1, 2), Piece.of(PieceType.QUEEN, Color.WHITE));
		Game game = new Game(board);

		game.makeMove(Square.of(1, 2), Square.of(1, 5));

		Assertions.assertEquals(GameStatus.STALEMATE, game.getStatus());
	}

	private Game generate99HalfMoveGame() {
		Board board = new Board();
		board.place(Square.of(0, 0), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(7, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(2, 3), Piece.of(PieceType.PAWN, Color.WHITE));
		board.place(Square.of(5, 5), Piece.of(PieceType.PAWN, Color.BLACK));
		board.place(Square.of(4, 4), Piece.of(PieceType.KNIGHT, Color.BLACK));
		Game game = new Game(board);
		int numberHalfMoves = 0;
		boolean breakFlag = false;
		for (int iteration = 0; iteration < 4; iteration++) {
			for (int i = 0; i < 7; i++) {
				game.makeMove(Square.of(0, i), Square.of(0, i + 1));
				numberHalfMoves++;
				if (numberHalfMoves == 99) {
					breakFlag = true;
					break;
				}
				game.makeMove(Square.of(7, 7 - i), Square.of(7, 7 - i - 1));
				numberHalfMoves++;
				if (numberHalfMoves == 99) {
					breakFlag = true;
					break;
				}
			}
			if (breakFlag) {
				break;
			}
			for (int i = 0; i < 7; i++) {
				game.makeMove(Square.of(i, 7), Square.of(i + 1, 7));
				numberHalfMoves++;
				if (numberHalfMoves == 99) {
					breakFlag = true;
					break;
				}
				game.makeMove(Square.of(7 - i, 0), Square.of(7 - i - 1, 0));
				numberHalfMoves++;
				if (numberHalfMoves == 99) {
					breakFlag = true;
					break;
				}
			}
			if (breakFlag) {
				break;
			}
			for (int i = 0; i < 7; i++) {
				game.makeMove(Square.of(7, 7 - i), Square.of(7, 7 - i - 1));
				numberHalfMoves++;
				if (numberHalfMoves == 99) {
					breakFlag = true;
					break;
				}
				game.makeMove(Square.of(0, i), Square.of(0, i + 1));
				numberHalfMoves++;
				if (numberHalfMoves == 99) {
					breakFlag = true;
					break;
				}
			}
			if (breakFlag) {
				break;
			}
			for (int i = 0; i < 7; i++) {
				game.makeMove(Square.of(7 - i, 0), Square.of(7 - i - 1, 0));
				numberHalfMoves++;
				if (numberHalfMoves == 99) {
					breakFlag = true;
					break;
				}
				game.makeMove(Square.of(i, 7), Square.of(i + 1, 7));
				numberHalfMoves++;
				if (numberHalfMoves == 99) {
					breakFlag = true;
					break;
				}
			}
			if (breakFlag) {
				break;
			}
		}
		return game;
	}
}
