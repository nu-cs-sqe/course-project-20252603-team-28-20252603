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

- Test Case 19: standard setup, neither side is in checkmate
    - Method(s) under test: `isCheckmate(Color)`
    - State of the system: new Game with standard starting position
    - Expected output: isCheckmate(WHITE) returns false, isCheckmate(BLACK) returns false
    - Implemented: yes

- Test Case 20: back-rank checkmate against white king
    - Method(s) under test: `isCheckmate(Color)`
    - State of the system: white king at (7,0), black rook at (4,7), white pawns blocking escape on rank 1
    - Expected output: isCheckmate(WHITE) returns true
    - Implemented: yes

- Test Case 21: in check but king has an escape square
    - Method(s) under test: `isCheckmate(Color)`
    - State of the system: white king at (4,0), black rook at (4,7), no blockers on escape squares
    - Expected output: isCheckmate(WHITE) returns false because king can step away
    - Implemented: yes

- Test Case 22: isCheckmate rejects null color
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

- Test Case 23: standard setup, neither side is in stalemate
    - Method(s) under test: `isStalemate(Color)`
    - State of the system: new Game with standard starting position
    - Expected output: isStalemate(WHITE) returns false, isStalemate(BLACK) returns false
    - Implemented: yes

- Test Case 24: stalemate position, white not in check but no legal moves
    - Method(s) under test: `isStalemate(Color)`
    - State of the system: white king cornered, black king and queen creating no-legal-move position without check
    - Expected output: isStalemate(WHITE) returns true
    - Implemented: yes

- Test Case 25: isStalemate rejects null color
    - Method(s) under test: `isStalemate(Color)`
    - State of the system: new game from standard setup
    - Expected output: NullPointerException
    - Implemented: yes

- Test Case 38: white in checkmate
  - Method(s) under test: `isStalemate(Color)`
  - State of the system: white king at (4,0), black rook at (4,7), black king at (4,2)
  - Expected output: isStalemate(WHITE) returns false
  - Implemented: yes

---

## playerHasNoLegalMoves(Color color)

### Step 1 - Inputs and Outputs
- Input #1: color who we are checking for legal moves
- Output #1: boolean
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference (enum)
- Output #1: Boolean
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: true (no legal move can be made without leaving the king in check), false (a legal move can be made to bring the king out of check)
- Output #2: NullPointerException

- Test Case 39: stalemate position, white not in check but no legal moves
  - Method(s) under test: `playerHasNoLegalMoves(Color)`
  - State of the system: white king cornered, black king and queen creating no-legal-move position without check
  - Expected output: playerHasNoLegalMoves(WHITE) returns true
  - Implemented: yes

- Test Case 40: not stalemate position, white not in check with one legal move
  - Method(s) under test: `playerHasNoLegalMoves(Color)`
  - State of the system: white king cornered, black king and queen creating no-legal-move position without check
  - Expected output: playerHasNoLegalMoves(WHITE) returns false
  - Implemented: yes

---

## isThreefoldRepetition()

### Step 1 - Inputs and Outputs
- Input #1: Game history (sequence of board positions reached during play)
- Output #1: boolean 
- Output #2: exception 

### Step 2 - Data Types
- Input #1: Collection of board-state records 
- Output #1: Boolean 
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: Position occurs once, Position occurs twice, Position occurs three times, Null history (if possible)
- Output #1: true, false

### Step 4 - Test Cases

- Test Case 41: initial position is not threefold repetition
  - Method(s) under test: isThreefoldRepetition()
  - State of the system: new game, no moves played
  - Expected output: returns false
  - Implemented: no
  
- Test Case 42: position repeated twice
  - Method(s) under test: isThreefoldRepetition()
  - State of the system: players repeat moves until a position appears exactly two times
  - Expected output: returns false
  - Implemented: no
  
- Test Case 43: position repeated three times
  - Method(s) under test: isThreefoldRepetition()
  - State of the system: players repeat moves until the same board position, side-to-move, and rights occur three times
  - Expected output: returns true
  - Implemented: no
  
- Test Case 44: board layout repeats but side to move differs
  - Method(s) under test: isThreefoldRepetition()
  - State of the system: piece placement identical but different player to move
  - Expected output: returns false
  - Implemented: no
  
- Test Case 45: board layout repeats but castling/en-passant rights differ
  - Method(s) under test: isThreefoldRepetition()
  - State of the system: same piece placement but different game rights
  - Expected output: returns false
  - Implemented: no

---

## isFiftyMoveRule()

### Step 1 - Inputs and Outputs

- Input #1: Half-move counter since last pawn move or capture
- Output #1: boolean
- Output #2: exception

### Step 2 - Data Types

- Input #1: Integer
- Output #1: Boolean
- Output #2: Case

### Step 3 - Concrete Values

- Input #1: 0, 99, 100
- Output #1: true (fifty-move rule reached), false (fifty-move rule not reached)

### Step 4 - Test Cases

- Test Case 46: game starts below threshold
  - Method(s) under test: isFiftyMoveRule()
  - State of the system: new game
  - Expected output: returns false
  - Implemented: yes

- Test Case 47: ninety-nine half moves without pawn move or capture
  - Method(s) under test: isFiftyMoveRule()
  - State of the system: counter = 99
  - Expected output: returns false
  - Implemented: yes

- Test Case 48: exactly one hundred half moves without pawn move or capture
  - Method(s) under test: isFiftyMoveRule()
  - State of the system: counter = 100
  - Expected output: returns true
  - Implemented: yes

- Test Case 49: counter reset by pawn move
  - Method(s) under test: isFiftyMoveRule()
  - State of the system: counter reaches 99, then a pawn move occurs
  - Expected output: returns false
  - Implemented: yes

- Test Case 50: counter reset by capture
  - Method(s) under test: isFiftyMoveRule()
  - State of the system: counter reaches 99, then a piece is captured
  - Expected output: returns false
  - Implemented: yes