# BVA Analysis for Castling

## Specification

Castling moves the king two squares twoard a rook and the rook hops to the square the king crossed. Allowd only if the king and that rook are still on their startign squares, the squares betwen them are empty, the king isnt in check, and the king doesnt move through or onto an attacked squrae.

---

## canCastle(Color color, CastlingSide side)

Inputs: color (WHITE, BLACK, null), side (KINGSIDE, QUEENSIDE, null). Output: boolean, or NullPointerException on null input.

- Test Case 1: white kingside allowed, clear back rank
  - State of the system: white king (4,0), white rook (7,0), squares betwen empty, not in check
  - Expected output: true
  - Implemented: yes
- Test Case 2: white queenside allowed, clear back rank
  - State of the system: white king (4,0), white rook (0,0), squares betwen empty
  - Expected output: true
  - Implemented: yes
- Test Case 3: black kingside allowed
  - State of the system: black king (4,7), black rook (7,7), squares betwen empty
  - Expected output: true
  - Implemented: yes
- Test Case 4: king not on its home square
  - State of the system: nothing at (4,0)
  - Expected output: false
  - Implemented: yes
- Test Case 5: no rook on the rook square
  - State of the system: white king (4,0), nothing at (7,0)
  - Expected output: false
  - Implemented: yes
- Test Case 6: wrong piece type on the rook square
  - State of the system: white king (4,0), white bishop at (7,0)
  - Expected output: false
  - Implemented: yes
- Test Case 7: rook is the wrong color
  - State of the system: white king (4,0), black rook at (7,0)
  - Expected output: false
  - Implemented: yes
- Test Case 8: kingside square between occupied
  - State of the system: white king (4,0), white rook (7,0), a piece at (5,0)
  - Expected output: false
  - Implemented: yes
- Test Case 9: queenside b-file square occupied
  - State of the system: white king (4,0), white rook (0,0), a piece at (1,0)
  - Expected output: false
  - Implemented: yes
- Test Case 10: king currently in check
  - State of the system: white king (4,0), black rook attacking down file 4
  - Expected output: false
  - Implemented: yes
- Test Case 11: king passes through an attacked square
  - State of the system: white king (4,0), black rook attacking (5,0)
  - Expected output: false
  - Implemented: yes
- Test Case 12: null color
  - State of the system: color = null, side = KINGSIDE
  - Expected output: exception
  - Implemented: yes
- Test Case 13: null side
  - State of the system: color = WHITE, side = null
  - Expected output: exception
  - Implemented: yes

---

## legalMovesFrom(Square from) for castling

Inputs: the kings square. Output: the legal destination squares, which include the castle square when castling is allowed.

- Test Case 14: white king legal moves include the castle squares when allowed
  - State of the system: white king (4,0), both white rooks home, back rank clear
  - Expected output: legal moves contain (6,0) and (2,0)
  - Implemented: yes
- Test Case 15: black king legal moves include the castle squares when allowed
  - State of the system: black king (4,7), both black rooks home, back rank clear
  - Expected output: legal moves contain (6,7) and (2,7)
  - Implemented: yes
- Test Case 16: castle squares excluded when not allowed
  - State of the system: white king (4,0), no rooks on the back rank
  - Expected output: legal moves do not contain (6,0) or (2,0)
  - Implemented: yes

---

## makeMove(Square from, Square to) for castling

Inputs: the kings square, and a square two files away. Output: king and rook relocated, or an exception.

- Test Case 17: white kingside castle moves king and rook
  - State of the system: legal white kingside position, makeMove (4,0) to (6,0)
  - Expected output: king at (6,0), rook at (5,0)
  - Implemented: yes
- Test Case 18: white queenside castle moves king and rook
  - State of the system: legal white queenside position, makeMove (4,0) to (2,0)
  - Expected output: king at (2,0), rook at (3,0)
  - Implemented: yes
- Test Case 19: castling move rejected when not allowed
  - State of the system: a piece blocks (5,0), makeMove (4,0) to (6,0)
  - Expected output: exception
  - Implemented: yes
- Test Case 20: king moving one square is a normal move
  - State of the system: white king (4,0), makeMove (4,0) to (4,1)
  - Expected output: move is applied and the turn passes to black
  - Implemented: yes
