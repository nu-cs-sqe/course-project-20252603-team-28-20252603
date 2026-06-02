package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import domain.Board;
import domain.Color;
import domain.Piece;
import domain.Square;

// Renders the chess Board as an 8x8 grid with Unicode chess glyphs.
public class BoardPanel extends JPanel {
	private static final int SQUARE_SIZE = 64;
	private static final int BOARD_SIZE = 8;
	private static final java.awt.Color LIGHT_SQUARE = new java.awt.Color(240, 217, 181);
	private static final java.awt.Color DARK_SQUARE = new java.awt.Color(181, 136, 99);

	private final Board board;

	public BoardPanel(Board board) {
		this.board = board;
		setPreferredSize(new Dimension(SQUARE_SIZE * BOARD_SIZE, SQUARE_SIZE * BOARD_SIZE));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int file = 0; file < BOARD_SIZE; file++) {
			for (int rank = 0; rank < BOARD_SIZE; rank++) {
				int x = file * SQUARE_SIZE;
				int y = (BOARD_SIZE - 1 - rank) * SQUARE_SIZE;

				boolean dark = (file + rank) % 2 == 0;
				g.setColor(dark ? DARK_SQUARE : LIGHT_SQUARE);
				g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

				board.pieceAt(Square.of(file, rank)).ifPresent(piece -> {
					g.setColor(java.awt.Color.BLACK);
					g.setFont(new Font("Serif", Font.PLAIN, SQUARE_SIZE - 8));
					g.drawString(glyphFor(piece), x + 8, y + SQUARE_SIZE - 12);
				});
			}
		}
	}

	private static String glyphFor(Piece piece) {
		boolean white = piece.color() == Color.WHITE;
		switch (piece.type()) {
			case KING:   return white ? "♔" : "♚";
			case QUEEN:  return white ? "♕" : "♛";
			case ROOK:   return white ? "♖" : "♜";
			case BISHOP: return white ? "♗" : "♝";
			case KNIGHT: return white ? "♘" : "♞";
			case PAWN:   return white ? "♙" : "♟";
			default:
				throw new IllegalArgumentException(
					"Unknown piece type: " + piece.type());
		}
	}
}
