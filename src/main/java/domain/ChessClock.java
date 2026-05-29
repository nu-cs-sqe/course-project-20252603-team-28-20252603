package domain;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class ChessClock {
	private final TimeControl timeControl;
	private final Clock clock;
	private final Map<Color, Duration> remainingTimeFor;
	private Color runningClockColor;
	private Instant lastTickAt;

	public ChessClock(TimeControl timeControl, Clock clock) {
		Objects.requireNonNull(timeControl);
		Objects.requireNonNull(clock);
		this.timeControl = timeControl;
		this.clock = clock;
		this.remainingTimeFor = new EnumMap<>(Color.class);
		this.remainingTimeFor.put(Color.WHITE, timeControl.startingTime());
		this.remainingTimeFor.put(Color.BLACK, timeControl.startingTime());
	}

	public Duration remaining(Color color) {
		Objects.requireNonNull(color);
		return remainingTimeFor.get(color);
	}

	public Color running() {
		return runningClockColor;
	}

	public void start(Color active) {
		Objects.requireNonNull(active);
		this.runningClockColor = active;
		this.lastTickAt = clock.instant();
	}

	public void tick() {
		if (runningClockColor == null) {
			return;
		}
		Instant now = clock.instant();
		Duration elapsed = Duration.between(lastTickAt, now);
		Duration next = remainingTimeFor.get(runningClockColor).minus(elapsed);
		if (next.isNegative()) {
			next = Duration.ZERO;
		}
		remainingTimeFor.put(runningClockColor, next);
		lastTickAt = now;
	}

	public void completeTurn(Color moved, Color next) {
		Objects.requireNonNull(moved);
		Objects.requireNonNull(next);
		if (moved == next) {
			throw new IllegalArgumentException("moved and next must differ");
		}
		Duration current = remainingTimeFor.get(moved);
		remainingTimeFor.put(moved, current.plus(timeControl.increment()));
		this.runningClockColor = next;
	}

	public void pause() {
		this.runningClockColor = null;

	}

	public boolean isExpired(Color color) {
		Objects.requireNonNull(color);
		return remainingTimeFor.get(color).isZero();
	}

}
