# BVA Analysis for Chess Clock

## Specification

The chess clock gives both players a starting amount of time. Only the current player's time goes down. After a player makes a move, their clock stops, their increment is added, and the other player's clock starts. If a player reaches 0 seconds, that player loses by timeout.

---

## TimeControl(Duration startingTime, Duration increment)

### Step 1 - Inputs and Outputs
- Input #1: starting time
- Input #2: increment
- Output #1: TimeControl object
- Output #2: exception

### Step 2 - Data Types
- Input #1: Numbers
- Input #2: Numbers
- Output #1: Reference
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: null, negative duration, 0 seconds, 1 second, 5 minutes
- Input #2: null, negative duration, 0 seconds, 1 second, 3 seconds
- Output #1: valid TimeControl object
- Output #2: exception

### Step 4 - Test Cases
- Test Case 1: normal clock with no increment
    - Method(s) under test: `TimeControl(Duration startingTime, Duration increment)`
    - State of the system: starting time = 5 minutes, increment = 0 seconds
    - Expected output: TimeControl is created
    - Implemented: no

- Test Case 2: normal clock with increment
    - Method(s) under test: `TimeControl(Duration startingTime, Duration increment)`
    - State of the system: starting time = 5 minutes, increment = 3 seconds
    - Expected output: TimeControl is created
    - Implemented: no

- Test Case 3: one second starting time
    - Method(s) under test: `TimeControl(Duration startingTime, Duration increment)`
    - State of the system: starting time = 1 second, increment = 0 seconds
    - Expected output: TimeControl is created
    - Implemented: no

- Test Case 4: zero starting time
    - Method(s) under test: `TimeControl(Duration startingTime, Duration increment)`
    - State of the system: starting time = 0 seconds, increment = 0 seconds
    - Expected output: exception
    - Implemented: no

- Test Case 5: negative starting time
    - Method(s) under test: `TimeControl(Duration startingTime, Duration increment)`
    - State of the system: starting time = -1 second, increment = 0 seconds
    - Expected output: exception
    - Implemented: no

- Test Case 6: negative increment
    - Method(s) under test: `TimeControl(Duration startingTime, Duration increment)`
    - State of the system: starting time = 5 minutes, increment = -1 second
    - Expected output: exception
    - Implemented: no

- Test Case 7: null starting time
    - Method(s) under test: `TimeControl(Duration startingTime, Duration increment)`
    - State of the system: starting time = null, increment = 0 seconds
    - Expected output: exception
    - Implemented: no

- Test Case 8: null increment
    - Method(s) under test: `TimeControl(Duration startingTime, Duration increment)`
    - State of the system: starting time = 5 minutes, increment = null
    - Expected output: exception
    - Implemented: no

---

## ChessClock(TimeControl control, Clock clock)

### Step 1 - Inputs and Outputs
- Input #1: time control
- Input #2: clock
- Output #1: ChessClock object
- Output #2: remaining time for each player
- Output #3: exception

### Step 2 - Data Types
- Input #1: Reference
- Input #2: Reference
- Output #1: Reference
- Output #2: Collection
- Output #3: Case

### Step 3 - Concrete Values
- Input #1: null, valid TimeControl
- Input #2: null, valid Clock
- Output #1: valid ChessClock object
- Output #2: both players have starting time
- Output #3: exception

### Step 4 - Test Cases
- Test Case 9: create clock with valid inputs
    - Method(s) under test: `ChessClock(TimeControl control, Clock clock)`, `remaining(Color color)`
    - State of the system: TimeControl has 5 minute starting time and 0 second increment; Clock is a valid fixed clock
    - Expected output: ChessClock is created; white and black both have 5 minutes remaining
    - Implemented: no

- Test Case 10: null time control
    - Method(s) under test: `ChessClock(TimeControl control, Clock clock)`
    - State of the system: TimeControl is null; Clock is valid
    - Expected output: exception
    - Implemented: no

- Test Case 11: null clock
    - Method(s) under test: `ChessClock(TimeControl control, Clock clock)`
    - State of the system: TimeControl is valid; Clock is null
    - Expected output: exception
    - Implemented: no

