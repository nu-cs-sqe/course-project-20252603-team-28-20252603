# BVA Analysis for Bishop

## Specification

A bishop moves diagonally any number of squares. It cannot move off the board. It stops
before a friendly piece and may include an opponent-occupied square as a capture
destination. A bishop cannot jump over pieces.

---

## Bishop(Color color)

### Step 1 - Inputs and Outputs
- Input #1: bishop color
- Output #1: Bishop object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Case
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: valid Bishop object
- Output #2: exception

### Step 4 - Test Cases
- **TC1: create white bishop** ( ✅ )
	- **State of the system**: color = WHITE
	- **Expected output**: Bishop is created; `color()` returns WHITE; `type()` returns BISHOP

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
- Source square: center square (3, 3), corner square (0, 0), edge square (0, 3), null
- Board state: empty board, friendly piece on diagonal, opponent piece on diagonal,
  blockers beyond a capture, null
- Output: all diagonal squares, reduced edge/corner diagonals, friendly square excluded,
  opponent square included, beyond-blocker squares excluded, exception

### Step 4 - Test Cases
- **TC2: center square has all diagonal candidate moves** ( ✅ )
	- **State of the system**: white bishop at (3, 3), otherwise empty board
	- **Expected output**: candidate moves are all on-board diagonal squares from (3, 3)

- **TC3: corner square has one diagonal ray** ( ✅ )
	- **State of the system**: white bishop at (0, 0), otherwise empty board
	- **Expected output**: candidate moves are (1, 1), (2, 2), (3, 3), (4, 4),
	  (5, 5), (6, 6), and (7, 7)
