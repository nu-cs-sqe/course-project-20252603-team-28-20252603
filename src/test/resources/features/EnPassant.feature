Feature: En passant
  En passant is available only immediately after an adjacent opponent pawn moves two squares.

  Scenario Outline: En passant legal move availability
    Given an en passant board with <pieces>
    And the en passant move sequence <moves>
    When legal moves are requested from <from>
    Then the legal moves should <expectation> <target>

    Examples:
      | pieces                         | moves             | from | expectation | target |
      | WK@0,0;BK@7,7;WP@4,4;BP@5,6   | 0,0>0,1;5,6>5,4 | 4,4  | include     | 5,5    |
      | WK@0,0;BK@7,7;BP@4,3;WP@5,1   | 5,1>5,3         | 4,3  | include     | 5,2    |
      | WK@0,0;BK@7,7;WP@4,4;BP@5,5   | 0,0>0,1;5,5>5,4 | 4,4  | exclude     | 5,5    |
      | WK@0,0;BK@7,7;WP@4,4;BP@5,6   | 0,0>0,1;5,6>5,4;0,1>0,2 | 4,4 | exclude | 5,5 |

  Scenario Outline: En passant capture execution
    Given an en passant board with <pieces>
    And the en passant move sequence <moves>
    When the en passant move is made from <from> to <target>
    Then the board should have <expected>

    Examples:
      | pieces                         | moves             | from | target | expected                         |
      | WK@0,0;BK@7,7;WP@4,4;BP@5,6   | 0,0>0,1;5,6>5,4 | 4,4  | 5,5    | WP@5,5;empty@4,4;empty@5,4     |
