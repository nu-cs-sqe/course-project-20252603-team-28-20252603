package domain;

import java.util.Objects;
import java.util.Optional;

public final class Square {
	private static final int MIN_COORDINATE = 0;
	private static final int MAX_COORDINATE = 7;

	private final int file;
	private final int rank;

	public Square(int file, int rank) {
		if (!isOnBoard(file, rank)) {
			throw new IllegalArgumentException("Square must be on the chess board");
		}
		this.file = file;
		this.rank = rank;
	}

	public int file() {
		return file;
	}

	public int rank() {
		return rank;
	}

	public Optional<Square> offset(int fileDelta, int rankDelta) {
		int nextFile = file + fileDelta;
		int nextRank = rank + rankDelta;
		if (!isOnBoard(nextFile, nextRank)) {
			return Optional.empty();
		}
		return Optional.of(Square.of(nextFile, nextRank));
	}

	public static Square of(int file, int rank) {
		return new Square(file, rank);
	}

	private static boolean isOnBoard(int file, int rank) {
		return file >= MIN_COORDINATE
				&& file <= MAX_COORDINATE
				&& rank >= MIN_COORDINATE
				&& rank <= MAX_COORDINATE;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Square)) {
			return false;
		}
		Square square = (Square) other;
		return file == square.file && rank == square.rank;
	}

	@Override
	public int hashCode() {
		return Objects.hash(file, rank);
	}
}
