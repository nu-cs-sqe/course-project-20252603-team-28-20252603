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
