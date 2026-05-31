Feature: Rook movement
  The rook moves horizontally or vertically until it reaches the board edge or a blocking piece.

  Scenario Outline: Rook candidate moves from board positions
    Given a <color> rook starts at <from>
    And the rook board has blockers <blockers>
    When the rook candidate moves are requested
    Then the rook candidate moves should be <moves>

    Examples:
      | color | from | blockers | moves                                                 |
      | white | 3,3  | none     | 0,3;1,3;2,3;3,0;3,1;3,2;3,4;3,5;3,6;3,7;4,3;5,3;6,3;7,3 |
      | white | 0,0  | none     | 0,1;0,2;0,3;0,4;0,5;0,6;0,7;1,0;2,0;3,0;4,0;5,0;6,0;7,0 |
      | white | 0,3  | none     | 0,0;0,1;0,2;0,4;0,5;0,6;0,7;1,3;2,3;3,3;4,3;5,3;6,3;7,3 |
