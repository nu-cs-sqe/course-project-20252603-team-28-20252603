Feature: King movement
  The king moves one square horizontally, vertically, or diagonally.

  Scenario Outline: King candidate moves from board positions
    Given a <color> king starts at <from>
    And the king board has blockers <blockers>
    When the king candidate moves are requested
    Then the king candidate moves should be <moves>

    Examples:
      | color | from | blockers | moves                                   |
      | white | 3,3  | none     | 2,2;2,3;2,4;3,2;3,4;4,2;4,3;4,4       |
