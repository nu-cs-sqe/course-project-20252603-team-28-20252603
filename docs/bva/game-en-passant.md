# BVA Analysis for En Passant

## Specification

En passant is a Game-level pawn rule because it depends on the immediately
previous move. A pawn may capture an adjacent opponent pawn that just moved two
squares by moving diagonally behind it. The captured pawn is removed from the
square it passed through. The capture must be made immediately and must not leave
the moving side in check.

---

## legalMovesFrom(Square from)

### Step 1 - Inputs and Outputs
- Input #1: source square
- Input #2: current board state
- Input #3: immediately previous move
- Output #1: set of legal destination squares

### Step 2 - Data Types
- Input #1: Reference
- Input #2: Reference
- Input #3: Reference or empty state
- Output #1: Collection

### Step 3 - Concrete Values
- Source square: pawn adjacent to an opponent pawn that just moved two squares
- Board state: both kings present, capturing pawn and capturable pawn present
- Previous move: opponent pawn moved two squares to adjacent file and same rank
- Output: ordinary legal moves plus en passant destination

### Step 4 - Test Cases
- **TC1: white en passant is available after black two-step pawn move** ( ✅ )
	- **State of the system**: white pawn at (4, 4), black pawn moves from (5, 6)
	  to (5, 4), both kings are present
	- **Expected output**: `legalMovesFrom(4, 4)` includes (5, 5)

## makeMove(Square from, Square to)

- **TC2: white en passant execution removes the captured pawn** ( ✅ )
	- **State of the system**: white pawn at (4, 4), black pawn has just moved from
	  (5, 6) to (5, 4), both kings are present
	- **Expected output**: after `makeMove(4, 4, 5, 5)`, the white pawn is at
	  (5, 5), (4, 4) is empty, and (5, 4) is empty

- **TC3: black en passant is available after white two-step pawn move** ( ✅ )
	- **State of the system**: black pawn at (4, 3), white pawn moves from (5, 1)
	  to (5, 3), both kings are present
	- **Expected output**: `legalMovesFrom(4, 3)` includes (5, 2)
