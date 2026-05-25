package domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Knight extends Piece {

	private static final int[][] MOVE_DELTAS = {
		{1, 2},
		{1, -2},
		{-1, 2},
		{-1, -2},
		{2, 1},
		{2, -1},
		{-2, 1},
		{-2, -1}
	};

	public Knight(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.KNIGHT;
	}

	public Set<Square> moveCandidates(Square from) {
		Objects.requireNonNull(from);

		Set<Square> candidates = new HashSet<>();
		for (int[] delta : MOVE_DELTAS) {
			from.offset(delta[0], delta[1]).ifPresent(candidates::add);
		}
		return Collections.unmodifiableSet(candidates);
	}
}
