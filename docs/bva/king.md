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
