Feature: Bishop movement
  The bishop moves diagonally until it reaches the board edge or a blocking piece.

  Scenario Outline: Bishop candidate moves from board positions
    Given a <color> bishop starts at <from>
    And the bishop board has blockers <blockers>
    When the bishop candidate moves are requested
    Then the bishop candidate moves should be <moves>

    Examples:
      | color | from | blockers | moves |
