package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Duration;

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
}
