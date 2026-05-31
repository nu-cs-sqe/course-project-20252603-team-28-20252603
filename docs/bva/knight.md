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
- Board state: empty board, friendly piece on candidate square, opponent piece on candidate square,
  non-candidate blockers, null
- Output: all legal candidate squares, reduced edge/corner squares, capture square included,
  friendly-occupied square excluded, exception

### Step 4 - Test Cases
- **TC2: center square has eight candidate moves** ( ✅ )
  - **State of the system**: white knight at (3, 3), otherwise empty board
  - **Expected output**: candidate moves are (1, 2), (1, 4), (2, 1), (2, 5), (4, 1),
    (4, 5), (5, 2), and (5, 4)

- **TC3: corner square has two candidate moves** ( ✅ )
  - **State of the system**: white knight at (0, 0), otherwise empty board
  - **Expected output**: candidate moves are (1, 2) and (2, 1)

- **TC4: edge square has only on-board candidate moves** ( ✅ )
  - **State of the system**: white knight at (0, 3), otherwise empty board
  - **Expected output**: candidate moves are (1, 1), (1, 5), (2, 2), and (2, 4)

- **TC5: friendly piece on candidate square blocks destination** ( ✅ )
  - **State of the system**: white knight at (3, 3), white pawn at (4, 5)
  - **Expected output**: (4, 5) is not included; the other seven center moves remain

- **TC6: opponent piece on candidate square can be captured** ( ✅ )
  - **State of the system**: white knight at (3, 3), black pawn at (4, 5)
  - **Expected output**: (4, 5) is included with the other center moves

- **TC7: non-candidate blockers do not affect knight movement** ( ✅ )
  - **State of the system**: white knight at (3, 3), pieces at (3, 4) and (3, 2)
  - **Expected output**: all eight center moves are still included

- **TC8: null source square is rejected** ( ✅ )
  - **State of the system**: source square = null, board = empty board
  - **Expected output**: exception

- **TC9: null board is rejected** ( ✅ )
  - **State of the system**: source square = (3, 3), board = null
  - **Expected output**: exception
