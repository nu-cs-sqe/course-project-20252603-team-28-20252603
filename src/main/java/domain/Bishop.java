package domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Bishop extends Piece {
	private static final int FIRST_STEP = 1;
	private static final int[][] MOVE_DIRECTIONS = {
		{1, 1},
		{1, -1},
		{-1, 1},
		{-1, -1}
	};

	public Bishop(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.BISHOP;
	}

	@Override
	public Set<Square> candidateMoves(Square from, Board board) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(board);

		Set<Square> moves = new HashSet<>();
		for (int[] direction : MOVE_DIRECTIONS) {
			addRayMoves(from, moves, direction[0], direction[1]);
		}
		return Collections.unmodifiableSet(moves);
	}

	private void addRayMoves(
			Square from,
			Set<Square> moves,
			int fileDelta,
			int rankDelta) {
		Optional<Square> candidate = nextDiagonalSquare(from, fileDelta, rankDelta);
		while (candidate.isPresent()) {
			Square square = candidate.get();
			Optional<Piece> occupant = board.pieceAt(square);
			if (occupant.isPresent()) {
				return;
			}
			moves.add(square);
			candidate = nextDiagonalSquare(square, fileDelta, rankDelta);
		}
	}

	private Optional<Square> nextDiagonalSquare(
			Square square,
			int fileDelta,
			int rankDelta) {
		return square.offset(fileDelta * FIRST_STEP, rankDelta * FIRST_STEP);
	}
}
