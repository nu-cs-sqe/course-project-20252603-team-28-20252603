package domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;

public class PawnMovementSteps {
	private Board board = new Board();
	private Pawn pawn = new Pawn(Color.WHITE);
	private Square from = Square.of(0, 0);
	private Set<Square> actualMoves = Collections.emptySet();
	private RuntimeException movementException;

	@Given("a {word} pawn starts at {word}")
	public void pawnStartsAt(String colorName, String squareText) {
		board = new Board();
		pawn = new Pawn(colorFrom(colorName));
		from = squareFrom(squareText);
		board.place(from, pawn);
	}

	@Given("the pawn board has blockers {word}")
	public void pawnBoardHasBlockers(String blockersText) {
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

	@When("the pawn candidate moves are requested")
	public void pawnCandidateMovesAreRequested() {
		actualMoves = pawn.candidateMoves(from, board);
	}

	@When("the pawn candidate moves are requested with a null source")
	public void pawnCandidateMovesAreRequestedWithNullSource() {
		movementException = Assertions.assertThrows(
				NullPointerException.class,
				() -> pawn.candidateMoves(null, board));
	}

	@When("the pawn candidate moves are requested with a null board")
	public void pawnCandidateMovesAreRequestedWithNullBoard() {
		movementException = Assertions.assertThrows(
				NullPointerException.class,
				() -> pawn.candidateMoves(from, null));
	}

	@Then("the pawn candidate moves should be {word}")
	public void pawnCandidateMovesShouldBe(String movesText) {
		Assertions.assertEquals(squaresFrom(movesText), actualMoves);
	}

	@Then("the pawn movement request should fail with a null pointer exception")
	public void pawnMovementRequestShouldFailWithNullPointerException() {
		Assertions.assertInstanceOf(NullPointerException.class, movementException);
	}

	private Set<Square> squaresFrom(String movesText) {
		Set<Square> squares = new HashSet<>();
		if ("none".equals(movesText)) {
			return squares;
		}
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
