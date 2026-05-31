Feature: Knight movement
  The knight moves in an L shape and can jump over pieces.

  Scenario Outline: Knight candidate moves from board positions
    Given a <color> knight starts at <from>
    And the board has blockers <blockers>
    When the knight candidate moves are requested
    Then the knight candidate moves should be <moves>

    Examples:
      | color | from | blockers | moves                                   |
      | white | 3,3  | none     | 1,2;1,4;2,1;2,5;4,1;4,5;5,2;5,4       |
      | white | 0,0  | none     | 1,2;2,1                                 |
      | white | 0,3  | none     | 1,1;1,5;2,2;2,4                         |
      | white | 3,3  | W@4,5    | 1,2;1,4;2,1;2,5;4,1;5,2;5,4           |
      | white | 3,3  | B@4,5    | 1,2;1,4;2,1;2,5;4,1;4,5;5,2;5,4       |
      | white | 3,3  | W@3,4;B@3,2 | 1,2;1,4;2,1;2,5;4,1;4,5;5,2;5,4    |
