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
      | white | 3,1  | none     | 3,2;3,3 |
      | white | 3,3  | B@3,4    | none   |
      | white | 3,1  | B@3,3    | 3,2    |
      | white | 3,3  | B@4,4    | 3,4;4,4 |
      | white | 3,3  | W@4,4    | 3,4     |
      | white | 0,3  | B@1,4    | 0,4;1,4 |
      | white | 3,3  | B@2,4    | 3,4;2,4 |
      | white | 3,1  | B@3,2    | none    |

  Scenario: Pawn rejects null source square
    Given a white pawn starts at 3,3
    When the pawn candidate moves are requested with a null source
    Then the pawn movement request should fail with a null pointer exception

  Scenario: Pawn rejects null board
    Given a white pawn starts at 3,3
    When the pawn candidate moves are requested with a null board
    Then the pawn movement request should fail with a null pointer exception
