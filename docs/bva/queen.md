# BVA Analysis for Queen

## Specification

A queen moves horizontally, vertically, or diagonally any number of squares. It
cannot move off the board. It stops before a friendly piece and may include an
opponent-occupied square as a capture destination. A queen cannot jump over pieces.

---

## Queen(Color color)

### Step 1 - Inputs and Outputs
- Input #1: queen color
- Output #1: Queen object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Case
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: valid Queen object
- Output #2: exception

### Step 4 - Test Cases
- **TC1: create white queen** ( ✅ )
	- **State of the system**: color = WHITE
	- **Expected output**: Queen is created; `color()` returns WHITE; `type()` returns QUEEN

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
- Source square: center square (3, 3)
- Board state: empty board
- Output: all queen ray squares from the center

### Step 4 - Test Cases
- **TC2: center square has all queen candidate moves** ( ✅ )
	- **State of the system**: white queen at (3, 3), otherwise empty board
	- **Expected output**: candidate moves are all on-board horizontal, vertical, and
	  diagonal squares from (3, 3)
