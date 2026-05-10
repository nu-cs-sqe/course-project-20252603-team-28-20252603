package domain;

import java.util.Objects;

public abstract class Piece {
	private final Color color;

	protected Piece(Color color) {
		this.color = Objects.requireNonNull(color);
	}

	public Color color() {
		return color;
	}

	public abstract PieceType type();

	public static Piece of(PieceType type, Color color) {
		Objects.requireNonNull(type);
		Objects.requireNonNull(color);

		switch (type) {
			case PAWN:
				return new Pawn(color);
			case KNIGHT:
				return new Knight(color);
			case BISHOP:
				return new Bishop(color);
			case ROOK:
				return new Rook(color);
			case QUEEN:
				return new Queen(color);
			case KING:
				return new King(color);
			default:
				throw new IllegalArgumentException(
						"Unsupported piece type: " + type);
		}
	}
}