---

## start(Color active)

### Step 1 - Inputs and Outputs
- Input #1: active player
- Input #2: current clock state
- Output #1: running player
- Output #2: remaining times
- Output #3: exception

### Step 2 - Data Types
- Input #1: Case
- Input #2: Case
- Output #1: Case
- Output #2: Collection
- Output #3: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Input #2: not started, already started
- Output #1: WHITE running, BLACK running
- Output #2: remaining times do not change just from starting
- Output #3: exception

### Step 4 - Test Cases
- Test Case 12: start white clock
    - Method(s) under test: `start(Color active)`
    - State of the system: new ChessClock, active player = WHITE
    - Expected output: white clock is running; both players still have the starting time
    - Implemented: no

- Test Case 13: start black clock
    - Method(s) under test: `start(Color active)`
    - State of the system: new ChessClock, active player = BLACK
    - Expected output: black clock is running; both players still have the starting time
    - Implemented: no

- Test Case 14: start with null active player
    - Method(s) under test: `start(Color active)`
    - State of the system: new ChessClock, active player = null
    - Expected output: exception
    - Implemented: no

---

## tick()

### Step 1 - Inputs and Outputs
- Input #1: current clock state
- Input #2: elapsed time
- Input #3: remaining time before tick
- Output #1: remaining time after tick
- Output #2: timeout state

### Step 2 - Data Types
- Input #1: Case
- Input #2: Numbers
- Input #3: Numbers
- Output #1: Numbers
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: not running, WHITE running, BLACK running
- Input #2: 0 seconds, 1 second, exactly remaining time, more than remaining time
- Input #3: 1 second, 5 minutes
- Output #1: unchanged time, decreased time, 0 seconds
- Output #2: no timeout, timeout

### Step 4 - Test Cases
- Test Case 15: tick when clock is not running
    - Method(s) under test: `tick()`
    - State of the system: new ChessClock, no player has started
    - Expected output: both players still have the starting time
    - Implemented: no

- Test Case 16: tick with zero elapsed time
    - Method(s) under test: `start(Color active)`, `tick()`
    - State of the system: white clock is running; 0 seconds have passed
    - Expected output: white still has the starting time; black still has the starting time
    - Implemented: no

- Test Case 17: tick with one second elapsed
    - Method(s) under test: `start(Color active)`, `tick()`, `remaining(Color color)`
    - State of the system: white clock is running; white starts with 5 minutes; 1 second has passed
    - Expected output: white has 4 minutes 59 seconds remaining; black still has 5 minutes
    - Implemented: no

- Test Case 18: tick exactly to zero
    - Method(s) under test: `start(Color active)`, `tick()`, `remaining(Color color)`, `isExpired(Color color)`
    - State of the system: white clock is running; white has 1 second remaining; 1 second has passed
    - Expected output: white has 0 seconds remaining; white is expired
    - Implemented: no

- Test Case 19: tick past zero
    - Method(s) under test: `start(Color active)`, `tick()`, `remaining(Color color)`, `isExpired(Color color)`
    - State of the system: white clock is running; white has 1 second remaining; 2 seconds have passed
    - Expected output: white has 0 seconds remaining, not negative time; white is expired
    - Implemented: no

---

## completeTurn(Color moved, Color next)

### Step 1 - Inputs and Outputs
- Input #1: player who moved
- Input #2: next player
- Input #3: current clock state
- Output #1: moved player's remaining time
- Output #2: running player after switch
- Output #3: exception

### Step 2 - Data Types
- Input #1: Case
- Input #2: Case
- Input #3: Case
- Output #1: Numbers
- Output #2: Case
- Output #3: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Input #2: opposite color, same color, null
- Input #3: not running, running
- Output #1: time unchanged, time increased by increment
- Output #2: next player is running
- Output #3: exception

