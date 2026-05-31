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

## candidateMoves(Square from, Board board)

### Step 1 - Inputs and Outputs
- Input #1: source square
- Input #2: board state
- Output #1: set of candidate destination squares
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference
- Input #2: Reference
- Output #1: Collection
- Output #2: Case

### Step 3 - Concrete Values
- Source square: middle-board square (3, 3), white starting square (3, 1)
- Board state: empty board, occupied forward square, occupied double-step destination
- Output: one forward move for a white or black pawn

### Step 4 - Test Cases
- **TC2: white pawn moves one square forward** ( ✅ )
	- **State of the system**: white pawn at (3, 3), otherwise empty board
	- **Expected output**: candidate moves are (3, 4)

- **TC3: black pawn moves one square forward toward lower ranks** ( ✅ )
	- **State of the system**: black pawn at (3, 3), otherwise empty board
	- **Expected output**: candidate moves are (3, 2)

- **TC4: white pawn on starting rank may move two squares if unobstructed** ( ✅ )
	- **State of the system**: white pawn at (3, 1), otherwise empty board
	- **Expected output**: candidate moves are (3, 2) and (3, 3)

- **TC5: occupied forward square blocks pawn movement** ( ✅ )
	- **State of the system**: white pawn at (3, 3), any piece at (3, 4)
	- **Expected output**: no candidate moves

- **TC6: occupied double-step destination blocks only the double move** ( ✅ )
	- **State of the system**: white pawn at (3, 1), any piece at (3, 3), square (3, 2) empty
	- **Expected output**: candidate moves are (3, 2)
