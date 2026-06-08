package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

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
	public void newGameWithClockStartsWithWhiteRunning() {
		Board board = Board.standardSetup();
		TimeControl tc = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		ChessClock chessClock = new ChessClock(tc, Clock.systemUTC());
		Game game = new Game(board, chessClock);

		Assertions.assertEquals(Color.WHITE, game.clock().running());
	}

	@Test
	public void newGameNullClockThrows() {
		Board board = Board.standardSetup();

		Assertions.assertThrows(NullPointerException.class, () -> new Game(board, null));
	}

	@Test
	public void clockNotExpiredReturnsEmpty() {
		Board board = Board.standardSetup();
		TimeControl tc = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		ChessClock chessClock = new ChessClock(tc, Clock.systemUTC());
		Game game = new Game(board, chessClock);

		Assertions.assertTrue(game.winnerByTimeout().isEmpty());
	}

	@Test
	public void whiteClockExpiredReturnsBlackAsWinner() {
		MutableClock mutableClock = new MutableClock(
			Instant.parse("2026-01-01T00:00:00Z"),
			ZoneOffset.UTC);
		TimeControl tc = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		ChessClock chessClock = new ChessClock(tc, mutableClock);
		Board board = Board.standardSetup();
		Game game = new Game(board, chessClock);

		mutableClock.advance(Duration.ofMinutes(6));

		Assertions.assertEquals(Optional.of(Color.BLACK), game.winnerByTimeout());
	}

	@Test
	public void blackClockExpiredReturnsWhiteAsWinner() {
		MutableClock mutableClock = new MutableClock(
			Instant.parse("2026-01-01T00:00:00Z"),
			ZoneOffset.UTC);
		TimeControl tc = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		ChessClock chessClock = new ChessClock(tc, mutableClock);
		Board board = Board.standardSetup();
		Game game = new Game(board, chessClock);

		chessClock.completeTurn(Color.WHITE, Color.BLACK);
		mutableClock.advance(Duration.ofMinutes(6));

		Assertions.assertEquals(Optional.of(Color.WHITE), game.winnerByTimeout());
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
	public void newGameStartsInProgress() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
	}

	@Test
	public void whiteResignsAndBlackWins() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		game.resign(Color.WHITE);

		Assertions.assertEquals(GameStatus.BLACK_WIN, game.getStatus());
	}

	@Test
	public void blackResignsAndWhiteWins() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		game.resign(Color.BLACK);

		Assertions.assertEquals(GameStatus.WHITE_WIN, game.getStatus());
	}

	@Test
	public void resignNullColorThrows() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertThrows(NullPointerException.class, () -> game.resign(null));
	}

	@Test
	public void makeMoveAfterResignThrows() {
		Board board = Board.standardSetup();
		Game game = new Game(board);
		game.resign(Color.WHITE);

		Assertions.assertThrows(IllegalStateException.class,
			() -> game.makeMove(Square.of(1, 0), Square.of(2, 2)));
	}

	@Test
	public void canPromoteWhitePawnOnRank7() {
		Board boardMock = EasyMock.createMock(Board.class);
		Square target = Square.of(4, 7);
		Piece whitePawn = Piece.of(PieceType.PAWN, Color.WHITE);

		EasyMock.expect(boardMock.pieceAt(target)).andReturn(Optional.of(whitePawn));
		EasyMock.replay(boardMock);

		Game game = new Game(boardMock);

		Assertions.assertTrue(game.canPromote(target));
		EasyMock.verify(boardMock);
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

	@Test
	public void isCheckmateNullColorThrows() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertThrows(NullPointerException.class, () -> game.isCheckmate(null));
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
		board.place(Square.of(1, 2), Piece.of(PieceType.BISHOP, Color.BLACK));
		board.place(Square.of(2, 1), Piece.of(PieceType.BISHOP, Color.BLACK));
		board.place(Square.of(3, 2), Piece.of(PieceType.KNIGHT, Color.BLACK));
		Game game = new Game(board);

		Assertions.assertTrue(game.isStalemate(Color.WHITE));
	}

	@Test
	public void isStalemateNullColorThrows() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertThrows(NullPointerException.class, () -> game.isStalemate(null));
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
	public void stalemateMoveUpdatesStatusToStalemate() {
		Board board = new Board();
		board.place(Square.of(0, 7), Piece.of(PieceType.KING, Color.BLACK));
		board.place(Square.of(2, 5), Piece.of(PieceType.KING, Color.WHITE));
		board.place(Square.of(1, 2), Piece.of(PieceType.QUEEN, Color.WHITE));
		Game game = new Game(board);

		game.makeMove(Square.of(1, 2), Square.of(1, 5));

		Assertions.assertEquals(GameStatus.STALEMATE, game.getStatus());
	}

	@Test
	public void makeMoveSwitchesClockToOpponent() {
		Board board = Board.standardSetup();
		TimeControl tc = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		ChessClock chessClock = new ChessClock(tc, Clock.systemUTC());
		Game game = new Game(board, chessClock);

		game.makeMove(Square.of(1, 0), Square.of(2, 2));

		Assertions.assertEquals(Color.BLACK, game.clock().running());
	}
}
