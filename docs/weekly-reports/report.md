# Week 3 (04/13/2026-04/19/2026)
**Planning and Progress Tracking**:
1. [done] Alvin Xu: Setup repository
2. [done] Alvin Xu: Add GitHub Actions workflow for Gradle build (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/1)
3. [done] Alvin Xu: Add Gradle Build badge to README (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/2)
4. [done] Alvin Xu: Week 3 Report


# Week 4 (04/20/2026-04/24/2026)
**Planning and Progress Tracking**:
1. [done] Kyubinn Kim: Expanded README with project scope (chess ruleset + chess-clock feature), team table, tech stack, repository layout, build/test instructions, and development workflow
2. [done] Kyubinn Kim: Drafted Week 4 and Week 5 weekly reports capturing team decisions, member responsibilities, and the project timeline
3. [done] Team: Selected the team-defined "+1 feature" — chess clock (timed game mode with configurable starting time per side, turn-based countdown, and timeout-loss as an additional win condition)
4. [done] Team: Established team communication — group text thread for day-to-day coordination, plus a weekly meeting with Dr. Yiji on Monday or Tuesday (specific day chosen each week based on member availability)
5. [done] Team: Delegated initial design responsibilities — Kyubinn Kim will draft a class-design PlantUML diagram covering the chess engine and the chess-clock feature; the team will then review the diagram together, finalize the architecture, and split class-level implementation work from there
6. [done] Team: Agreed on the project timeline — class design finalized by end of Week 5; implementation and testing of all chess rules and the chess-clock feature complete by end of May. Driver: Jace Deng begins an internship on 06/01 with reduced availability afterward, so the bulk of collaborative work needs to land in May.
7. [done] Team: Aligned on a deliberate "design-first" approach — agreed it is more valuable to spend Weeks 4–5 reaching a clear shared understanding of what we are building than to start implementation prematurely; this informs the timeline above.
8. [not started] Kyubinn Kim: Limited availability earlier in the quarter due to personal circumstances; resuming active contribution in Week 4 starting with the PlantUML class design.


# Week 5 (04/27/2026-05/02/2026)
**Planning and Progress Tracking**:
1. [done] Kyubinn Kim: Worked on the first version of the class diagram for the chess project. The diagram includes the main game classes, board representation, move classes, piece classes, chess clock, and abstracted UI structure.
2. [done] Kyubinn Kim: Added the PlantUML design file for team review. (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/28)
3. [done] Kyubinn Kim: Updated the Week 5 report. (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/29)
4. [done] Team: Kept the chess clock as our extra feature. The clock will let each side have a set amount of time, switch after each move, and end the game if a player runs out of time.
5. [done] Kyubinn Kim: Added the first BVA document for the chess clock feature. (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/31)
6. [done] Team: Stayed focused on design before starting the full implementation, since we wanted everyone to agree on the structure before splitting up the code.
7. [in progress] Kyubinn Kim: Started the BVA work for the Knight class as the first simple piece-movement feature.
8. [in progress] Kyubinn Kim: Started a small Knight skeleton implementation with one basic constructor/type test. This is meant to begin the TDD trail for Week 6, not finish Knight movement yet. (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/33)
9. [done] Team: Prepared for Exam 1 on Wednesday 04/29.
10. [planned] Team: Next week, review the open design/BVA PRs, then continue with Knight tests and implementation before moving to the other pieces.


# Week 6 (05/04/2026-05/10/2026)
**Planning and Progress Tracking**:
1. [done] Kyubinn Kim: Set up Checkstyle and SpotBugs through Gradle so the project now runs style checks, static analysis, and tests together with `bash ./gradlew check`. (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/34)
2. [done] Kyubinn Kim: Updated the Checkstyle setup to match our team preference for tab indentation while still checking naming, imports, whitespace, braces, line length, and other basic style rules.
3. [done] Kyubinn Kim: Started the Game Setup Phase by adding BVA documentation for `Square`, `Piece`, and `Board`. (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/35)
4. [done] Kyubinn Kim: Added tests for the game setup classes, including square boundaries, piece creation, board placement/removal/movement, board copying, king lookup, occupied squares by color, and the standard chess starting position. (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/35)
5. [done] Kyubinn Kim: Implemented the core game setup domain classes: `Square`, `Piece`, `Board`, `Pawn`, `Bishop`, `Rook`, `Queen`, and `King`, and updated `Knight`, `Color`, and `PieceType` to fit the shared domain structure. (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/35)
6. [done] Kyubinn Kim: Responded to PR review feedback by adding the missing `BoardTest` cases that were already listed in the BVA document, then reran `bash ./gradlew check`.
7. [done] Kyubinn Kim: Kept the implementation flow in the intended order: BVA first, then tests, then implementation.
8. [planned] Kyubinn Kim: Next, continue with Knight movement and capture using the new `Square`, `Piece`, and `Board` classes.
9. [planned] Kyubinn Kim: After Knight movement, start coverage/mutation tooling work with JaCoCo and PIT so we can keep moving toward the B/A rubric requirements.


