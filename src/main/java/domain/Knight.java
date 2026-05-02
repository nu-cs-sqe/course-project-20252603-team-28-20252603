package domain;

import java.util.Objects;

public class Knight {
	private final Color color;

	public Knight(Color color) {
		this.color = Objects.requireNonNull(color);
	}

	public Color color() {
		return color;
	}

	public PieceType type() {
		return PieceType.KNIGHT;
	}
}
