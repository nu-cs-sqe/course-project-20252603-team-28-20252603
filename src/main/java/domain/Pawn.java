package domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pawn extends Piece {
	private static final int WHITE_FORWARD = 1;
	private static final int BLACK_FORWARD = -1;
	private static final int WHITE_START_RANK = 1;
	private static final int BLACK_START_RANK = 6;

	public Pawn(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.PAWN;
	}

	@Override
	public Set<Square> candidateMoves(Square from, Board board) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(board);

		Set<Square> moves = new HashSet<>();
		int forward = forwardDelta();
		from.offset(0, forward).ifPresent(moves::add);
		if (from.rank() == startRank()) {
			from.offset(0, forward * 2).ifPresent(moves::add);
		}
		return Collections.unmodifiableSet(moves);
	}

	private int forwardDelta() {
		if (color() == Color.WHITE) {
			return WHITE_FORWARD;
		}
		return BLACK_FORWARD;
	}

	private int startRank() {
		if (color() == Color.WHITE) {
			return WHITE_START_RANK;
		}
		return BLACK_START_RANK;
	}
}
