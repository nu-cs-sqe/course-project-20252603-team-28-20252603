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

	@Test
	public void nullTimeControlThrows() {
		Clock clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
		Assertions.assertThrows(NullPointerException.class,
			() -> new ChessClock(null, clock));
	}

	@Test
	public void nullClockThrows() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Assertions.assertThrows(NullPointerException.class,
			() -> new ChessClock(control, null));
	}

	@Test
	public void startWhiteSetsWhiteAsRunning() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
		ChessClock chessClock = new ChessClock(control, clock);

		chessClock.start(Color.WHITE);

		Assertions.assertEquals(Color.WHITE, chessClock.running());
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}

	@Test
	public void startBlackSetsBlackAsRunning() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
		ChessClock chessClock = new ChessClock(control, clock);

		chessClock.start(Color.BLACK);

		Assertions.assertEquals(Color.BLACK, chessClock.running());
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}

	@Test
	public void startWithNullActiveThrows() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
		ChessClock chessClock = new ChessClock(control, clock);

		Assertions.assertThrows(NullPointerException.class,
			() -> chessClock.start(null));
	}

	@Test
	public void tickWhenNotRunningKeepsStartingTime() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
		ChessClock chessClock = new ChessClock(control, clock);

		chessClock.tick();

		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}

	@Test
	public void tickWithZeroElapsedKeepsStartingTime() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Clock clock = Clock.fixed(Instant.EPOCH, ZoneOffset.UTC);
		ChessClock chessClock = new ChessClock(control, clock);

		chessClock.start(Color.WHITE);
		chessClock.tick();

		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}

	@Test
	public void tickWithOneSecondElapsedDeductsFromRunning() {
		TimeControl control = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		MutableClock clock = new MutableClock(Instant.EPOCH, ZoneOffset.UTC);
		ChessClock chessClock = new ChessClock(control, clock);

		chessClock.start(Color.WHITE);
		clock.advance(Duration.ofSeconds(1));
		chessClock.tick();

		Assertions.assertEquals(Duration.ofMinutes(5).minusSeconds(1), chessClock.remaining(Color.WHITE));
		Assertions.assertEquals(Duration.ofMinutes(5), chessClock.remaining(Color.BLACK));
	}
}
