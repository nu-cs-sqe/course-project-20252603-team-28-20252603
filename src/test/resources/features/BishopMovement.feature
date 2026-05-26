Feature: Bishop movement
  The bishop moves diagonally until it reaches the board edge or a blocking piece.

  Scenario Outline: Bishop candidate moves from board positions
    Given a <color> bishop starts at <from>
    And the bishop board has blockers <blockers>
    When the bishop candidate moves are requested
    Then the bishop candidate moves should be <moves>

    Examples:
      | color | from | blockers | moves                                                     |
      | white | 3,3  | none     | 0,0;0,6;1,1;1,5;2,2;2,4;4,2;4,4;5,1;5,5;6,0;6,6;7,7 |
      | white | 0,0  | none     | 1,1;2,2;3,3;4,4;5,5;6,6;7,7                         |
      | white | 0,3  | none     | 1,2;1,4;2,1;2,5;3,0;3,6;4,7                         |
      | white | 3,3  | W@5,5    | 0,0;0,6;1,1;1,5;2,2;2,4;4,2;4,4;5,1;6,0             |
      | white | 3,3  | B@5,5    | 0,0;0,6;1,1;1,5;2,2;2,4;4,2;4,4;5,1;5,5;6,0         |
