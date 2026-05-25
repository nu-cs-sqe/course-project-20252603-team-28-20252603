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

---

## moveCandidates(Square from)

L-shape destinations from the given square on an empty board. No capture or blocking logic.

### Step 1 - Inputs and Outputs
- Input #1: knight's current square
- Output #1: set of candidate squares
- Output #2: exception

### Step 2 - Data Types
- Input #1: Square (file in [0, 7]; rank in [0, 7])
- Output #1: Collection (Set)
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: file in {0, 4, 7}; rank in {0, 4, 7}; null Square
- Output #1: set of size 2, 4, or 8
- Output #2: NullPointerException

### Step 4 - Test Cases
- **TC1: knight at center returns 8 candidates**
  - **State of the system**: from = (4, 4)
  - **Expected output**: (5, 6), (5, 2), (3, 6), (3, 2), (6, 5), (6, 3), (2, 5), (2, 3)
- **TC2: knight at corner a1 returns 2 candidates**
  - **State of the system**: from = (0, 0)
  - **Expected output**: (1, 2), (2, 1)
- **TC3: knight at corner h8 returns 2 candidates**
  - **State of the system**: from = (7, 7)
  - **Expected output**: (6, 5), (5, 6)
- **TC4: knight at edge a-file middle returns 4 candidates**
  - **State of the system**: from = (0, 4)
  - **Expected output**: (1, 6), (1, 2), (2, 5), (2, 3)
- **TC5: knight at edge first-rank middle returns 4 candidates**
  - **State of the system**: from = (4, 0)
  - **Expected output**: (5, 2), (3, 2), (6, 1), (2, 1)
- **TC6: null Square throws NullPointerException**
  - **State of the system**: from = null
  - **Expected output**: NullPointerException

