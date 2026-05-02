# BVA Analysis for Knight

## Specification

A knight moves in an L-shape: two squares in one direction and one square in a perpendicular direction. A knight can jump over other pieces. It can move to an empty square or capture an opponent's piece. It cannot move off the board, move to a square occupied by a friendly piece, or make a non-knight move.

---

## Knight(Color color)

### Step 1 - Inputs and Outputs
- Input #1: knight color
- Output #1: Knight object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Case
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: valid Knight object
- Output #2: exception

### Step 4 - Test Cases
- **TC1: create white knight** ( ❌ )
  - **State of the system**: color = WHITE
  - **Expected output**: Knight is created; `color()` returns WHITE; `type()` returns KNIGHT
