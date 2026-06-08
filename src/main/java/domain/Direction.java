package domain;

import java.util.Objects;
import java.util.Optional;

// a single file/rank step a sliding piece travels along
final class Direction {
	private final int fileDelta;
	private final int rankDelta;

	Direction(int fileDelta, int rankDelta) {
		this.fileDelta = fileDelta;
		this.rankDelta = rankDelta;
	}

	// the next square one step along this direction, if still on the board
	Optional<Square> from(Square square) {
		Objects.requireNonNull(square);
		return square.offset(fileDelta, rankDelta);
	}
}
