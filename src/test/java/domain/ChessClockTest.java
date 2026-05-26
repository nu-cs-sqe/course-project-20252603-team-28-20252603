package domain;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChessClockTest {

	@Test
	public void chessClockIsCreatedWithBothPlayersHavingStartingTime() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
		ChessClock chessClock = new ChessClock(control, clock);

		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}
}
