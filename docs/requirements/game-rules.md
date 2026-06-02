# Game Rules

The rules implemented in this project. Matches the README scope.

## Board

Standard 8x8 board. White at the bottom (rows 0-1), black at the top (rows 6-7). Squares are 0-indexed in code (`(row, col)` from `(0,0)` to `(7,7)`).

## Pieces

Six piece types, both colors:
- Pawn: moves 1 forward, 2 from starting row, captures diagonally
- Rook: any number of squares orthogonal, stops on first piece
- Knight: L-shape (2+1), can jump over pieces
- Bishop: any number of squares diagonal, stops on first piece
- Queen: rook + bishop moves combined
- King: 1 square in any direction

All pieces can capture an opposing piece by landing on its square. None can land on a friendly piece.

## Turn order

White moves first. Players alternate. Only one move per turn.

## Check

A king is in check if an opposing piece could capture it on the next move. The player in check has to escape it on the next move. Options are moving the king, blocking, or capturing whatever's attacking.

## Checkmate

If the player in check has no legal move to escape, that's checkmate. The other side wins.

## Stalemate

If the player to move is NOT in check but has no legal moves, that's stalemate. The game is a draw.

## Pawn promotion

When a pawn reaches the back rank (row 7 for white, row 0 for black), the player picks Queen, Rook, Bishop, or Knight. The pawn is replaced with the chosen piece, same color.

## Chess clock

Both sides start with the same configurable time. The active player's clock ticks down. The other side's clock pauses. If your clock hits zero, you lose.

## Out of scope

Castling, en passant, threefold repetition, fifty-move rule, and insufficient material draws are not implemented.
