package domain;

public enum PieceType {
	PAWN,
	KNIGHT,
	BISHOP,
	ROOK,
	QUEEN,
	KING;

	public boolean isPromotionChoice() {
		return this == KNIGHT || this == BISHOP || this == ROOK || this == QUEEN;
	}
}
