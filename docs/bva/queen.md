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
