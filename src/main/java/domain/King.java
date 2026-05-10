package domain;

public class King extends Piece {
	public King(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.KING;
	}
}
