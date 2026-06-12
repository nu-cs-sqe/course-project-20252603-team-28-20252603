# Mutation Testing

pitest runs on the domain classes. the surviving mutants are listed below with why
they survive. everything else is killed.

## Equivalent mutants (cannot be killed, same behavior)

- Game.isLightSquare, addition replaced with subtraction. (file+rank)%2 and
  (file-rank)%2 give the same parity for every square.
- Game.isLightSquare, negated conditional. symmetric, same result.