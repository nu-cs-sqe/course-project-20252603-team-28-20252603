# BVA Analysis for Game.isCheckmate and Game.isStalemate

## Specification

isCheckmate(Color color) returns true if the given color's king is currently in check AND has no legal moves that escape check. isStalemate(Color color) returns true if the king is NOT in check AND there are no legal moves at all. Both methods iterate the player's pieces, simulate each candidate move on a board copy, and ask whether the simulated board still leaves the king in check. A legal move is one that does not leave the moving player's king in check after the move.

---

## isCheckmate(Color color)

### Step 1 - Inputs and Outputs
- Input #1: color whose king we are checking for checkmate
- Output #1: boolean (true if in check and no legal escape, false otherwise)
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference (enum)
- Output #1: Boolean
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: true (checkmated), false (not checkmated)
- Output #2: NullPointerException

### Step 4 - Test Cases

- Test Case 15: standard setup, neither side is in checkmate
    - Method(s) under test: `isCheckmate(Color)`
    - State of the system: new Game with standard starting position
    - Expected output: isCheckmate(WHITE) returns false, isCheckmate(BLACK) returns false
    - Implemented: yes

- Test Case 16: back-rank checkmate against white king
    - Method(s) under test: `isCheckmate(Color)`
    - State of the system: white king at (4,0), black rook at (4,7), white pawns blocking escape on rank 1
    - Expected output: isCheckmate(WHITE) returns true
    - Implemented: yes

- Test Case 17: in check but king has an escape square
    - Method(s) under test: `isCheckmate(Color)`
    - State of the system: white king at (4,0), black rook at (4,7), no blockers on escape squares
    - Expected output: isCheckmate(WHITE) returns false because king can step away
    - Implemented: yes

- Test Case 18: isCheckmate rejects null color
    - Method(s) under test: `isCheckmate(Color)`
    - State of the system: new game from standard setup
    - Expected output: NullPointerException
    - Implemented: yes

---

## isStalemate(Color color)

### Step 1 - Inputs and Outputs
- Input #1: color whose king we are checking for stalemate
- Output #1: boolean
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference (enum)
- Output #1: Boolean
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: true (stalemated), false (not stalemated)
- Output #2: NullPointerException

### Step 4 - Test Cases

- Test Case 19: standard setup, neither side is in stalemate
    - Method(s) under test: `isStalemate(Color)`
    - State of the system: new Game with standard starting position
    - Expected output: isStalemate(WHITE) returns false, isStalemate(BLACK) returns false
    - Implemented: yes

- Test Case 20: stalemate position, white not in check but no legal moves
    - Method(s) under test: `isStalemate(Color)`
    - State of the system: white king cornered, black king and queen creating no-legal-move position without check
    - Expected output: isStalemate(WHITE) returns true
    - Implemented: yes

- Test Case 21: isStalemate rejects null color
    - Method(s) under test: `isStalemate(Color)`
    - State of the system: new game from standard setup
    - Expected output: NullPointerException
    - Implemented: yes
