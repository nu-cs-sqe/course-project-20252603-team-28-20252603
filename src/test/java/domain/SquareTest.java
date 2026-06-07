package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class SquareTest {
	@Test
	public void createLowerLeftBoardSquareExpectCoordinates() {
		Square square = Square.of(0, 0);

		Assertions.assertEquals(0, square.file());
		Assertions.assertEquals(0, square.rank());
	}

	@Test
	public void createUpperRightBoardSquareExpectCoordinates() {
		Square square = Square.of(7, 7);

		Assertions.assertEquals(7, square.file());
		Assertions.assertEquals(7, square.rank());
	}

	@Test
	public void createSquareWithFileBelowBoardExpectException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> Square.of(-1, 0));
	}

	@Test
	public void createSquareWithRankAboveBoardExpectException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> Square.of(7, 8));
	}

	@Test
	public void offsetFromCenterStaysOnBoardExpectSquare() {
		Optional<Square> result = Square.of(3, 3).offset(2, 1);

		Assertions.assertEquals(Optional.of(Square.of(5, 4)), result);
	}

	@Test
	public void offsetFromEdgeLeavesBoardExpectEmptyOptional() {
		Optional<Square> result = Square.of(0, 0).offset(-1, 0);

		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	public void equalSquaresWithSameCoordinatesExpectSameHashCode() {
		Square firstSquare = Square.of(4, 4);
		Square secondSquare = Square.of(4, 4);

		Assertions.assertEquals(firstSquare, secondSquare);
		Assertions.assertEquals(firstSquare.hashCode(), secondSquare.hashCode());
	}

	@Test
	public void equalSquaresWithDifferentCoordinatesExpectNotEqual() {
		Square firstSquare = Square.of(4, 4);
		Square secondSquare = Square.of(4, 5);

		Assertions.assertNotEquals(firstSquare, secondSquare);
	}
}
