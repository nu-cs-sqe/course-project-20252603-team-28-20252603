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
- Board state: kings only, king and one bishop versus king,
  king and one knight versus king, bishops only on one square color
- Output: true

### Step 4 - Test Cases
- **TC1: king versus king is insufficient material** ( ✅ )
	- **State of the system**: white king and black king are the only pieces
	- **Expected output**: `isInsufficientMaterial()` returns true

- **TC2: king and one bishop versus king is insufficient material** ( ✅ )
	- **State of the system**: white king, white bishop, and black king are the only pieces
	- **Expected output**: `isInsufficientMaterial()` returns true

- **TC3: king and one knight versus king is insufficient material** ( ✅ )
	- **State of the system**: white king, white knight, and black king are the only pieces
	- **Expected output**: `isInsufficientMaterial()` returns true

- **TC4: same-color bishops are insufficient material** ( ✅ )
	- **State of the system**: both sides have only a king and bishops, and every
	  bishop is on the same square color
	- **Expected output**: `isInsufficientMaterial()` returns true
