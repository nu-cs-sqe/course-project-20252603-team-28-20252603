# Mutation Testing

pitest runs on the domain classes. the surviving mutants are listed below with why
they survive. everything else is killed.

## Equivalent mutants (cannot be killed, same behavior)

- Game.isLightSquare, addition replaced with subtraction. (file+rank)%2 and
  (file-rank)%2 give the same parity for every square.
- Game.isLightSquare, negated conditional. symmetric, same result.
- Game.performCastle, conditional boundary. only legal king targets are file 6 and
  file 2, so the boundary change still picks the same side.

## Mock limited mutants (Board is mocked, moves checked by interaction not state)

- Game.canCastle, removed Board::move call.
- Game.performCastle, removed Board::move call.
- Game.makeMove, removed applyEnPassant call.
- Game.kingPath, return replaced with empty list.
