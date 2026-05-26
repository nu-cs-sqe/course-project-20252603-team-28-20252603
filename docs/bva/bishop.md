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
