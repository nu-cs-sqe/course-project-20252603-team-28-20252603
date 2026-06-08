# BVA Analysis for Game

## Specification

A Game tracks the Board and whose turn it is. Players take turns moving. After a move, it's the other player's turn. The Game blocks moves from empty squares, moves of the opponent's pieces, and null inputs. This version doesn't handle piece-specific rules like the knight L-shape or check/checkmate.

---

## Game(Board board)

### Step 1 - Inputs and Outputs
- Input #1: starting board
- Output #1: Game object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Reference
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: null, valid Board
- Output #1: valid Game with WHITE to move
- Output #2: NullPointerException

### Step 4 - Test Cases
- **TC1: new game starts with WHITE to move** ( ✅ )
    - **State of the system**: standard starting board
    - **Expected output**: Game is created; `currentTurn()` returns WHITE
- **TC2: null board throws NullPointerException** ( ✅ )
    - **State of the system**: board = null
    - **Expected output**: NullPointerException

---

## makeMove(Square from, Square to)

### Step 1 - Inputs and Outputs
- Input #1: from square
- Input #2: to square
- Input #3: current turn
- Output #1: updated board state
- Output #2: flipped turn
- Output #3: exception

### Step 2 - Data Types
- Input #1: Reference
- Input #2: Reference
- Input #3: Case
- Output #1: Reference
- Output #2: Case
- Output #3: Case

### Step 3 - Concrete Values
- Input #1: square with current player's piece, square with opponent piece, empty square, null
- Input #2: any board square, null
- Output #1: piece moved from `from` to `to`
- Output #2: turn flipped to opposite color
- Output #3: IllegalStateException or NullPointerException

### Step 4 - Test Cases
- **TC3: white moves piece, turn flips to BLACK** ( ✅ )
    - **State of the system**: standard starting board, WHITE to move, move knight b1 to c3
    - **Expected output**: knight is at c3, b1 is empty, currentTurn() returns BLACK
- **TC4: moving from empty square throws** ( ✅ )
    - **State of the system**: standard board, move from e4 (empty) to e5
    - **Expected output**: IllegalStateException
- **TC5: moving opponent's piece throws** ( ✅ )
    - **State of the system**: standard board, WHITE to move, move from b8 (black knight) to c6
    - **Expected output**: IllegalStateException
- **TC6: null from throws NullPointerException** ( ✅ )
    - **State of the system**: from = null
    - **Expected output**: NullPointerException
- **TC7: null to throws NullPointerException** ( ✅ )
    - **State of the system**: to = null
    - **Expected output**: NullPointerException
