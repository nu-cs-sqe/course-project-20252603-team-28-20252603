package domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BishopMovementSteps {
	private Board board = new Board();
	private Bishop bishop = new Bishop(Color.WHITE);
	private Square from = Square.of(0, 0);
	private Set<Square> actualMoves = Collections.emptySet();
	private RuntimeException movementException;

	@Given("a {word} bishop starts at {word}")
	public void bishopStartsAt(String colorName, String squareText) {
		board = new Board();
		bishop = new Bishop(colorFrom(colorName));
		from = squareFrom(squareText);
		board.place(from, bishop);
	}

	@Given("the bishop board has blockers {word}")
	public void bishopBoardHasBlockers(String blockersText) {
		if ("none".equals(blockersText)) {
			return;
		}

		String[] blockers = blockersText.split(";");
		for (String blocker : blockers) {
			String[] parts = blocker.split("@");
			Piece piece = Piece.of(PieceType.PAWN, colorFrom(parts[0]));
			board.place(squareFrom(parts[1]), piece);
		}
	}

	@When("the bishop candidate moves are requested")
	public void bishopCandidateMovesAreRequested() {
		actualMoves = bishop.candidateMoves(from, board);
	}

	@When("the bishop candidate moves are requested with a null source")
	public void bishopCandidateMovesAreRequestedWithNullSource() {
		movementException = Assertions.assertThrows(
				NullPointerException.class,
				() -> bishop.candidateMoves(null, board));
	}

	@When("the bishop candidate moves are requested with a null board")
	public void bishopCandidateMovesAreRequestedWithNullBoard() {
		movementException = Assertions.assertThrows(
				NullPointerException.class,
				() -> bishop.candidateMoves(from, null));
	}

	@Then("the bishop candidate moves should be {word}")
	public void bishopCandidateMovesShouldBe(String movesText) {
		Assertions.assertEquals(squaresFrom(movesText), actualMoves);
	}

	@Then("the bishop movement request should fail with a null pointer exception")
	public void bishopMovementRequestShouldFailWithNullPointerException() {
		Assertions.assertInstanceOf(NullPointerException.class, movementException);
	}

	private Set<Square> squaresFrom(String movesText) {
		Set<Square> squares = new HashSet<>();
		for (String squareText : movesText.split(";")) {
			squares.add(squareFrom(squareText));
		}
		return squares;
	}

	private Square squareFrom(String squareText) {
		String[] coordinates = squareText.split(",");
		return Square.of(
				Integer.parseInt(coordinates[0]),
				Integer.parseInt(coordinates[1]));
	}

	private Color colorFrom(String colorName) {
		if ("white".equalsIgnoreCase(colorName) || "W".equalsIgnoreCase(colorName)) {
			return Color.WHITE;
		}
		if ("black".equalsIgnoreCase(colorName) || "B".equalsIgnoreCase(colorName)) {
			return Color.BLACK;
		}
		throw new IllegalArgumentException("Unsupported color: " + colorName);
	}
}
