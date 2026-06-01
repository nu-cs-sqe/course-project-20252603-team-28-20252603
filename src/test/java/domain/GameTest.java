package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

	@Test
	public void newGameStartsWithWhiteToMove() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertEquals(Color.WHITE, game.currentTurn());
	}

	@Test
	public void nullBoardThrows() {
		Assertions.assertThrows(NullPointerException.class, () -> new Game(null));
	}

	@Test
	public void newGameStartsInProgress() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
	}

	@Test
	public void whiteResignsAndBlackWins() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		game.resign(Color.WHITE);

		Assertions.assertEquals(GameStatus.BLACK_WIN, game.getStatus());
	}

	@Test
	public void blackResignsAndWhiteWins() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		game.resign(Color.BLACK);

		Assertions.assertEquals(GameStatus.WHITE_WIN, game.getStatus());
	}

	@Test
	public void resignNullColorThrows() {
		Board board = Board.standardSetup();
		Game game = new Game(board);

		Assertions.assertThrows(NullPointerException.class, () -> game.resign(null));
	}

	@Test
	public void makeMoveAfterResignThrows() {
		Board board = Board.standardSetup();
		Game game = new Game(board);
		game.resign(Color.WHITE);

		Assertions.assertThrows(IllegalStateException.class,
			() -> game.makeMove(Square.of(1, 0), Square.of(2, 2)));
	}
}
