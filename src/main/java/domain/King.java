package domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class King extends Piece {
	private static final int[][] MOVE_DELTAS = {
		{1, 0},
		{1, 1},
		{0, 1},
		{-1, 1},
		{-1, 0},
		{-1, -1},
		{0, -1},
		{1, -1}
	};

	public King(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.KING;
	}

	@Override
	public Set<Square> candidateMoves(Square from, Board board) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(board);

		Set<Square> moves = new HashSet<>();
		for (int[] delta : MOVE_DELTAS) {
			Optional<Square> candidate = from.offset(delta[0], delta[1]);
			candidate.ifPresent(square -> addIfEmptyOrOpponent(board, moves, square));
		}
		return Collections.unmodifiableSet(moves);
	}

	private void addIfEmptyOrOpponent(Board board, Set<Square> moves, Square square) {
		Optional<Piece> occupant = board.pieceAt(square);
		if (occupant.isEmpty() || occupant.get().color() != color()) {
			moves.add(square);
		}
	}
}
