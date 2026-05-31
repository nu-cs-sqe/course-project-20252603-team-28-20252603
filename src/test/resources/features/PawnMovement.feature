Feature: Pawn movement
  The pawn moves forward and captures diagonally toward the opponent side.

  Scenario Outline: Pawn candidate moves from board positions
    Given a <color> pawn starts at <from>
    And the pawn board has blockers <blockers>
    When the pawn candidate moves are requested
    Then the pawn candidate moves should be <moves>

    Examples:
      | color | from | blockers | moves |
      | white | 3,3  | none     | 3,4   |
      | black | 3,3  | none     | 3,2   |
