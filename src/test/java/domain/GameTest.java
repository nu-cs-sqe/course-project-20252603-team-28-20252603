package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

public class GameTest {

	// Tests for game.md
	@Test
	public void newGameStartsWithWhiteToMove() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);

		Assertions.assertEquals(Color.WHITE, game.currentTurn());
	}

	@Test
	public void nullBoardThrows() {
		Assertions.assertThrows(NullPointerException.class, () -> new Game(null));
	}

	@Test
	public void whiteMovesPieceAndTurnFlipsToBlack() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);
		Piece whiteKnightMock = getMockedPiece(Color.WHITE, PieceType.KNIGHT);

		EasyMock.expect(boardMock.pieceAt(Square.of(1, 0))).andReturn(
				Optional.of(whiteKnightMock));
		boardMock.move(Square.of(1, 0), Square.of(2, 2));
		EasyMock.expectLastCall().times(2);
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 2))).andReturn(
				Optional.of(whiteKnightMock)).times(4);
		// These stubs are generally irrelevant to the result, only to ensure
		// Checkmate and stalemate detection pass
		EasyMock.expect(boardMock.findKing(Color.BLACK)).andStubReturn(Square.of(4, 7));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.WHITE))
		        .andStubReturn(Set.of(Square.of(2, 2)));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK)).andStubReturn(Set.of());
		EasyMock.expect(whiteKnightMock.candidateMoves(Square.of(2, 2), boardMock))
		        .andStubReturn(Set.of());

		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andStubReturn(Square.of(4, 0));

		EasyMock.replay(boardMock, whiteKnightMock);

		game.makeMove(Square.of(1, 0), Square.of(2, 2));
		Assertions.assertEquals(Color.BLACK, game.currentTurn());
		Assertions.assertEquals(
				PieceType.KNIGHT,
				boardMock.pieceAt(Square.of(2, 2)).orElseThrow().type()
		);
		Assertions.assertEquals(
				Color.WHITE,
				boardMock.pieceAt(Square.of(2, 2)).orElseThrow().color()
		);
		EasyMock.verify(boardMock);
	}

	@Test
	public void movingFromAnEmptySquareThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 3)))
		        .andReturn(Optional.empty());
		EasyMock.replay(boardMock);
		Assertions.assertThrows(
				IllegalStateException.class,
				() -> game.makeMove(Square.of(4, 3), Square.of(4, 4))
		);
		EasyMock.verify(boardMock);
	}

	@Test
	public void movingOpponentsPieceThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);
		Piece opponentPieceMock = getMockedPiece(Color.BLACK, PieceType.KING);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 3)))
		        .andReturn(Optional.of(opponentPieceMock));
		EasyMock.replay(boardMock, opponentPieceMock);
		Assertions.assertThrows(
				IllegalStateException.class,
				() -> game.makeMove(Square.of(4, 3), Square.of(4, 4))
		);
		EasyMock.verify(boardMock);
	}

	@Test
	public void movingFromNullSquareThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);
		Assertions.assertThrows(
				NullPointerException.class,
				() -> game.makeMove(null, Square.of(4, 4))
		);
	}

	@Test
	public void movingToNullSquareThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);
		Assertions.assertThrows(
				NullPointerException.class,
				() -> game.makeMove(Square.of(4, 3), null)
		);
	}

	// Tests for game-status
	@Test
	public void newGameStartsInProgress() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);

		Assertions.assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
	}

	@Test
	public void whiteResignsAndBlackWins() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);

		game.resign(Color.WHITE);

		Assertions.assertEquals(GameStatus.BLACK_WIN, game.getStatus());
	}

	@Test
	public void blackResignsAndWhiteWins() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);

		game.resign(Color.BLACK);

		Assertions.assertEquals(GameStatus.WHITE_WIN, game.getStatus());
	}

	@Test
	public void resignNullColorThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);

		Assertions.assertThrows(NullPointerException.class, () -> game.resign(null));
	}

	@Test
	public void makeMoveAfterResignThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);
		Piece whiteKnightMock = getMockedPiece(Color.WHITE, PieceType.KNIGHT);
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 0))).andStubReturn(
				Optional.of(whiteKnightMock));
		EasyMock.replay(boardMock, whiteKnightMock);

		game.resign(Color.WHITE);
		Assertions.assertThrows(
				IllegalStateException.class,
				() -> game.makeMove(Square.of(1, 0), Square.of(2, 2))
		);
	}

	// Tests for in-check
	@Test
	public void bishopAttacksKingOnDiagonal() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKingMock = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece blackKingMock = getMockedPiece(Color.BLACK, PieceType.KING);
		Piece blackBishopMock = getMockedPiece(Color.BLACK, PieceType.BISHOP);
		// Stubs will be used because the mocking of each function is too convoluted
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andStubReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
		        .andStubReturn(Set.of(Square.of(4, 7), Square.of(7, 3)));
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
		        .andStubReturn(Optional.of(blackKingMock));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 3)))
		        .andStubReturn(Optional.of(blackBishopMock));

		EasyMock.expect(boardMock.findKing(Color.BLACK)).andStubReturn(Square.of(4, 7));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.WHITE))
		        .andStubReturn(Set.of(Square.of(4, 0)));
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
		        .andStubReturn(Optional.of(whiteKingMock));

		EasyMock.expect(blackBishopMock.candidateMoves(Square.of(7, 3), boardMock))
		        .andStubReturn(Set.of(Square.of(4, 0)));
		EasyMock.expect(whiteKingMock.candidateMoves(Square.of(4, 0), boardMock))
		        .andStubReturn(Set.of());
		EasyMock.expect(blackKingMock.candidateMoves(Square.of(4, 7), boardMock))
		        .andStubReturn(Set.of());

		Game game = new Game(boardMock);
		EasyMock.replay(boardMock, whiteKingMock, blackKingMock, blackBishopMock);
		Assertions.assertTrue(game.isInCheck(Color.WHITE));
		Assertions.assertFalse(game.isInCheck(Color.BLACK));
	}

	@Test
	public void isInCheckNullColorThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);

		Assertions.assertThrows(NullPointerException.class, () -> game.isInCheck(null));
	}

	// Tests for checkmate
	@Test
	public void backRankCheckmateAgainstWhiteKing() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKingMock = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece blackKingMock = getMockedPiece(Color.BLACK, PieceType.KING);
		Piece blackRookMock = getMockedPiece(Color.BLACK, PieceType.ROOK);

		// Stubs will be used because the mocking of each function is too convoluted
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(Square.of(4, 0), Square.of(5, 0));
		EasyMock.expectLastCall();
		boardMock.move(Square.of(4, 0), Square.of(3, 0));
		EasyMock.expectLastCall();
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(3, 0));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(5, 0));
		EasyMock.expect(boardMock.findKing(Color.BLACK)).andStubReturn(Square.of(4, 7));

		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
		        .andStubReturn(Set.of(Square.of(4, 7), Square.of(7, 0)));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.WHITE))
		        .andStubReturn(Set.of(Square.of(4, 0)));

		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
		        .andStubReturn(Optional.of(blackKingMock));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0)))
		        .andStubReturn(Optional.of(blackRookMock));
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
		        .andStubReturn(Optional.of(whiteKingMock));

		EasyMock.expect(blackRookMock.candidateMoves(Square.of(7, 0), boardMock))
		        .andStubReturn(Set.of(
						Square.of(4, 0),
				        Square.of(3, 0),
				        Square.of(5, 0)
				));
		EasyMock.expect(whiteKingMock.candidateMoves(Square.of(4, 0), boardMock))
		        .andStubReturn(Set.of(Square.of(3, 0), Square.of(5, 0)));
		EasyMock.expect(blackKingMock.candidateMoves(Square.of(4, 7), boardMock))
		        .andStubReturn(Set.of());

		Game game = new Game(boardMock);
		EasyMock.replay(boardMock, whiteKingMock, blackKingMock, blackRookMock);
		Assertions.assertTrue(game.isCheckmate(Color.WHITE));
	}

	@Test
	public void inCheckWithEscapeIsNotCheckmate() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKingMock = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece blackKingMock = getMockedPiece(Color.BLACK, PieceType.KING);
		Piece blackRookMock = getMockedPiece(Color.BLACK, PieceType.ROOK);

		// Stubs will be used because the mocking of each function is too convoluted
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(Square.of(4, 0), Square.of(5, 0));
		EasyMock.expectLastCall();
		boardMock.move(Square.of(4, 0), Square.of(3, 0));
		EasyMock.expectLastCall();
		boardMock.move(Square.of(4, 0), Square.of(3, 1));
		EasyMock.expectLastCall();
		boardMock.move(Square.of(4, 0), Square.of(4, 1));
		EasyMock.expectLastCall();
		boardMock.move(Square.of(4, 0), Square.of(5, 1));
		EasyMock.expectLastCall();
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(3, 0));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(5, 0));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(3, 1));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(4, 1));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(5, 1));
		EasyMock.expect(boardMock.findKing(Color.BLACK)).andStubReturn(Square.of(4, 7));

		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
		        .andStubReturn(Set.of(Square.of(4, 7), Square.of(7, 0)));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.WHITE))
		        .andStubReturn(Set.of(Square.of(4, 0)));

		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
		        .andStubReturn(Optional.of(blackKingMock));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0)))
		        .andStubReturn(Optional.of(blackRookMock));
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
		        .andStubReturn(Optional.of(whiteKingMock));

		EasyMock.expect(blackRookMock.candidateMoves(Square.of(7, 0), boardMock))
		        .andStubReturn(Set.of(
						Square.of(4, 0),
				        Square.of(3, 0),
				        Square.of(5, 0)
				));
		EasyMock.expect(whiteKingMock.candidateMoves(Square.of(4, 0), boardMock))
		        .andStubReturn(Set.of(
						Square.of(3, 0),
				        Square.of(5, 0),
				        Square.of(3, 1),
				        Square.of(4, 1),
				        Square.of(5, 1)
				));
		EasyMock.expect(blackKingMock.candidateMoves(Square.of(4, 7), boardMock))
		        .andStubReturn(Set.of());

		Game game = new Game(boardMock);
		EasyMock.replay(boardMock, whiteKingMock, blackKingMock, blackRookMock);
		Assertions.assertFalse(game.isCheckmate(Color.WHITE));
	}

	@Test
	public void isCheckmateNullColorThrows() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertThrows(NullPointerException.class, () -> game.isCheckmate(null));
	}

	// Tests for stalemate
	@Test
	public void stalematePositionWithBlockedKingNotInCheck() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKingMock = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece blackKingMock = getMockedPiece(Color.BLACK, PieceType.KING);
		Piece blackQueenMock = getMockedPiece(Color.BLACK, PieceType.QUEEN);

		// Stubs will be used because the mocking of each function is too convoluted
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(Square.of(0, 0), Square.of(0, 1));
		EasyMock.expectLastCall();
		boardMock.move(Square.of(0, 0), Square.of(1, 0));
		EasyMock.expectLastCall();
		boardMock.move(Square.of(0, 0), Square.of(1, 1));
		EasyMock.expectLastCall();
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(0, 0));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(0, 1));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(1, 0));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(1, 1));
		EasyMock.expect(boardMock.findKing(Color.BLACK)).andStubReturn(Square.of(4, 7));

		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
		        .andStubReturn(Set.of(Square.of(4, 7), Square.of(1, 2)));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.WHITE))
		        .andStubReturn(Set.of(Square.of(0, 0)));

		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
		        .andStubReturn(Optional.of(blackKingMock));
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 2)))
		        .andStubReturn(Optional.of(blackQueenMock));
		EasyMock.expect(boardMock.pieceAt(Square.of(0, 0)))
		        .andStubReturn(Optional.of(whiteKingMock));

		EasyMock.expect(blackQueenMock.candidateMoves(Square.of(1, 2), boardMock))
		        .andStubReturn(Set.of(
						Square.of(0, 1),
				        Square.of(1, 0),
				        Square.of(1, 1)
				));
		EasyMock.expect(whiteKingMock.candidateMoves(Square.of(0, 0), boardMock))
		        .andStubReturn(Set.of(
						Square.of(0, 1),
				        Square.of(1, 0),
				        Square.of(1, 1)
				));
		EasyMock.expect(blackKingMock.candidateMoves(Square.of(4, 7), boardMock))
		        .andStubReturn(Set.of());
		Game game = new Game(boardMock);

		EasyMock.replay(boardMock, whiteKingMock, blackKingMock, blackQueenMock);
		Assertions.assertTrue(game.isStalemate(Color.WHITE));
	}

	@Test
	public void isStalemateNullColorThrows() {
		Board board = EasyMock.createMock(Board.class);
		Game game = new Game(board);

		Assertions.assertThrows(NullPointerException.class, () -> game.isStalemate(null));
	}

	@Test
	public void newGameNullClockThrows() {
		Board boardMock = EasyMock.createMock(Board.class);

		Assertions.assertThrows(
				NullPointerException.class, () -> new Game(boardMock, null));
	}

	@Test
	public void canPromoteWhitePawnOnRank7() {
		Board boardMock = EasyMock.createMock(Board.class);
		Square target = Square.of(4, 7);
		Piece whitePawn = Piece.of(PieceType.PAWN, Color.WHITE);

		EasyMock.expect(boardMock.pieceAt(target)).andReturn(Optional.of(whitePawn));
		EasyMock.replay(boardMock);

		Game game = new Game(boardMock);

		Assertions.assertTrue(game.canPromote(target));
		EasyMock.verify(boardMock);
	}

	private Piece getMockedPiece(Color color, PieceType type) {
		Piece mockedPiece = EasyMock.createMock(Knight.class);
		EasyMock.expect(mockedPiece.color()).andStubReturn(color);
		EasyMock.expect(mockedPiece.type()).andStubReturn(type);
		return mockedPiece;
	}

	// tests for legal-move-enforcement
	@Test
	public void legalMovesFromEmptySquareReturnsEmptySet() {
		Board boardMock = EasyMock.createMock(Board.class);
		EasyMock.expect(boardMock.pieceAt(Square.of(3, 3)))
			.andReturn(Optional.empty());
		EasyMock.replay(boardMock);
		Game game = new Game(boardMock);

		Assertions.assertTrue(game.legalMovesFrom(Square.of(3, 3)).isEmpty());
		EasyMock.verify(boardMock);
	}

	@Test
	public void legalMovesFromNullSquareThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);

		Assertions.assertThrows(
			NullPointerException.class, () -> game.legalMovesFrom(null));
	}

	@Test
	public void legalMovesFromExcludesMoveThatExposesKing() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKnightMock = getMockedPiece(Color.WHITE, PieceType.KNIGHT);
		Piece blackRookMock = getMockedPiece(Color.BLACK, PieceType.ROOK);

		EasyMock.expect(boardMock.pieceAt(Square.of(1, 0)))
			.andReturn(Optional.of(whiteKnightMock));
		EasyMock.expect(whiteKnightMock.candidateMoves(Square.of(1, 0), boardMock))
			.andReturn(Set.of(Square.of(2, 2)));
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(Square.of(1, 0), Square.of(2, 2));
		EasyMock.expectLastCall();
		EasyMock.expect(boardMock.findKing(Color.WHITE))
			.andStubReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
			.andStubReturn(Set.of(Square.of(4, 7)));
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
			.andStubReturn(Optional.of(blackRookMock));
		EasyMock.expect(blackRookMock.candidateMoves(Square.of(4, 7), boardMock))
			.andStubReturn(Set.of(Square.of(4, 0)));

		EasyMock.replay(boardMock, whiteKnightMock, blackRookMock);
		Game game = new Game(boardMock);

		Assertions.assertTrue(game.legalMovesFrom(Square.of(1, 0)).isEmpty());
		EasyMock.verify(boardMock);
	}

	@Test
	public void legalMovesFromIncludesMoveThatKeepsKingSafe() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKnightMock = getMockedPiece(Color.WHITE, PieceType.KNIGHT);

		EasyMock.expect(boardMock.pieceAt(Square.of(1, 0)))
			.andReturn(Optional.of(whiteKnightMock));
		EasyMock.expect(whiteKnightMock.candidateMoves(Square.of(1, 0), boardMock))
			.andReturn(Set.of(Square.of(2, 2)));
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(Square.of(1, 0), Square.of(2, 2));
		EasyMock.expectLastCall();
		EasyMock.expect(boardMock.findKing(Color.WHITE))
			.andStubReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
			.andStubReturn(Set.of());

		EasyMock.replay(boardMock, whiteKnightMock);
		Game game = new Game(boardMock);

		Set<Square> legal = game.legalMovesFrom(Square.of(1, 0));
		Assertions.assertTrue(legal.contains(Square.of(2, 2)));
		EasyMock.verify(boardMock);
	}

	@Test
	public void makeMoveRejectsMoveLeavingOwnKingInCheck() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKnightMock = getMockedPiece(Color.WHITE, PieceType.KNIGHT);
		Piece blackRookMock = getMockedPiece(Color.BLACK, PieceType.ROOK);

		EasyMock.expect(boardMock.pieceAt(Square.of(1, 0)))
			.andReturn(Optional.of(whiteKnightMock));
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(Square.of(1, 0), Square.of(2, 2));
		EasyMock.expectLastCall();
		EasyMock.expect(boardMock.findKing(Color.WHITE))
			.andStubReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
			.andStubReturn(Set.of(Square.of(4, 7)));
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
			.andStubReturn(Optional.of(blackRookMock));
		EasyMock.expect(blackRookMock.candidateMoves(Square.of(4, 7), boardMock))
			.andStubReturn(Set.of(Square.of(4, 0)));

		EasyMock.replay(boardMock, whiteKnightMock, blackRookMock);
		Game game = new Game(boardMock);

		Assertions.assertThrows(
			IllegalStateException.class,
			() -> game.makeMove(Square.of(1, 0), Square.of(2, 2)));
		EasyMock.verify(boardMock);
	}

	@Test
	public void boardReturnsBackingBoard() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);

		Assertions.assertSame(boardMock, game.board());
	}

	@Test
	public void winnerByTimeoutWithoutClockIsEmpty() {
		Board boardMock = EasyMock.createMock(Board.class);
		Game game = new Game(boardMock);

		Assertions.assertTrue(game.winnerByTimeout().isEmpty());
	}

	@Test
	public void isStalemateWhenInCheckIsFalse() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece blackRookMock = getMockedPiece(Color.BLACK, PieceType.ROOK);

		EasyMock.expect(boardMock.findKing(Color.WHITE))
			.andStubReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
			.andStubReturn(Set.of(Square.of(4, 7)));
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
			.andStubReturn(Optional.of(blackRookMock));
		EasyMock.expect(blackRookMock.candidateMoves(Square.of(4, 7), boardMock))
			.andStubReturn(Set.of(Square.of(4, 0)));

		EasyMock.replay(boardMock, blackRookMock);
		Game game = new Game(boardMock);

		Assertions.assertFalse(game.isStalemate(Color.WHITE));
	}

	@Test
	public void cannotPromoteBlackPawnOffBackRank() {
		Board boardMock = EasyMock.createMock(Board.class);
		Square target = Square.of(3, 4);
		Piece blackPawn = Piece.of(PieceType.PAWN, Color.BLACK);

		EasyMock.expect(boardMock.pieceAt(target)).andReturn(Optional.of(blackPawn));
		EasyMock.replay(boardMock);

		Game game = new Game(boardMock);

		Assertions.assertFalse(game.canPromote(target));
		EasyMock.verify(boardMock);
	}

	@Test
	public void promoteToPawnThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		Square target = Square.of(4, 7);
		Piece whitePawn = Piece.of(PieceType.PAWN, Color.WHITE);

		EasyMock.expect(boardMock.pieceAt(target)).andStubReturn(Optional.of(whitePawn));
		EasyMock.replay(boardMock);

		Game game = new Game(boardMock);

		Assertions.assertThrows(
			IllegalArgumentException.class,
			() -> game.promote(target, PieceType.PAWN));
		EasyMock.verify(boardMock);
	}

	// tests for castling
	@Test
	public void whiteKingsideCastleAllowedFromClearBackRank() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece whiteRook = getMockedPiece(Color.WHITE, PieceType.ROOK);
		// Stubs will be used because the mocking of each function is too convoluted
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0)))
			.andStubReturn(Optional.of(whiteRook));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(6, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andStubReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK)).andStubReturn(Set.of());
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(Square.of(4, 0), Square.of(5, 0));
		EasyMock.expectLastCall().anyTimes();
		boardMock.move(Square.of(4, 0), Square.of(6, 0));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(boardMock, whiteKing, whiteRook);
		Game game = new Game(boardMock);
		Assertions.assertTrue(game.canCastle(Color.WHITE, CastlingSide.KINGSIDE));
	}

	@Test
	public void cannotCastleWhenKingNotOnHomeSquare() {
		Board boardMock = EasyMock.createMock(Board.class);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0))).andStubReturn(Optional.empty());
		EasyMock.replay(boardMock);
		Game game = new Game(boardMock);
		Assertions.assertFalse(game.canCastle(Color.WHITE, CastlingSide.KINGSIDE));
	}

	@Test
	public void cannotCastleWhenRookSquareEmpty() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0))).andStubReturn(Optional.empty());
		EasyMock.replay(boardMock, whiteKing);
		Game game = new Game(boardMock);
		Assertions.assertFalse(game.canCastle(Color.WHITE, CastlingSide.KINGSIDE));
	}

	@Test
	public void cannotCastleWhenWrongPieceTypeOnRookSquare() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece whiteBishop = getMockedPiece(Color.WHITE, PieceType.BISHOP);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0)))
			.andStubReturn(Optional.of(whiteBishop));
		EasyMock.replay(boardMock, whiteKing, whiteBishop);
		Game game = new Game(boardMock);
		Assertions.assertFalse(game.canCastle(Color.WHITE, CastlingSide.KINGSIDE));
	}

	@Test
	public void cannotCastleWhenRookIsWrongColor() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece blackRook = getMockedPiece(Color.BLACK, PieceType.ROOK);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0)))
			.andStubReturn(Optional.of(blackRook));
		EasyMock.replay(boardMock, whiteKing, blackRook);
		Game game = new Game(boardMock);
		Assertions.assertFalse(game.canCastle(Color.WHITE, CastlingSide.KINGSIDE));
	}

	@Test
	public void whiteQueensideCastleAllowedFromClearBackRank() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece whiteRook = getMockedPiece(Color.WHITE, PieceType.ROOK);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(0, 0)))
			.andStubReturn(Optional.of(whiteRook));
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(3, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andStubReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK)).andStubReturn(Set.of());
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(EasyMock.anyObject(Square.class), EasyMock.anyObject(Square.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(boardMock, whiteKing, whiteRook);
		Game game = new Game(boardMock);

		Assertions.assertTrue(game.canCastle(Color.WHITE, CastlingSide.QUEENSIDE));
	}

	@Test
	public void blackKingsideCastleAllowed() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece blackKing = getMockedPiece(Color.BLACK, PieceType.KING);
		Piece blackRook = getMockedPiece(Color.BLACK, PieceType.ROOK);
		// Stubs will be used because the mocking of each function is too convoluted
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
			.andStubReturn(Optional.of(blackKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 7)))
			.andStubReturn(Optional.of(blackRook));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 7))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(6, 7))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.findKing(Color.BLACK)).andStubReturn(Square.of(4, 7));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.WHITE)).andStubReturn(Set.of());
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(EasyMock.anyObject(Square.class), EasyMock.anyObject(Square.class));

		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(boardMock, blackKing, blackRook);

		Game game = new Game(boardMock);

		Assertions.assertTrue(game.canCastle(Color.BLACK, CastlingSide.KINGSIDE));
	}

	@Test
	public void cannotCastleKingsideWhenSquareBetweenOccupied() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece whiteRook = getMockedPiece(Color.WHITE, PieceType.ROOK);
		Piece blocker = getMockedPiece(Color.WHITE, PieceType.BISHOP);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0)))
			.andStubReturn(Optional.of(whiteRook));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 0)))
			.andStubReturn(Optional.of(blocker));
		EasyMock.expect(boardMock.pieceAt(Square.of(6, 0))).andStubReturn(Optional.empty());
		EasyMock.replay(boardMock, whiteKing, whiteRook, blocker);
		Game game = new Game(boardMock);
		Assertions.assertFalse(game.canCastle(Color.WHITE, CastlingSide.KINGSIDE));
	}

	@Test
	public void cannotCastleQueensideWhenBFileOccupied() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece whiteRook = getMockedPiece(Color.WHITE, PieceType.ROOK);
		Piece blocker = getMockedPiece(Color.WHITE, PieceType.KNIGHT);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(0, 0)))
			.andStubReturn(Optional.of(whiteRook));
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 0)))
			.andStubReturn(Optional.of(blocker));
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(3, 0))).andStubReturn(Optional.empty());
		EasyMock.replay(boardMock, whiteKing, whiteRook, blocker);
		Game game = new Game(boardMock);
		Assertions.assertFalse(game.canCastle(Color.WHITE, CastlingSide.QUEENSIDE));
	}

	@Test
	public void cannotCastleWhileInCheck() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece whiteRook = getMockedPiece(Color.WHITE, PieceType.ROOK);
		Piece blackRook = getMockedPiece(Color.BLACK, PieceType.ROOK);
		// Stubs will be used because the mocking of each function is too convoluted
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0)))
			.andStubReturn(Optional.of(whiteRook));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(6, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andStubReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
			.andStubReturn(Set.of(Square.of(4, 7)));
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
			.andStubReturn(Optional.of(blackRook));
		EasyMock.expect(blackRook.candidateMoves(Square.of(4, 7), boardMock))
			.andStubReturn(Set.of(Square.of(4, 0)));
		EasyMock.replay(boardMock, whiteKing, whiteRook, blackRook);
		Game game = new Game(boardMock);
		Assertions.assertFalse(game.canCastle(Color.WHITE, CastlingSide.KINGSIDE));
	}

	@Test
	public void cannotCastleThroughAttackedSquare() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece whiteRook = getMockedPiece(Color.WHITE, PieceType.ROOK);
		Piece blackRook = getMockedPiece(Color.BLACK, PieceType.ROOK);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0)))
			.andStubReturn(Optional.of(whiteRook));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(6, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andReturn(Square.of(5, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK))
			.andStubReturn(Set.of(Square.of(5, 7)));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 7)))
			.andStubReturn(Optional.of(blackRook));
		EasyMock.expect(blackRook.candidateMoves(Square.of(5, 7), boardMock))
			.andStubReturn(Set.of(Square.of(5, 0)));
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(Square.of(4, 0), Square.of(5, 0));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(boardMock, whiteKing, whiteRook, blackRook);

		Game game = new Game(boardMock);

		Assertions.assertFalse(game.canCastle(Color.WHITE, CastlingSide.KINGSIDE));
	}

	@Test
	public void canCastleNullColorThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		EasyMock.replay(boardMock);
		Game game = new Game(boardMock);
		Assertions.assertThrows(NullPointerException.class,
			() -> game.canCastle(null, CastlingSide.KINGSIDE));
	}

	@Test
	public void canCastleNullSideThrows() {
		Board boardMock = EasyMock.createMock(Board.class);
		EasyMock.replay(boardMock);
		Game game = new Game(boardMock);
		Assertions.assertThrows(NullPointerException.class,
			() -> game.canCastle(Color.WHITE, null));
	}

	@Test
	public void kingLegalMovesIncludeCastleSquaresWhenAllowed() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece whiteKing = getMockedPiece(Color.WHITE, PieceType.KING);
		Piece whiteRookK = getMockedPiece(Color.WHITE, PieceType.ROOK);
		Piece whiteRookQ = getMockedPiece(Color.WHITE, PieceType.ROOK);
		// Stubs will be used because the mocking of each function is too convoluted
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 0)))
			.andStubReturn(Optional.of(whiteKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 0)))
			.andStubReturn(Optional.of(whiteRookK));
		EasyMock.expect(boardMock.pieceAt(Square.of(0, 0)))
			.andStubReturn(Optional.of(whiteRookQ));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(6, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(3, 0))).andStubReturn(Optional.empty());
		EasyMock.expect(whiteKing.candidateMoves(Square.of(4, 0), boardMock))
			.andStubReturn(Set.of());
		EasyMock.expect(boardMock.findKing(Color.WHITE)).andStubReturn(Square.of(4, 0));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.BLACK)).andStubReturn(Set.of());
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(EasyMock.anyObject(Square.class), EasyMock.anyObject(Square.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(boardMock, whiteKing, whiteRookK, whiteRookQ);
		Game game = new Game(boardMock);
		Set<Square> moves = game.legalMovesFrom(Square.of(4, 0));
		Assertions.assertTrue(moves.contains(Square.of(6, 0)));
		Assertions.assertTrue(moves.contains(Square.of(2, 0)));
	}

	@Test
	public void blackKingLegalMovesIncludeCastleSquaresWhenAllowed() {
		Board boardMock = EasyMock.createMock(Board.class);
		Piece blackKing = getMockedPiece(Color.BLACK, PieceType.KING);
		Piece blackRookK = getMockedPiece(Color.BLACK, PieceType.ROOK);
		Piece blackRookQ = getMockedPiece(Color.BLACK, PieceType.ROOK);
		EasyMock.expect(boardMock.pieceAt(Square.of(4, 7)))
			.andStubReturn(Optional.of(blackKing));
		EasyMock.expect(boardMock.pieceAt(Square.of(7, 7)))
			.andStubReturn(Optional.of(blackRookK));
		EasyMock.expect(boardMock.pieceAt(Square.of(0, 7)))
			.andStubReturn(Optional.of(blackRookQ));
		EasyMock.expect(boardMock.pieceAt(Square.of(5, 7))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(6, 7))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(1, 7))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(2, 7))).andStubReturn(Optional.empty());
		EasyMock.expect(boardMock.pieceAt(Square.of(3, 7))).andStubReturn(Optional.empty());
		EasyMock.expect(blackKing.candidateMoves(Square.of(4, 7), boardMock))
			.andStubReturn(Set.of());
		EasyMock.expect(boardMock.findKing(Color.BLACK)).andStubReturn(Square.of(4, 7));
		EasyMock.expect(boardMock.occupiedSquaresOf(Color.WHITE)).andStubReturn(Set.of());
		EasyMock.expect(boardMock.copy()).andStubReturn(boardMock);
		boardMock.move(EasyMock.anyObject(Square.class), EasyMock.anyObject(Square.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(boardMock, blackKing, blackRookK, blackRookQ);
		Game game = new Game(boardMock);
		Set<Square> moves = game.legalMovesFrom(Square.of(4, 7));
		Assertions.assertTrue(moves.contains(Square.of(6, 7)));
		Assertions.assertTrue(moves.contains(Square.of(2, 7)));
	}
}
