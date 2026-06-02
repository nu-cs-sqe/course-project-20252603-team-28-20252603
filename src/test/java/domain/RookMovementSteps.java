package domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;

public class RookMovementSteps {
	private Board board = new Board();
	private Rook rook = new Rook(Color.WHITE);
	private Square from = Square.of(0, 0);
	private Set<Square> actualMoves = Collections.emptySet();
	private RuntimeException movementException;

	@Given("a {word} rook starts at {word}")
	public void rookStartsAt(String colorName, String squareText) {
		board = new Board();
		rook = new Rook(colorFrom(colorName));
		from = squareFrom(squareText);
		board.place(from, rook);
	}

	@Given("the rook board has blockers {word}")
	public void rookBoardHasBlockers(String blockersText) {
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

	@When("the rook candidate moves are requested")
	public void rookCandidateMovesAreRequested() {
		actualMoves = rook.candidateMoves(from, board);
	}

	@When("the rook candidate moves are requested with a null source")
	public void rookCandidateMovesAreRequestedWithNullSource() {
		movementException = Assertions.assertThrows(
				NullPointerException.class,
				() -> rook.candidateMoves(null, board));
	}

	@When("the rook candidate moves are requested with a null board")
	public void rookCandidateMovesAreRequestedWithNullBoard() {
		movementException = Assertions.assertThrows(
				NullPointerException.class,
				() -> rook.candidateMoves(from, null));
	}

	@Then("the rook candidate moves should be {word}")
	public void rookCandidateMovesShouldBe(String movesText) {
		Assertions.assertEquals(squaresFrom(movesText), actualMoves);
	}

	@Then("the rook movement request should fail with a null pointer exception")
	public void rookMovementRequestShouldFailWithNullPointerException() {
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