# Week 7 (05/11/2026-05/17/2026)
**Planning and Progress Tracking**:
1. [done] Team: Talked through Yiji's feedback. Two things to fix going forward: TDD commits should be one passing test + the minimum impl that passes it, not "all tests, then all impl." And we need to spread the work more evenly so each of us has merged PRs.
2. [in progress] Kyubinn Kim: Knight movement (#7). Draft PR open on `kyubinn/knight-movement`. Got the first test passing (knight from center of empty board returns 8 candidate moves) and expanded the BVA to cover TC1-TC6. Remaining cases to go, one red-green cycle per commit.

# Week 8 (05/18/2026-05/24/2026)
**Planning and Progress Tracking**:
1. [done] Jace Deng: Started Bishop movement on `Jace/bishop-bdd-movement` from updated `main`.
2. [done] Jace Deng: Added Bishop BVA one test case per commit to make each boundary/equivalence case visible in history.
3. [done] Jace Deng: Added Cucumber setup, the Bishop scenario outline, step definitions, and one BDD example row per commit.
4. [done] Jace Deng: Added Bishop JUnit tests and minimal implementation commits for diagonal movement, edge handling, friendly blockers, opponent captures, and null inputs.
5. [planned] Team: Review the Bishop movement PR and continue the same BDD+BVA commit discipline for the next piece.
1. [done] Jace: Adopted a hybrid BDD+BVA workflow. Each new feature will use its own feature branch, BVA documented in `docs/bva`, executable BDD scenarios where appropriate, and a pull request before merging.
2. [done] Jace Deng: Created the `knight-bdd-movement` feature branch for Knight movement using the BDD+BVA workflow.
3. [done] Jace Deng: Expanded `docs/bva/knight.md` to cover Knight candidate moves from center, edge, and corner squares; friendly-piece blocking; opponent capture; jumping over pieces; and null inputs.
4. [done] Jace Deng: Added Cucumber/Gherkin support and wrote `KnightMovement.feature` with Scenario Outline examples that contain the BVA cases.
5. [done] Jace Deng: Implemented Knight candidate movement and capture behavior while keeping existing JUnit tests for low-level object behavior.
6. [planned] Team: Review the Knight movement PR and use it as the template for future piece movement features.
7. [planned] Team: After Knight movement is merged, continue with sliding-piece movement and then one-turn game flow.
1. [done] Kyubinn Kim: Expanded the Knight BVA and got TC1 passing (knight at center returns 8 candidate squares). (https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/pull/43)
2. [done] Alvin Xu: Set up JaCoCo and PIT for coverage and mutation testing.
3. [done] Jace Deng: Started Knight BDD implementation.
4. [planned] Kyubinn Kim: Continue Knight TC2-TC6 next week.

# Week 9 (05/25/2026-05/31/2026)
**Planning and Progress Tracking**:
1. [done] Jace Deng: Created the `queen-bdd-movement` feature branch for Queen movement instead of working directly on `main`.
2. [done] Jace Deng: Reworked Queen movement using the detailed BDD+BVA workflow, including one BVA commit per identified test case.
3. [done] Jace Deng: Added Queen Cucumber coverage with the scenario outline and step definitions committed before examples.
4. [done] Jace Deng: Added each Queen BDD example row as its own commit, then used minimal implementation commits for empty-board ray movement, blockers, and opponent captures.
5. [done] Jace Deng: Verified the Queen branch with `sh gradlew check`.
6. [planned] Team: Open and review the Queen movement PR, checking that the branch history demonstrates the required BDD+BVA commit discipline.

# Week X (XX/XX/2026-XX/XX/2026) TEMPLATE (You can change the format to whatever the team likes better)
**Planning and Progress Tracking**:
1. [done] Person: Task (Links to PR)
2. [not started] Person: Task (Links to PR)
3. [80% done] Person: Task (Links to PR)
