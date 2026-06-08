package domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Queen extends Piece {
	private static final Direction[] MOVE_DIRECTIONS = {
		new Direction(1, 0),
		new Direction(1, 1),
		new Direction(0, 1),
		new Direction(-1, 1),
		new Direction(-1, 0),
		new Direction(-1, -1),
		new Direction(0, -1),
		new Direction(1, -1)
	};

	public Queen(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.QUEEN;
	}

	@Override
	public Set<Square> candidateMoves(Square from, Board board) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(board);

		Set<Square> moves = new HashSet<>();
		for (Direction direction : MOVE_DIRECTIONS) {
			addRayMoves(from, board, moves, direction);
		}
		return Collections.unmodifiableSet(moves);
	}

	private void addRayMoves(
		Square from,
		Board board,
		Set<Square> moves,
		Direction direction) {
		Optional<Square> candidate = direction.from(from);
		while (candidate.isPresent()) {
			Square square = candidate.get();
			Optional<Piece> occupant = board.pieceAt(square);
			if (occupant.isPresent()) {
				if (occupant.get().color() != color()) {
					moves.add(square);
				}
				return;
			}
			moves.add(square);
			candidate = direction.from(square);
		}
	}
}
