# BVA Analysis for Draw Rules

## Specification

The game can end in a draw for insufficient material when neither side has enough
pieces to force checkmate. Stalemate remains represented separately as
`GameStatus.STALEMATE`.

---

## isInsufficientMaterial()

### Step 1 - Inputs and Outputs
- Input #1: current board state
- Output #1: boolean

### Step 2 - Data Types
- Input #1: Reference
- Output #1: Boolean

### Step 3 - Concrete Values
- Board state: kings only
- Output: true

### Step 4 - Test Cases
- **TC1: king versus king is insufficient material** ( ✅ )
	- **State of the system**: white king and black king are the only pieces
	- **Expected output**: `isInsufficientMaterial()` returns true
