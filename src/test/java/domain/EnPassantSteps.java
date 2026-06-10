package domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Set;
import org.junit.jupiter.api.Assertions;

public class EnPassantSteps {
	private Board board = new Board();
	private Game game = new Game(board);
	private Set<Square> legalMoves = Set.of();

	@Given("an en passant board with {word}")
	public void enPassantBoardWith(String piecesText) {
		board = new Board();
		game = new Game(board);
		placePieces(piecesText);
	}

	@Given("the en passant move sequence {word}")
	public void enPassantMoveSequence(String movesText) {
		if ("none".equals(movesText)) {
			return;
		}
		for (String moveText : movesText.split(";")) {
			String[] squares = moveText.split(">");
			game.makeMove(squareFrom(squares[0]), squareFrom(squares[1]));
		}
	}

	@When("legal moves are requested from {word}")
	public void legalMovesAreRequestedFrom(String squareText) {
		legalMoves = game.legalMovesFrom(squareFrom(squareText));
	}

	@When("the en passant move is made from {word} to {word}")
	public void enPassantMoveIsMade(String fromText, String toText) {
		game.makeMove(squareFrom(fromText), squareFrom(toText));
	}

	@Then("the legal moves should {word} {word}")
	public void legalMovesShould(String expectation, String targetText) {
		boolean containsTarget = legalMoves.contains(squareFrom(targetText));
		if ("include".equals(expectation)) {
			Assertions.assertTrue(containsTarget);
			return;
		}
		if ("exclude".equals(expectation)) {
			Assertions.assertFalse(containsTarget);
			return;
		}
		throw new IllegalArgumentException("Unsupported expectation: " + expectation);
	}

	@Then("the board should have {word}")
	public void boardShouldHave(String expectedText) {
		for (String expectation : expectedText.split(";")) {
			String[] parts = expectation.split("@");
			Square square = squareFrom(parts[1]);
			if ("empty".equals(parts[0])) {
				Assertions.assertTrue(board.pieceAt(square).isEmpty());
				continue;
			}
			Piece piece = board.pieceAt(square).orElseThrow();
			Assertions.assertEquals(pieceTypeFrom(parts[0]), piece.type());
			Assertions.assertEquals(colorFrom(parts[0].substring(0, 1)), piece.color());
		}
	}

	private void placePieces(String piecesText) {
		for (String pieceText : piecesText.split(";")) {
			String[] parts = pieceText.split("@");
			String pieceCode = parts[0];
			Piece piece = Piece.of(
					pieceTypeFrom(pieceCode),
					colorFrom(pieceCode.substring(0, 1)));
			board.place(squareFrom(parts[1]), piece);
		}
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
		if ("P".equals(typeCode)) {
			return PieceType.PAWN;
		}
		if ("R".equals(typeCode)) {
			return PieceType.ROOK;
		}
		throw new IllegalArgumentException("Unsupported piece type: " + pieceCode);
	}

	private Color colorFrom(String colorCode) {
		if ("W".equals(colorCode)) {
			return Color.WHITE;
		}
		if ("B".equals(colorCode)) {
			return Color.BLACK;
		}
		throw new IllegalArgumentException("Unsupported color: " + colorCode);
	}
}
