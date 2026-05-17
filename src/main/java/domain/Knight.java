package domain;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
	private static final int[][] L_SHAPE_OFFSETS = {
		{2, 1},
		{2, -1},
		{-2, 1},
		{-2, -1},
		{1, 2},
		{1, -2},
		{-1, 2},
		{-1, -2}
	};

	public Knight(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.KNIGHT;
	}

	public Set<Square> candidateMoves(Square from, Board board) {
		Set<Square> moves = new HashSet<>();
		for (int[] offset : L_SHAPE_OFFSETS) {
			from.offset(offset[0], offset[1]).ifPresent(moves::add);
		}
		return moves;
	}
}
