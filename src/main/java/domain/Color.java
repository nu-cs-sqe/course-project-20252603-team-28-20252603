package domain;

public enum Color {
	WHITE,
	BLACK;

	public Color opposite() {
		if (this == WHITE) {
			return BLACK;
		}
		return WHITE;
	}
}