### Step 4 - Test Cases
- Test Case 20: complete turn with no increment
    - Method(s) under test: `completeTurn(Color moved, Color next)`
    - State of the system: white has moved; increment is 0 seconds; white has 4 minutes 59 seconds remaining
    - Expected output: white still has 4 minutes 59 seconds; black clock is running
    - Implemented: no

- Test Case 21: complete turn with increment
    - Method(s) under test: `completeTurn(Color moved, Color next)`
    - State of the system: white has moved; increment is 3 seconds; white has 4 minutes 59 seconds remaining
    - Expected output: white has 5 minutes 2 seconds remaining; black clock is running
    - Implemented: no

- Test Case 22: complete turn with same player
    - Method(s) under test: `completeTurn(Color moved, Color next)`
    - State of the system: moved = WHITE, next = WHITE
    - Expected output: exception
    - Implemented: no

- Test Case 23: complete turn with null moved player
    - Method(s) under test: `completeTurn(Color moved, Color next)`
    - State of the system: moved = null, next = BLACK
    - Expected output: exception
    - Implemented: no

- Test Case 24: complete turn with null next player
    - Method(s) under test: `completeTurn(Color moved, Color next)`
    - State of the system: moved = WHITE, next = null
    - Expected output: exception
    - Implemented: no

---

## pause()

### Step 1 - Inputs and Outputs
- Input #1: current clock state
- Output #1: running player
- Output #2: remaining times

### Step 2 - Data Types
- Input #1: Case
- Output #1: Case
- Output #2: Collection

### Step 3 - Concrete Values
- Input #1: not running, WHITE running, BLACK running
- Output #1: no player running
- Output #2: remaining times stop changing

### Step 4 - Test Cases
- Test Case 25: pause before starting
    - Method(s) under test: `pause()`
    - State of the system: new ChessClock, no player has started
    - Expected output: no player is running; both players still have the starting time
    - Implemented: no

- Test Case 26: pause while white is running
    - Method(s) under test: `start(Color active)`, `pause()`, `tick()`
    - State of the system: white clock is running, then the clock is paused, then time passes
    - Expected output: white's time does not keep going down after pause
    - Implemented: no

---

## remaining(Color color)

### Step 1 - Inputs and Outputs
- Input #1: player color
- Output #1: remaining time
- Output #2: exception

### Step 2 - Data Types
- Input #1: Case
- Output #1: Numbers
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Output #1: 0 seconds, 1 second, 5 minutes
- Output #2: exception

### Step 4 - Test Cases
- Test Case 27: remaining time for white
    - Method(s) under test: `remaining(Color color)`
    - State of the system: new ChessClock with 5 minute starting time
    - Expected output: white has 5 minutes remaining
    - Implemented: no

- Test Case 28: remaining time for black
    - Method(s) under test: `remaining(Color color)`
    - State of the system: new ChessClock with 5 minute starting time
    - Expected output: black has 5 minutes remaining
    - Implemented: no

- Test Case 29: remaining time with null color
    - Method(s) under test: `remaining(Color color)`
    - State of the system: color = null
    - Expected output: exception
    - Implemented: no

---

## isExpired(Color color)

### Step 1 - Inputs and Outputs
- Input #1: player color
- Input #2: remaining time
- Output #1: boolean result
- Output #2: exception

### Step 2 - Data Types
- Input #1: Case
- Input #2: Numbers
- Output #1: Boolean
- Output #2: Case

### Step 3 - Concrete Values
- Input #1: WHITE, BLACK, null
- Input #2: 0 seconds, 1 second, 5 minutes
- Output #1: true, false
- Output #2: exception

### Step 4 - Test Cases
- Test Case 30: player is not expired
    - Method(s) under test: `isExpired(Color color)`
    - State of the system: white has 1 second remaining
    - Expected output: false
    - Implemented: no

- Test Case 31: player is expired
    - Method(s) under test: `isExpired(Color color)`
    - State of the system: white has 0 seconds remaining
    - Expected output: true
    - Implemented: no

- Test Case 32: isExpired with null color
    - Method(s) under test: `isExpired(Color color)`
    - State of the system: color = null
    - Expected output: exception
    - Implemented: no
