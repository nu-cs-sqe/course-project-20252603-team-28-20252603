package domain;

import java.time.Clock;
import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;

public final class ChessClock {
	private final TimeControl control;
	private final Clock clock;
	private final Map<Color, Duration> remaining;

	public ChessClock(TimeControl control, Clock clock) {
		this.control = control;
		this.clock = clock;
		this.remaining = new EnumMap<>(Color.class);
		this.remaining.put(Color.WHITE, control.startingTime());
		this.remaining.put(Color.BLACK, control.startingTime());
	}

	public Duration remaining(Color color) {
		return remaining.get(color);
	}
}
