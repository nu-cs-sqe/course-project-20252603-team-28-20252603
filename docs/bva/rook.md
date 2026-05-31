# BVA Analysis for Rook

## Specification

A rook moves horizontally or vertically any number of squares. It cannot move off
the board. It stops before a friendly piece and may include an opponent-occupied
square as a capture destination. A rook cannot jump over pieces.

---

## Rook(Color color)

### Step 1 - Inputs and Outputs
- Input #1: rook color
- Output #1: Rook object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Case
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: valid Rook object
- Output #2: exception

### Step 4 - Test Cases
- **TC1: create white rook** ( ✅ )
	- **State of the system**: color = WHITE
	- **Expected output**: Rook is created; `color()` returns WHITE; `type()` returns ROOK

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
- Board state: empty board, friendly piece on rook ray, opponent piece on rook ray
- Output: all rook ray squares from the center, friendly square excluded,
  opponent square included, beyond-blocker squares excluded

### Step 4 - Test Cases
- **TC2: center square has all rook candidate moves** ( ✅ )
	- **State of the system**: white rook at (3, 3), otherwise empty board
	- **Expected output**: candidate moves are all on-board horizontal and vertical
	  squares from (3, 3)

- **TC3: corner square has two rook rays** ( ✅ )
	- **State of the system**: white rook at (0, 0), otherwise empty board
	- **Expected output**: candidate moves are all squares to the right and upward
	  from (0, 0)

- **TC4: edge square has only on-board rook rays** ( ✅ )
	- **State of the system**: white rook at (0, 3), otherwise empty board
	- **Expected output**: candidate moves are all horizontal and vertical squares
	  that remain on the board

- **TC5: friendly piece blocks rook movement** ( ✅ )
	- **State of the system**: white rook at (3, 3), white pawn at (5, 3)
	- **Expected output**: (5, 3) and squares beyond it on that rank are not included

- **TC6: opponent piece can be captured and blocks beyond squares** ( ✅ )
	- **State of the system**: white rook at (3, 3), black pawn at (5, 3)
	- **Expected output**: (5, 3) is included; (6, 3) and (7, 3) are not included

- **TC7: null source square is rejected** ( ✅ )
	- **State of the system**: source square = null, board = empty board
	- **Expected output**: exception
