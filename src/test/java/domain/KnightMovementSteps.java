package domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KnightMovementSteps {
	private Board board = new Board();
	private Knight knight = new Knight(Color.WHITE);
	private Square from = Square.of(0, 0);
	private Set<Square> actualMoves = Collections.emptySet();

	@Given("a {word} knight starts at {word}")
	public void knightStartsAt(String colorName, String squareText) {
		board = new Board();
		knight = new Knight(colorFrom(colorName));
		from = squareFrom(squareText);
		board.place(from, knight);
	}

	@Given("the board has blockers {word}")
	public void boardHasBlockers(String blockersText) {
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

	@When("the knight candidate moves are requested")
	public void knightCandidateMovesAreRequested() {
		actualMoves = knight.candidateMoves(from, board);
	}

	@Then("the knight candidate moves should be {word}")
	public void knightCandidateMovesShouldBe(String movesText) {
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
