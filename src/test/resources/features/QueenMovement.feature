Feature: Queen movement
  The queen moves horizontally, vertically, or diagonally until it reaches the board edge or a blocking piece.

  Scenario Outline: Queen candidate moves from board positions
    Given a <color> queen starts at <from>
    And the queen board has blockers <blockers>
    When the queen candidate moves are requested
    Then the queen candidate moves should be <moves>

    Examples:
      | color | from | blockers | moves |
