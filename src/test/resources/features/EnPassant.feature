Feature: En passant
  En passant is available only immediately after an adjacent opponent pawn moves two squares.

  Scenario Outline: En passant legal move availability
    Given an en passant board with <pieces>
    And the en passant move sequence <moves>
    When legal moves are requested from <from>
    Then the legal moves should <expectation> <target>

    Examples:
      | pieces | moves | from | expectation | target |

  Scenario Outline: En passant capture execution
    Given an en passant board with <pieces>
    And the en passant move sequence <moves>
    When the en passant move is made from <from> to <target>
    Then the board should have <expected>

    Examples:
      | pieces | moves | from | target | expected |
