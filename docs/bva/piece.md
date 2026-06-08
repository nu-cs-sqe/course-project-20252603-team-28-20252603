# BVA Analysis for Piece

## Specification

A piece has a color and a type. The abstract Piece class stores the shared color behavior,
while `Piece.of()` creates the correct concrete piece class from a `PieceType`.

---

## Piece.of(PieceType type, Color color)

### Step 1 - Inputs and Outputs
- Input #1: piece type
- Input #2: piece color
- Output #1: Piece object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Case
- Input #2: Case
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING, null
- Input #2: WHITE, BLACK, null
- Output #1: matching concrete Piece object
- Output #2: exception

### Step 4 - Test Cases
- **TC1: create each white piece type** ( ✅ )
	- **State of the system**: type is each PieceType value, color = WHITE
	- **Expected output**: Piece is created; `type()` matches input type; `color()` returns WHITE

- **TC2: create black knight** ( ✅ )
	- **State of the system**: type = KNIGHT, color = BLACK
	- **Expected output**: Knight is created; `type()` returns KNIGHT; `color()` returns BLACK

- **TC3: reject null piece type** ( ✅ )
	- **State of the system**: type = null, color = WHITE
	- **Expected output**: exception

- **TC4: reject null color** ( ✅ )
	- **State of the system**: type = PAWN, color = null
	- **Expected output**: exception

## color()

### Step 4 - Test Cases
- **TC5: return white piece color** ( implemented in TC1 )
	- **State of the system**: piece created with color = WHITE
	- **Expected output**: `color()` returns WHITE

- **TC6: return black piece color** ( implemented in TC2 )
	- **State of the system**: piece created with color = BLACK
	- **Expected output**: `color()` returns BLACK

## type()

### Step 4 - Test Cases
- **TC7: return concrete piece type** ( implemented in TC1 )
	- **State of the system**: piece created through `Piece.of()`
	- **Expected output**: `type()` returns the matching PieceType

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
- Source square: corner square (0, 0)
- Board state: empty board, null
- Capture state: opponent piece on diagonal forward square, friendly piece on
  diagonal forward square
- Output: collection of candidate moves

### Step 4 - Test Cases
- **TC8: null source square is rejected** ( ✅ )
	- **State of the system**: source square = null, board = empty board
	- **Expected output**: exception

- **TC9: null board is rejected** ( ✅ )
	- **State of the system**: source square = (0, 0), board = null
	- **Expected output**: exception

- **TC10: candidate moves for default piece is empty** ( ✅ )
	- **State of the system**: source square = (0, 0), board = empty board
	- **Expected output**: no candidate moves
