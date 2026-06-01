# BVA Analysis for Game.isInCheck

## Specification

isInCheck(Color color) returns true if the given color's king is under attack by any opposing piece on the current board. The check is computed by iterating every opponent piece and asking whether any of its candidateMoves lands on the king's square.

---

## isInCheck(Color color)

### Step 1 - Inputs and Outputs
- Input #1: color whose king we are checking
- Output #1: boolean (true if king is attacked, false otherwise)
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference (enum)
- Output #1: Boolean
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: true (king attacked), false (king not attacked)
- Output #2: NullPointerException

### Step 4 - Test Cases

- Test Case 11: standard setup, neither king is in check
    - Method(s) under test: `isInCheck(Color)`
    - State of the system: new Game with standard chess starting position
    - Expected output: isInCheck(WHITE) returns false, isInCheck(BLACK) returns false
    - Implemented: no

- Test Case 12: bishop attacks white king on diagonal
    - Method(s) under test: `isInCheck(Color)`
    - State of the system: empty board, white king at (4,0), black bishop at (7,3)
    - Expected output: isInCheck(WHITE) returns true, isInCheck(BLACK) returns false
    - Implemented: no

- Test Case 13: knight attacks white king via L move
    - Method(s) under test: `isInCheck(Color)`
    - State of the system: empty board, white king at (4,0), black knight at (3,2)
    - Expected output: isInCheck(WHITE) returns true, isInCheck(BLACK) returns false
    - Implemented: no

- Test Case 14: isInCheck rejects null color
    - Method(s) under test: `isInCheck(Color)`
    - State of the system: new game from standard setup
    - Expected output: NullPointerException
    - Implemented: no
