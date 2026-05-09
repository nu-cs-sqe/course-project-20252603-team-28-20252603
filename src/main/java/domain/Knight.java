package domain;

public class Knight extends Piece {
	public Knight(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.KNIGHT;
	}
}
