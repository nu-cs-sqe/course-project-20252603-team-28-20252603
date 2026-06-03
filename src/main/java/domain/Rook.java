package domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Rook extends Piece {
	private static final int FIRST_STEP = 1;
	private static final int[][] MOVE_DIRECTIONS = {
		{1, 0},
		{0, 1},
		{-1, 0},
		{0, -1}
	};

	public Rook(Color color) {
		super(color);
	}

	@Override
	public PieceType type() {
		return PieceType.ROOK;
	}

	@Override
	public Set<Square> candidateMoves(Square from, Board board) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(board);

		Set<Square> moves = new HashSet<>();
		for (int[] direction : MOVE_DIRECTIONS) {
			addRayMoves(from, board, moves, direction[0], direction[1]);
		}
		return Collections.unmodifiableSet(moves);
	}

	private void addRayMoves(
			Square from,
			Board board,
			Set<Square> moves,
			int fileDelta,
			int rankDelta) {
		Optional<Square> candidate = nextSquare(from, fileDelta, rankDelta);
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
			candidate = nextSquare(square, fileDelta, rankDelta);
		}
	}

	private Optional<Square> nextSquare(
			Square square,
			int fileDelta,
			int rankDelta) {
		return square.offset(fileDelta * FIRST_STEP, rankDelta * FIRST_STEP);
	}
}
