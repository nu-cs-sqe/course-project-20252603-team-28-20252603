# BVA Analysis for Pawn

## Specification

A pawn moves one square forward toward the opponent side. From its starting rank,
it may move two squares forward if both forward squares are empty. It captures one
square diagonally forward when that square is occupied by an opponent piece. It
cannot move forward into an occupied square, capture a friendly piece, move
backward, or move off the board.

---

## Pawn(Color color)

### Step 1 - Inputs and Outputs
- Input #1: pawn color
- Output #1: Pawn object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Case
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: valid Pawn object
- Output #2: exception

### Step 4 - Test Cases
- **TC1: create white pawn** ( ✅ )
	- **State of the system**: color = WHITE
	- **Expected output**: Pawn is created; `color()` returns WHITE; `type()` returns PAWN
