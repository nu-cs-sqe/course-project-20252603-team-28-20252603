package domain;

public class Queen extends Piece {
	public Queen(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.QUEEN;
	}
}
