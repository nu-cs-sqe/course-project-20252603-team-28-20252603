package domain;

import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
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
}