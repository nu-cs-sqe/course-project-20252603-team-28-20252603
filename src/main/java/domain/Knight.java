package domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Knight extends Piece {

	// The 8 L-shape jumps a knight can make.
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

	// Returns every square the knight could land on from the starting square.
	public Set<Square> candidateMoves(Square from, Board board) {
		Set<Square> moves = new HashSet<>();
		for (int[] offset : L_SHAPE_OFFSETS) {
			Optional<Square> destination = from.offset(offset[0], offset[1]);
			if (destination.isPresent()
				&& !isFriendlyOccupied(destination.get(), board)) {
				moves.add(destination.get());
			}
		}
		return moves;
	}

	// Helper function that stops us from capturing our own pieces
	private boolean isFriendlyOccupied(Square square, Board board) {
		return board.pieceAt(square)
			.map(piece -> piece.color() == color())
			.orElse(false);
	}
}
