package domain;

import java.time.Duration;

public final class TimeControl {
	private final Duration startingTime;
	private final Duration increment;

	public TimeControl(Duration startingTime, Duration increment) {
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