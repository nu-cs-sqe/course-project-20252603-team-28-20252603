package domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KingMovementSteps {
	private Board board = new Board();
	private King king = new King(Color.WHITE);
	private Square from = Square.of(0, 0);
	private Set<Square> actualMoves = Collections.emptySet();
	private RuntimeException movementException;

	@Given("a {word} king starts at {word}")
	public void kingStartsAt(String colorName, String squareText) {
		board = new Board();
		king = new King(colorFrom(colorName));
		from = squareFrom(squareText);
		board.place(from, king);
	}

	@Given("the king board has blockers {word}")
	public void kingBoardHasBlockers(String blockersText) {
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

	@When("the king candidate moves are requested")
	public void kingCandidateMovesAreRequested() {
		actualMoves = king.candidateMoves(from, board);
	}

	@When("the king candidate moves are requested with a null source")
	public void kingCandidateMovesAreRequestedWithNullSource() {
		movementException = Assertions.assertThrows(
				NullPointerException.class,
				() -> king.candidateMoves(null, board));
	}

	@When("the king candidate moves are requested with a null board")
	public void kingCandidateMovesAreRequestedWithNullBoard() {
		movementException = Assertions.assertThrows(
				NullPointerException.class,
				() -> king.candidateMoves(from, null));
	}

	@Then("the king candidate moves should be {word}")
	public void kingCandidateMovesShouldBe(String movesText) {
		Assertions.assertEquals(squaresFrom(movesText), actualMoves);
	}

	@Then("the king movement request should fail with a null pointer exception")
	public void kingMovementRequestShouldFailWithNullPointerException() {
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
