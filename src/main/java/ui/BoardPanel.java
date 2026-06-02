package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import javax.swing.JPanel;

import domain.Board;
import domain.Color;
import domain.Piece;
import domain.Square;

// renders the chess Board as an 8x8 grid with unicode chess glyphs
public class BoardPanel extends JPanel {
	private static final int BOARD_SIZE = 8;
	private static final java.awt.Color LIGHT_SQUARE = new java.awt.Color(160, 180, 200);
	private static final java.awt.Color DARK_SQUARE = new java.awt.Color(70, 100, 130);
	private static final java.awt.Color BACKGROUND = new java.awt.Color(50, 50, 60);
	private static final java.awt.Color HIGHLIGHT = new java.awt.Color(255, 235, 50, 120);
	private static final java.awt.Color LEGAL_DEST = new java.awt.Color(50, 100, 50, 150);

	private final Board board;
	private Square selected;

	public BoardPanel(Board board) {
		this.board = board;
		setBackground(BACKGROUND);
		setPreferredSize(new Dimension(900, 900));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				squareAt(e.getX(), e.getY()).ifPresent(square -> {
					selected = square;
					System.out.println("selected square file=" + square.file() + " rank=" + square.rank());
					repaint();
				});
			}
		});
	}

	// inverse of the paint math, figures out which square the pixel landed on
	private Optional<Square> squareAt(int pixelX, int pixelY) {
		int squareSize = Math.min(getWidth(), getHeight()) / BOARD_SIZE;
		int xOffset = (getWidth() - squareSize * BOARD_SIZE) / 2;
		int yOffset = (getHeight() - squareSize * BOARD_SIZE) / 2;
		int relX = pixelX - xOffset;
		int relY = pixelY - yOffset;
		int boardPixelSize = squareSize * BOARD_SIZE;
		if (relX < 0 || relY < 0 || relX >= boardPixelSize || relY >= boardPixelSize) {
			return Optional.empty();
		}
		int file = relX / squareSize;
		// rank flips again because we paint rank=7 at top
		int rank = BOARD_SIZE - 1 - relY / squareSize;
		return Optional.of(Square.of(file, rank));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// recompute square size each repaint so the board adapts to window resize
		int squareSize = Math.min(getWidth(), getHeight()) / BOARD_SIZE;
		int xOffset = (getWidth() - squareSize * BOARD_SIZE) / 2;
		int yOffset = (getHeight() - squareSize * BOARD_SIZE) / 2;

		// figure out which squares the selected piece can move to
		Set<Square> legalDests = Collections.emptySet();
		if (selected != null) {
			Optional<Piece> selectedPiece = board.pieceAt(selected);
			if (selectedPiece.isPresent()) {
				legalDests = selectedPiece.get().candidateMoves(selected, board);
			}
		}

		for (int file = 0; file < BOARD_SIZE; file++) {
			for (int rank = 0; rank < BOARD_SIZE; rank++) {
				int x = xOffset + file * squareSize;
				// swing draws y = 0 at top of the panel, but rank=0 is whites home row
				// flip rank so rank= 7 (black side) draws at top
				int y = yOffset + (BOARD_SIZE - 1 - rank) * squareSize;

				boolean dark = (file + rank) % 2 == 0;
				g.setColor(dark ? DARK_SQUARE : LIGHT_SQUARE);
				g.fillRect(x, y, squareSize, squareSize);

				// highlight if this is the selected square
				if (selected != null && selected.file() == file && selected.rank() == rank) {
					g.setColor(HIGHLIGHT);
					g.fillRect(x, y, squareSize, squareSize);
				}

				// dot on empty legal destinations, ring on capturable squares
				Square here = Square.of(file, rank);
				if (legalDests.contains(here)) {
					g.setColor(LEGAL_DEST);
					if (board.pieceAt(here).isPresent()) {
						java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
						g2.setStroke(new java.awt.BasicStroke(squareSize / 16f));
						g2.drawOval(x + squareSize / 16, y + squareSize / 16, squareSize - squareSize / 8, squareSize - squareSize / 8);
					} else {
						int dotSize = squareSize / 4;
						g.fillOval(x + (squareSize - dotSize) / 2, y + (squareSize - dotSize) / 2, dotSize, dotSize);
					}
				}

				board.pieceAt(here).ifPresent(piece -> {
					g.setColor(piece.color() == Color.WHITE ? java.awt.Color.WHITE : java.awt.Color.BLACK);
					g.setFont(new Font("Serif", Font.PLAIN, squareSize * 9 / 10));
					// y is the font basline so we offset down so the glyph centers veritcally
					FontMetrics fm = g.getFontMetrics();
					String glyph = glyphFor(piece);
					Rectangle2D bounds = fm.getStringBounds(glyph, g);
					int glyphX = x + (int) ((squareSize - bounds.getWidth()) / 2);
					int glyphY = y + (int) ((squareSize - bounds.getHeight()) / 2 - bounds.getY());
					g.drawString(glyph, glyphX, glyphY);
				});
			}
		}
	}

	private static String glyphFor(Piece piece) {
		switch (piece.type()) {
			case KING:   return "♚";
			case QUEEN:  return "♛";
			case ROOK:   return "♜";
			case BISHOP: return "♝";
			case KNIGHT: return "♞";
			case PAWN:   return "♟";
			default:
				throw new IllegalArgumentException(
					"Unknown piece type: " + piece.type());
		}
	}
}
