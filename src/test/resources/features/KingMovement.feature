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
      | white | 0,0  | none     | 0,1;1,0;1,1                             |
      | white | 0,3  | none     | 0,2;0,4;1,2;1,3;1,4                     |
      | white | 3,3  | W@4,4    | 2,2;2,3;2,4;3,2;3,4;4,2;4,3             |
      | white | 3,3  | B@4,4    | 2,2;2,3;2,4;3,2;3,4;4,2;4,3;4,4       |
