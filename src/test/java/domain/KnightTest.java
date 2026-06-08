package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

public class KnightTest {
	@Test
	public void constructorWithWhiteColorExpectWhiteKnight() {
		Knight knight = new Knight(Color.WHITE);
		Color expectedColor = Color.WHITE;
		PieceType expectedType = PieceType.KNIGHT;
		Assertions.assertEquals(expectedColor, knight.color());
		Assertions.assertEquals(expectedType, knight.type());
	}

	@Test
	public void candidateMovesFromCenterExpectEightMoves() {
		Board boardMock = EasyMock.createMock(Board.class);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 5))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 4))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 2))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 1))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 1))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 2))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 4))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 5))).andReturn(Optional.empty());
		EasyMock.replay(boardMock);
		Knight knight = new Knight(Color.WHITE);
		Square from = Square.of(3, 3);

		Set<Square> moves = knight.candidateMoves(from, boardMock);

		Assertions.assertEquals(
				Set.of(
						Square.of(4, 5),
						Square.of(5, 4),
						Square.of(5, 2),
						Square.of(4, 1),
						Square.of(2, 1),
						Square.of(1, 2),
						Square.of(1, 4),
						Square.of(2, 5)
				), moves
		);
		EasyMock.verify(boardMock);
	}

	@Test
	public void candidateMovesWithFriendlyPieceExpectBlockedSquareExcluded() {
		Board boardMock = EasyMock.createMock(Board.class);
		Knight friendlyPiece = new Knight(Color.WHITE);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 5)))
		        .andReturn(Optional.of(friendlyPiece));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 4))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 2))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 1))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 1))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 2))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 4))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 5))).andReturn(Optional.empty());
		EasyMock.replay(boardMock);
		Knight knight = new Knight(Color.WHITE);

		Set<Square> moves = knight.candidateMoves(Square.of(3, 3), boardMock);

		Assertions.assertFalse(moves.contains(Square.of(4, 5)));
		EasyMock.verify(boardMock);
	}

	@Test
	public void candidateMovesWithOpponentPieceExpectCaptureSquareIncluded() {
		Board boardMock = EasyMock.createMock(Board.class);
		Knight opponentPiece = new Knight(Color.BLACK);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 5)))
		        .andReturn(Optional.of(opponentPiece));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 4))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 2))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 1))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 1))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 2))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 4))).andReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 5))).andReturn(Optional.empty());
		EasyMock.replay(boardMock);
		Knight knight = new Knight(Color.WHITE);

		Set<Square> moves = knight.candidateMoves(Square.of(3, 3), boardMock);

		Assertions.assertTrue(moves.contains(Square.of(4, 5)));
	}

	@Test
	public void candidateMovesWithNullSourceExpectException() {
		Knight knight = new Knight(Color.WHITE);
		Board boardMock = EasyMock.createMock(Board.class);

		Assertions.assertThrows(
				NullPointerException.class,
				() -> knight.candidateMoves(null, boardMock)
		);
	}

	@Test
	public void candidateMovesWithNullBoardExpectException() {
		Knight knight = new Knight(Color.WHITE);

		Assertions.assertThrows(
				NullPointerException.class,
				() -> knight.candidateMoves(Square.of(3, 3), null)
		);
	}
}
