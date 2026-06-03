# BVA Analysis for Pawn Promotion

## Specification

A pawn promotes when it reaches the other side of the board. White promotes at row 7, black at row 0. The player picks what to promote to: Queen, Rook, Bishop, or Knight. King and Pawn are not allowed. canPromote tells us if a square has a pawn ready to promote. promote does the swap, and throws if the square is wrong or the type is not allowed.

---

## canPromote(Square square)

### Step 1 - Inputs and Outputs
- Input #1: square
- Input #2: piece at the square
- Output #1: boolean result

### Step 2 - Data Types
- Input #1: Reference
- Input #2: Case
- Output #1: Boolean

### Step 3 - Concrete Values
- Input #1: on-board square
- Input #2: empty, white pawn on rank 7, white pawn off rank 7, non-pawn on back rank
- Output #1: true, false

### Step 4 - Test Cases
- Test Case 27: white pawn on rank 7 can be promoted
    - Method(s) under test: `canPromote(Square square)`
    - State of the system: empty board with a white pawn placed at (7, 4)
    - Expected output: true
    - Implemented: yes

---

## promote(Square square, PieceType newType)

### Step 1 - Inputs and Outputs
- Input #1: square
- Input #2: chosen piece type
- Input #3: piece at the square
- Output #1: piece at the square after promotion
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference
- Input #2: Case
- Input #3: Case
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: on-board square
- Input #2: QUEEN, KNIGHT, KING, PAWN
- Input #3: pawn on back rank, pawn off back rank, non-pawn
- Output #1: new piece of chosen type and same color
- Output #2: exception

### Step 4 - Test Cases
- Test Case 28: promote white pawn to queen
    - Method(s) under test: `promote(Square square, PieceType newType)`
    - State of the system: white pawn at (7, 4); newType = QUEEN
    - Expected output: white queen at (7, 4)
    - Implemented: no

- Test Case 29: promote black pawn to knight
    - Method(s) under test: `promote(Square square, PieceType newType)`
    - State of the system: black pawn at (0, 4); newType = KNIGHT
    - Expected output: black knight at (0, 4)
    - Implemented: no

- Test Case 30: promote pawn off back rank throws
    - Method(s) under test: `promote(Square square, PieceType newType)`
    - State of the system: white pawn at (6, 4); newType = QUEEN
    - Expected output: exception
    - Implemented: no

- Test Case 31: promote non-pawn throws
    - Method(s) under test: `promote(Square square, PieceType newType)`
    - State of the system: white rook at (7, 0); newType = QUEEN
    - Expected output: exception
    - Implemented: no

- Test Case 32: promote to king throws
    - Method(s) under test: `promote(Square square, PieceType newType)`
    - State of the system: white pawn at (7, 4); newType = KING
    - Expected output: exception
    - Implemented: no
