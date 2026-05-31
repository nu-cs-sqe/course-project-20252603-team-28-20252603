Feature: Rook movement
  The rook moves horizontally or vertically until it reaches the board edge or a blocking piece.

  Scenario Outline: Rook candidate moves from board positions
    Given a <color> rook starts at <from>
    And the rook board has blockers <blockers>
    When the rook candidate moves are requested
    Then the rook candidate moves should be <moves>

    Examples:
      | color | from | blockers | moves |
