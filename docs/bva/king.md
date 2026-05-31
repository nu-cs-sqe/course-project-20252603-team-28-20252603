# BVA Analysis for King

## Specification

A king moves one square horizontally, vertically, or diagonally. It cannot move off
the board. It can move to an empty square or capture an opponent's piece. It
cannot move to a square occupied by a friendly piece.

---

## King(Color color)

### Step 1 - Inputs and Outputs
- Input #1: king color
- Output #1: King object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Case
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: valid King object
- Output #2: exception

### Step 4 - Test Cases
- **TC1: create white king** ( ✅ )
	- **State of the system**: color = WHITE
	- **Expected output**: King is created; `color()` returns WHITE; `type()` returns KING

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
- Source square: center square (3, 3), corner square (0, 0)
- Board state: empty board
- Output: all adjacent squares from the center

### Step 4 - Test Cases
- **TC2: center square has eight candidate moves** ( ✅ )
	- **State of the system**: white king at (3, 3), otherwise empty board
	- **Expected output**: candidate moves are the eight adjacent squares around (3, 3)

- **TC3: corner square has three candidate moves** ( ✅ )
	- **State of the system**: white king at (0, 0), otherwise empty board
	- **Expected output**: candidate moves are (0, 1), (1, 0), and (1, 1)
