package domain;

import java.time.Duration;

public final class TimeControl {
	private final Duration startingTime;
	private final Duration increment;

	public TimeControl(Duration startingTime, Duration increment) {
		if (startingTime.isZero() || startingTime.isNegative()) {
			throw new IllegalArgumentException("startingTime must be positive");
		}
		if (increment.isNegative()) {
			throw new IllegalArgumentException("increment must not be negative");
		}
		this.startingTime = startingTime;
		this.increment = increment;
	}

	public Duration startingTime() {
		return startingTime;
	}

	public Duration increment() {
		return increment;
	}
}