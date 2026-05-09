package domain;

public class Rook extends Piece {
	public Rook(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.ROOK;
	}
}
