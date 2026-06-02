# BVA Analysis for Game Status and Resign

## Specification

A Game tracks whose turn it is and what state the game is in. The game starts IN_PROGRESS. A player can resign at any time. When white resigns, black wins. When black resigns, white wins. Once a game is no longer IN_PROGRESS, no more moves are alllowed.

---

## getStatus()

### Step 1 - Inputs and Outputs
- Output #1: current GameStatus

### Step 2 - Data Types
- Output #1: Reference (enum)

### Step 3 - Concrete Values
- Output #1: IN_PROGRESS, WHITE_WIN, BLACK_WIN

### Step 4 - Test Cases
- Test Case 6: new game starts IN_PROGRESS
    - Method(s) under test: `getStatus()`
    - State of the system: new Game with standard setup
    - Expected output: IN_PROGRESS
    - Implemented: yes
---

## resign(Color resigningColor)

### Step 1 - Inputs and Outputs
- Input #1: color of the resigning player
- Output #1: game status updated to opposite color's win
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference (enum)
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: status set
- Output #2: NullPointerException

### Step 4 - Test Cases
- Test Case 7: resign WHITE sets status to BLACK_WIN
    - Method(s) under test: `resign(Color)`, `getStatus()`
    - State of the system: new game, white resigns
    - Expected output: status = BLACK_WIN
    - Implemented: yes

- Test Case 8: resign BLACK sets status to WHITE_WIN
    - Method(s) under test: `resign(Color)`, `getStatus()`
    - State of the system: new game, black resigns
    - Expected output: status = WHITE_WIN
    - Implemented: yes

- Test Case 9: resign with null color throws
    - Method(s) under test: `resign(Color)`
    - State of the system: new game, null passed
    - Expected output: NullPointerException
    - Implemented: yes

---

## makeMove side effect after game ends

### Step 1 - Inputs and Outputs
- Input #1: from square, to square
- Output #1: exception if game not IN_PROGRESS

### Step 2 - Data Types
- Input #1: Reference, Reference
- Output #1: Case

### Step 3 - Concrete Values
- Input #1: any from/to pair
- Output #1: IlegalStateException

### Step 4 - Test Cases
- Test Case 10: makeMove throws when game not IN_PROGRESS
    - Method(s) under test: `makeMove(Square, Square)`
    - State of the system: after white resigns, white tries to move
    - Expected output: IlegalStateException
    - Implemented: yes
