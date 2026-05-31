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
- Source square: center square (3, 3), corner square (0, 0), edge square (0, 3)
- Board state: empty board, friendly piece on orthogonal ray, opponent piece on
  orthogonal ray, friendly piece on diagonal ray, opponent piece on diagonal ray
- Output: all queen ray squares from the center, friendly square excluded,
  opponent square included, beyond-blocker squares excluded

### Step 4 - Test Cases
- **TC2: center square has all queen candidate moves** ( ✅ )
	- **State of the system**: white queen at (3, 3), otherwise empty board
	- **Expected output**: candidate moves are all on-board horizontal, vertical, and
	  diagonal squares from (3, 3)

- **TC3: corner square has only on-board queen rays** ( ✅ )
	- **State of the system**: white queen at (0, 0), otherwise empty board
	- **Expected output**: candidate moves are all squares to the right, upward, and
	  upward diagonal from (0, 0)

- **TC4: edge square has only on-board queen rays** ( ✅ )
	- **State of the system**: white queen at (0, 3), otherwise empty board
	- **Expected output**: candidate moves are all horizontal, vertical, and diagonal
	  squares that remain on the board

- **TC5: friendly piece blocks orthogonal movement** ( ✅ )
	- **State of the system**: white queen at (3, 3), white pawn at (5, 3)
	- **Expected output**: (5, 3) and squares beyond it on that rank are not included

- **TC6: opponent piece on orthogonal ray can be captured and blocks beyond squares** ( ✅ )
	- **State of the system**: white queen at (3, 3), black pawn at (5, 3)
	- **Expected output**: (5, 3) is included; (6, 3) and (7, 3) are not included

- **TC7: friendly piece blocks diagonal movement** ( ✅ )
	- **State of the system**: white queen at (3, 3), white pawn at (5, 5)
	- **Expected output**: (5, 5) and squares beyond it on that diagonal are not included

- **TC8: opponent piece on diagonal ray can be captured and blocks beyond squares** ( ✅ )
	- **State of the system**: white queen at (3, 3), black pawn at (5, 5)
	- **Expected output**: (5, 5) is included; (6, 6) and (7, 7) are not included
