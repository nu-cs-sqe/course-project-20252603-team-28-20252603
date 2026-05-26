package domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;

public class BishopMovementSteps {
	private Board board = new Board();
	private Bishop bishop = new Bishop(Color.WHITE);
	private Square from = Square.of(0, 0);
	private Set<Square> actualMoves = Collections.emptySet();

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

	@Then("the bishop candidate moves should be {word}")
	public void bishopCandidateMovesShouldBe(String movesText) {
		Assertions.assertEquals(squaresFrom(movesText), actualMoves);
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
