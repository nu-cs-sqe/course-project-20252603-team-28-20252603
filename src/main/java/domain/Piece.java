package domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public abstract class Piece {
	private static final Map<PieceType, Function<Color, Piece>> FACTORIES =
		new EnumMap<>(PieceType.class);

	static {
		FACTORIES.put(PieceType.PAWN, Pawn::new);
		FACTORIES.put(PieceType.KNIGHT, Knight::new);
		FACTORIES.put(PieceType.BISHOP, Bishop::new);
		FACTORIES.put(PieceType.ROOK, Rook::new);
		FACTORIES.put(PieceType.QUEEN, Queen::new);
		FACTORIES.put(PieceType.KING, King::new);
	}

	private final Color color;

	protected Piece(Color color) {
		this.color = Objects.requireNonNull(color);
	}

	public Color color() {
		return color;
	}

	public abstract PieceType type();

	public Set<Square> candidateMoves(Square from, Board board) {
		Objects.requireNonNull(from);
		Objects.requireNonNull(board);
		return Collections.emptySet();
	}

	public static Piece of(PieceType type, Color color) {
		Objects.requireNonNull(type);
		Objects.requireNonNull(color);
		return FACTORIES.get(type).apply(color);
	}
}
