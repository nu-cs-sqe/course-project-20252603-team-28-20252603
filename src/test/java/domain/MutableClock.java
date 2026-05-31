package domain;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

class MutableClock extends Clock {
	private Instant now;
	private final ZoneId zone;

	MutableClock(Instant now, ZoneId zone) {
		this.now = now;
		this.zone = zone;
	}

	@Override
	public Instant instant() {
		return now;
	}

	@Override
	public ZoneId getZone() {
		return zone;
	}

	@Override
	public Clock withZone(ZoneId zone) {
		return new MutableClock(now, zone);
	}

	void advance(Duration duration) {
		now = now.plus(duration);
	}
}
