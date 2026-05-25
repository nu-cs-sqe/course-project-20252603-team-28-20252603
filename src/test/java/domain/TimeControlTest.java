package domain;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
