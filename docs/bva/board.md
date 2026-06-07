# BVA Analysis for Board

## Specification

A board stores pieces by square. A new board starts empty. A standard setup board starts
with 32 pieces: 16 white pieces on ranks 0 and 1, and 16 black pieces on ranks 6 and 7.

Treat the state of the board as an implicit input described in state of the system.

---

## Board()

### Step 1 - Inputs and Outputs

* Input #1: none
* Output #1: empty Board

### Step 2 - Data Types

* Input #1: N/A
* Output #1: Board

### Step 3 - Concrete Values

* Input #1: N/A
* Output #1: Board with no occupied squares

### Step 4 - Test Cases
- **TC1: new board starts empty** ( ✅ )
	- **State of the system**: new Board
	- **Expected output**: no square has a piece; `occupiedSquaresOf(WHITE)` and `occupiedSquaresOf(BLACK)` are empty

## pieceAt(Square square)

### Step 1 - Inputs and Outputs

* Input #1: square
* Output #1: Optional containing piece
* Output #2: Optional.empty()

### Step 2 - Data Types

* Input #1: Square
* Output #1: Optional<Piece>
* Output #2: Optional<Piece>

### Step 3 - Concrete Values

* Input #1: occupied square (1,0), empty square (0,0)
* Output #1: Optional.of(knight)
* Output #2: Optional.empty()

### Step 4 - Test Cases
- **TC2: empty square has no piece** ( ✅ )
	- **State of the system**: new Board, square = (0, 0)
	- **Expected output**: Optional.empty()

- **TC3: occupied square returns placed piece** ( ✅ )
	- **State of the system**: white knight placed at square = (1, 0)
	- **Expected output**: Optional.of(knight)

## place(Square square, Piece piece)

### Step 1 - Inputs and Outputs

* Input #1: square
* Input #2: piece
* Output #1: piece placed successfully
* Output #2: exception

### Step 2 - Data Types

* Input #1: Square
* Input #2: Piece
* Output #1: Board state
* Output #2: NullPointerException

### Step 3 - Concrete Values

* Input #1: (1,0), null
* Input #2: white knight, null
* Output #1: square occupied by white knight
* Output #2: NullPointerException

### Step 4 - Test Cases
- **TC4: place piece on empty square** ( implemented in TC3 )
	- **State of the system**: new Board, square = (1, 0), piece = white knight
	- **Expected output**: `pieceAt(square)` returns the white knight

- **TC5: reject null square** ( ✅ )
	- **State of the system**: square = null, piece = white knight
	- **Expected output**: exception

- **TC6: reject null piece** ( ✅ )
	- **State of the system**: square = (1, 0), piece = null
	- **Expected output**: exception

## remove(Square square)

### Step 1 - Inputs and Outputs

* Input #1: square
* Output #1: removed piece
* Output #2: Optional.empty()

### Step 2 - Data Types

* Input #1: Square
* Output #1: Optional<Piece>
* Output #2: Optional<Piece>

### Step 3 - Concrete Values

* Input #1: occupied square (1,0), empty square (1,0)
* Output #1: Optional containing white knight
* Output #2: Optional.empty()

### Step 4 - Test Cases
- **TC7: remove occupied square** ( ✅ )
	- **State of the system**: white knight placed at square = (1, 0)
	- **Expected output**: removed Optional contains the white knight; square becomes empty

- **TC8: remove empty square** ( ✅ )
	- **State of the system**: new Board, square = (1, 0)
	- **Expected output**: Optional.empty()

## move(Square from, Square to)

### Step 1 - Inputs and Outputs

* Input #1: source square
* Input #2: destination square
* Output #1: piece moved successfully
* Output #2: exception

### Step 2 - Data Types

* Input #1: Square
* Input #2: Square
* Output #1: Board state
* Output #2: IllegalArgumentException

### Step 3 - Concrete Values

* Input #1: (1,0), (2,2)
* Input #2: occupied destination, empty destination
* Output #1: piece moved or captured
* Output #2: IllegalStateException

### Step 4 - Test Cases
- **TC9: move piece to empty square** ( ✅ )
	- **State of the system**: white knight at (1, 0), destination = (2, 2)
	- **Expected output**: source becomes empty; destination contains the white knight

- **TC10: move piece to occupied square captures destination piece** ( ✅ )
	- **State of the system**: white knight at (1, 0), black pawn at (2, 2)
	- **Expected output**: destination contains the white knight; source becomes empty

- **TC11: reject move from empty square** ( ✅ )
	- **State of the system**: new Board, source = (1, 0), destination = (2, 2)
	- **Expected output**: exception

## occupiedSquaresOf(Color color)

### Step 1 - Inputs and Outputs

* Input #1: color
* Output #1: set of occupied squares

### Step 2 - Data Types

* Input #1: Color
* Output #1: Set<Square>

### Step 3 - Concrete Values

* Input #1: Color.WHITE, Color.BLACK
* Output #1: {Square.of(1, 0)}, {Square.of(2, 2)}

### Step 4 - Test Cases
- **TC12: only return squares occupied by requested color** ( ✅ )
	- **State of the system**: white knight at (1, 0), black pawn at (2, 2)
	- **Expected output**: white occupied squares contains only (1, 0); black occupied squares contains only (2, 2)

## copy()

### Step 1 - Inputs and Outputs

* Input #1: board state
* Output #1: copied board

### Step 2 - Data Types

* Input #1: Board
* Output #1: Board

### Step 3 - Concrete Values

* Input #1: board containing white knight at (1,0)
* Output #1: independent copy containing white knight at (1,0)

### Step 4 - Test Cases
- **TC13: copied board is independent of original board** ( ✅ )
	- **State of the system**: original board has white knight at (1, 0); copy is created; original removes the knight
	- **Expected output**: copy still contains the white knight at (1, 0)

## findKing(Color color)

### Step 1 - Inputs and Outputs

* Input #1: color
* Output #1: king square
* Output #2: exception

### Step 2 - Data Types

* Input #1: Color
* Output #1: Square
* Output #2: IllegalStateException

### Step 3 - Concrete Values

* Input #1: WHITE, BLACK
* Output #1: (4,0), (4,7)
* Output #2: IllegalStateException

### Step 4 - Test Cases
- **TC14: find king in standard setup** ( ✅ )
	- **State of the system**: standard setup board
	- **Expected output**: white king is at (4, 0); black king is at (4, 7)

- **TC15: reject missing king** ( ✅ )
	- **State of the system**: new empty Board
	- **Expected output**: exception

## Board.standardSetup()

### Step 1 - Inputs and Outputs

* Input #1: none
* Output #1: standard chess board

### Step 2 - Data Types

* Input #1: N/A
* Output #1: Board

### Step 3 - Concrete Values

* Input #1: N/A
* Output #1: board containing 32 correctly positioned pieces

### Step 4 - Test Cases
- **TC16: standard setup contains correct total piece counts** ( ✅ )
	- **State of the system**: standard setup board
	- **Expected output**: white has 16 occupied squares; black has 16 occupied squares

- **TC17: standard setup contains correct back-rank pieces** ( ✅ )
	- **State of the system**: standard setup board
	- **Expected output**: rooks, knights, bishops, queen, and king are on the correct starting squares

- **TC18: standard setup contains correct pawn ranks** ( ✅ )
	- **State of the system**: standard setup board
	- **Expected output**: white pawns occupy rank 1; black pawns occupy rank 6
