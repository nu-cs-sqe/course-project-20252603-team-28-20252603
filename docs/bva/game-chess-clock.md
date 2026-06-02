# BVA Analysis for Game ChessClock Integration

## Specification

A Game can take a ChessClock so we can tell who runss out of time. When the game is built, the clock starts on white. If a players time hits zero, the other color wins. winnerByTimeout returns the winning color, or empty if no one has timed out yet.

---

## Game(Board board, ChessClock clock)

### Step 1 - Inputs and Outputs
- Input #1: board
- Input #2: chess clock
- Output #1: Game object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference
- Input #2: Reference
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: board, null
- Input #2: chess clock, null
- Output #1: valid Game object
- Output #2: NullPointerException

### Step 4 - Test Cases

- Test Case 22: new Game with valid board and clock starts with white clock running
    - Method(s) under test: `Game(Board, ChessClock)`
    - State of the system: standard setup board, fresh clock with 5 minute time control
    - Expected output: Game is created, clock running for white
    - Implemented: no

- Test Case 23: null clock in constructor throws
    - Method(s) under test: `Game(Board, ChessClock)`
    - State of the system: valid board, clock = null
    - Expected output: NullPointerException
    - Implemented: no

---

## winnerByTimeout()

### Step 1 - Inputs and Outputs
- Output #1: Optional<Color> representing winner by timeout, or empty

### Step 2 - Data Types
- Output #1: Reference (Optional)

### Step 3 - Concrete Values
- Output #1: empty, Optional.of(WHITE), Optional.of(BLACK)

### Step 4 - Test Cases

- Test Case 24: clock not expired returns empty
    - Method(s) under test: `winnerByTimeout()`
    - State of the system: new Game, clock has time remaining
    - Expected output: Optional.empty()
    - Implemented: no

- Test Case 25: white clock expired returns BLACK as winner
    - Method(s) under test: `winnerByTimeout()`
    - State of the system: clock advanced past white's time
    - Expected output: Optional.of(BLACK)
    - Implemented: no

- Test Case 26: black clock expired returns WHITE as winner
    - Method(s) under test: `winnerByTimeout()`
    - State of the system: clock advanced past black's time
    - Expected output: Optional.of(WHITE)
    - Implemented: no
