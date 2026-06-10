Feature: Draw rules
  Games can be automatically drawn when neither side has sufficient mating material.

  Scenario Outline: Insufficient material detection
    Given a draw rules board with <pieces>
    When insufficient material is checked
    Then insufficient material should be <result>

    Examples:
      | pieces | result |

  Scenario Outline: Draw status after a move
    Given a draw rules board with <pieces>
    When a draw rules move is made from <from> to <to>
    Then game status should be <status>

    Examples:
      | pieces | from | to | status |
