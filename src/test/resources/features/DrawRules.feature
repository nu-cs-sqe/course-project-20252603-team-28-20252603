Feature: Draw rules
  Games can be automatically drawn when neither side has sufficient mating material.

  Scenario Outline: Insufficient material detection
    Given a draw rules board with <pieces>
    When insufficient material is checked
    Then insufficient material should be <result>

    Examples:
      | pieces        | result |
      | WK@0,0;BK@7,7 | true   |
      | WK@0,0;BK@7,7;WB@2,2 | true |
      | WK@0,0;BK@7,7;WN@2,2 | true |
      | WK@0,0;BK@7,7;WB@2,0;BB@4,2 | true |
      | WK@0,0;BK@7,7;WB@2,0;BB@5,2 | false |
      | WK@0,0;BK@7,7;WP@1,1 | false |
      | WK@0,0;BK@7,7;WR@1,1 | false |
      | WK@0,0;BK@7,7;WQ@1,1 | false |

  Scenario Outline: Draw status after a move
    Given a draw rules board with <pieces>
    When a draw rules move is made from <from> to <to>
    Then game status should be <status>

    Examples:
      | pieces | from | to | status |
