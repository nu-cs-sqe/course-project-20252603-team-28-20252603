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
- **TC1: create white knight** ( ✅ )
  - **State of the system**: color = WHITE
  - **Expected output**: Knight is created; `color()` returns WHITE; `type()` returns KNIGHT


## candidateMoves(Square from, Board board)

### Step 1 - Inputs and Outputs
- Input #1: starting square (where the knight currently is)
- Input #2: board state (positions of all pieces)
- Output #1: set of candidate destination squares
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference (Square)
- Input #2: Reference (Board)
- Output #1: Reference (Set<Square>)
- Output #2: Case (NullPointerException)

### Step 3 - Concrete Values
- Input #1: center of board, corner, edge, near-corner
- Input #2: empty board, friendly piece on destination, opposing piece on destination, intervening pieces
- Output #1: set sized 0-8 of valid destination squares
- Output #2: NullPointerException on null arguments

### Step 4 - Test Cases
- **TC1: knight at center of empty board → 8 L-shape destinations** ( ✅ )
  - State: knight at d4 (3,3); board otherwise empty
  - Expected: returns 8 squares (b3, b5, c2, c6, e2, e6, f3, f5)
- **TC2: knight at corner a1 → 2 destinations** ( ✅ )
  - State: knight at a1 (0,0); board otherwise empty
  - Expected: returns 2 squares (b3, c2)
- **TC3: knight at edge a4 → 4 destinations** ( ✅ )
  - State: knight at a4 (0,3); board otherwise empty
  - Expected: returns 4 squares (b2, b6, c3, c5)
- **TC4: knight cannot land on friendly piece → friendly destination excluded** ( ✅ )
  - State: white knight at d4; white pawn at e6
  - Expected: e6 is NOT in the returned set
- **TC5: knight can land on opposing piece (capture) → destination included** ( ❌ )
  - State: white knight at d4; black pawn at e6
  - Expected: e6 IS in the returned set
- **TC6: knight jumps over intervening pieces → unaffected** ( ❌ )
  - State: white knight at d4; arbitrary pieces at d5, e4, etc.
  - Expected: returns 8 squares (intervening pieces are irelevant)