# BVA Analysis for Square

## Specification

A square represents one legal location on an 8 by 8 chess board. Files and ranks use
zero-based indexes from 0 to 7. A square outside that range is invalid. A square can also
produce an optional offset square when a relative move stays on the board.

---

## Square.of(int file, int rank)

### Step 1 - Inputs and Outputs
- Input #1: file
- Input #2: rank
- Output #1: Square object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Interval
- Input #2: Interval
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: -1, 0, 1, 6, 7, 8
- Input #2: -1, 0, 1, 6, 7, 8
- Output #1: valid Square object
- Output #2: exception

### Step 4 - Test Cases
- **TC1: create lower-left board square** ( ❌ )
	- **State of the system**: file = 0, rank = 0
	- **Expected output**: Square is created; `file()` returns 0; `rank()` returns 0

- **TC2: create upper-right board square** ( ❌ )
	- **State of the system**: file = 7, rank = 7
	- **Expected output**: Square is created; `file()` returns 7; `rank()` returns 7

- **TC3: reject file below board** ( ❌ )
	- **State of the system**: file = -1, rank = 0
	- **Expected output**: exception

- **TC4: reject rank above board** ( ❌ )
	- **State of the system**: file = 7, rank = 8
	- **Expected output**: exception

## offset(int fileDelta, int rankDelta)

### Step 1 - Inputs and Outputs
- Input #1: starting square
- Input #2: file delta
- Input #3: rank delta
- Output #1: Optional containing a Square
- Output #2: Optional.empty()

### Step 2 - Data Types
- Input #1: Reference
- Input #2: Interval
- Input #3: Interval
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: center square, edge square
- Input #2: -1, 0, 1, 2
- Input #3: -1, 0, 1, 2
- Output #1: present Square
- Output #2: empty Optional

### Step 4 - Test Cases
- **TC5: offset from center stays on board** ( ❌ )
	- **State of the system**: starting square = (3, 3), fileDelta = 2, rankDelta = 1
	- **Expected output**: Optional contains square (5, 4)

- **TC6: offset from edge leaves board** ( ❌ )
	- **State of the system**: starting square = (0, 0), fileDelta = -1, rankDelta = 0
	- **Expected output**: Optional.empty()

## equals(Object other)

### Step 4 - Test Cases
- **TC7: equal squares have same coordinates** ( ❌ )
	- **State of the system**: first square = (4, 4), second square = (4, 4)
	- **Expected output**: `equals()` returns true and hash codes match

- **TC8: different squares have different coordinates** ( ❌ )
	- **State of the system**: first square = (4, 4), second square = (4, 5)
	- **Expected output**: `equals()` returns false
