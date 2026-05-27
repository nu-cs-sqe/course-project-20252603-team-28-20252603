package domain;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class ChessClock {
	private final TimeControl control;
	private final Clock clock;
	private final Map<Color, Duration> remaining;
	private Color running;
	private Instant lastTickAt;

	public ChessClock(TimeControl control, Clock clock) {
		Objects.requireNonNull(control);
		Objects.requireNonNull(clock);
		this.control = control;
		this.clock = clock;
		this.remaining = new EnumMap<>(Color.class);
		this.remaining.put(Color.WHITE, control.startingTime());
		this.remaining.put(Color.BLACK, control.startingTime());
	}

	public Duration remaining(Color color) {
		return remaining.get(color);
	}

	public Color running() {
		return running;
	}

	public void start(Color active) {
		Objects.requireNonNull(active);
		this.running = active;
		this.lastTickAt = clock.instant();
	}

	public void tick() {
		if (running == null) {
			return;
		}
		Instant now = clock.instant();
		Duration elapsed = Duration.between(lastTickAt, now);
		Duration next = remaining.get(running).minus(elapsed);
		if (next.isNegative()) {
			next = Duration.ZERO;
		}
		remaining.put(running, next);
		lastTickAt = now;
	}

	public void completeTurn(Color moved, Color next) {
		Objects.requireNonNull(moved);
		Objects.requireNonNull(next);
		if (moved == next) {
			throw new IllegalArgumentException("moved and next must differ");
		}
		Duration current = remaining.get(moved);
		remaining.put(moved, current.plus(control.increment()));
		this.running = next;
	}

}
