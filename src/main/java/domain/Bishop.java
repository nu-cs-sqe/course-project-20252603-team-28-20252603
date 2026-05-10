package domain;

public class Bishop extends Piece {
	public Bishop(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.BISHOP;
	}
}
