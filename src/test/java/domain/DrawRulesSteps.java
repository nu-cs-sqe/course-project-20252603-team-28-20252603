package domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class DrawRulesSteps {
	private Board board = new Board();
	private Game game = new Game(board);
	private boolean insufficientMaterial;

	@Given("a draw rules board with {word}")
	public void drawRulesBoardWith(String piecesText) {
		board = new Board();
		game = new Game(board);
		for (String pieceText : piecesText.split(";")) {
			String[] parts = pieceText.split("@");
			String pieceCode = parts[0];
			board.place(
					squareFrom(parts[1]),
					Piece.of(pieceTypeFrom(pieceCode), colorFrom(pieceCode)));
		}
	}

	@When("insufficient material is checked")
	public void insufficientMaterialIsChecked() {
		insufficientMaterial = game.isInsufficientMaterial();
	}

	@When("a draw rules move is made from {word} to {word}")
	public void drawRulesMoveIsMade(String fromText, String toText) {
		game.makeMove(squareFrom(fromText), squareFrom(toText));
	}

	@Then("insufficient material should be {word}")
	public void insufficientMaterialShouldBe(String expectedText) {
		Assertions.assertEquals(Boolean.parseBoolean(expectedText), insufficientMaterial);
	}

	@Then("game status should be {word}")
	public void gameStatusShouldBe(String statusText) {
		Assertions.assertEquals(GameStatus.valueOf(statusText), game.getStatus());
	}

	private Square squareFrom(String squareText) {
		String[] coordinates = squareText.split(",");
		return Square.of(
				Integer.parseInt(coordinates[0]),
				Integer.parseInt(coordinates[1]));
	}

	private PieceType pieceTypeFrom(String pieceCode) {
		String typeCode = pieceCode.substring(1);
		if ("K".equals(typeCode)) {
			return PieceType.KING;
		}
		if ("B".equals(typeCode)) {
			return PieceType.BISHOP;
		}
		if ("N".equals(typeCode)) {
			return PieceType.KNIGHT;
		}
		if ("P".equals(typeCode)) {
			return PieceType.PAWN;
		}
		if ("R".equals(typeCode)) {
			return PieceType.ROOK;
		}
		if ("Q".equals(typeCode)) {
			return PieceType.QUEEN;
		}
		throw new IllegalArgumentException("Unsupported piece type: " + pieceCode);
	}

	private Color colorFrom(String pieceCode) {
		if ("W".equals(pieceCode.substring(0, 1))) {
			return Color.WHITE;
		}
		if ("B".equals(pieceCode.substring(0, 1))) {
			return Color.BLACK;
		}
		throw new IllegalArgumentException("Unsupported color: " + pieceCode);
	}
}
