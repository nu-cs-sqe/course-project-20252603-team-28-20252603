package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class TimeControlTest {

	@Test
	public void normalClockWithNoIncrementIsCreated() {
		TimeControl tc = new TimeControl(Duration.ofMinutes(5), Duration.ZERO);
		Assertions.assertEquals(Duration.ofMinutes(5), tc.startingTime());
		Assertions.assertEquals(Duration.ZERO, tc.increment());
	}

	@Test
	public void normalClockWithIncrementIsCreated() {
		TimeControl tc = new TimeControl(Duration.ofMinutes(5), Duration.ofSeconds(3));
		Assertions.assertEquals(Duration.ofMinutes(5), tc.startingTime());
		Assertions.assertEquals(Duration.ofSeconds(3), tc.increment());
	}

	@Test
	public void oneSecondStartingTimeIsCreated() {
		TimeControl tc = new TimeControl(Duration.ofSeconds(1), Duration.ZERO);
		Assertions.assertEquals(Duration.ofSeconds(1), tc.startingTime());
	}

	@Test
	public void zeroStartingTimeThrows() {
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new TimeControl(Duration.ZERO, Duration.ZERO));
	}

	@Test
	public void negativeStartingTimeThrows() {
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new TimeControl(Duration.ofSeconds(-1), Duration.ZERO));
	}

	@Test
	public void negativeIncrementThrows() {
		Assertions.assertThrows(IllegalArgumentException.class,
			() -> new TimeControl(Duration.ofMinutes(5), Duration.ofSeconds(-1)));
	}

	@Test
	public void nullStartingTimeThrows() {
		Assertions.assertThrows(NullPointerException.class,
			() -> new TimeControl(null, Duration.ZERO));
	}

	@Test
	public void nullIncrementThrows() {
		Assertions.assertThrows(NullPointerException.class,
			() -> new TimeControl(Duration.ofMinutes(5), null));
	}
}
