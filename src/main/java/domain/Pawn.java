package domain;

public class Pawn extends Piece {
	public Pawn(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.PAWN;
	}
}
