Feature: Queen movement
  The queen moves horizontally, vertically, or diagonally until it reaches the board edge or a blocking piece.

  Scenario Outline: Queen candidate moves from board positions
    Given a <color> queen starts at <from>
    And the queen board has blockers <blockers>
    When the queen candidate moves are requested
    Then the queen candidate moves should be <moves>

    Examples:
      | color | from | blockers | moves                                                                                                                                                 |
      | white | 3,3  | none     | 0,0;0,3;0,6;1,1;1,3;1,5;2,2;2,3;2,4;3,0;3,1;3,2;3,4;3,5;3,6;3,7;4,2;4,3;4,4;5,1;5,3;5,5;6,0;6,3;6,6;7,3;7,7 |
      | white | 0,0  | none     | 0,1;0,2;0,3;0,4;0,5;0,6;0,7;1,0;1,1;2,0;2,2;3,0;3,3;4,0;4,4;5,0;5,5;6,0;6,6;7,0;7,7                         |
      | white | 0,3  | none     | 0,0;0,1;0,2;0,4;0,5;0,6;0,7;1,2;1,3;1,4;2,1;2,3;2,5;3,0;3,3;3,6;4,3;4,7;5,3;6,3;7,3                         |
