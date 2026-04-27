![Gradle Build](https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/actions/workflows/main.yml/badge.svg)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23638202)

# Chess — CS 380 Team 28

A 2D chess game built in Java for COMP_SCI 380: Software Quality Engineering (Spring 2026). The project follows a TDD/BDD workflow with continuous integration, branch protection, mutation testing, and BVA-driven test design.

## Project Scope

The game implements the full standard chess ruleset along with one team-defined feature.

**Standard rules**
- All six piece types (Pawn, Knight, Bishop, Rook, Queen, King) with correct movement and capture
- Check, checkmate, and stalemate detection
- Special moves: castling (kingside and queenside), en passant, pawn promotion
- Draw conditions: stalemate, threefold repetition, fifty-move rule, insufficient material
- Turn-based play with two human players sharing one machine

**Team-defined feature: Chess clock**
- Configurable per-side starting time
- Turn-based countdown that pauses on the inactive player's clock
- Timeout produces an additional win condition (the player whose clock runs out loses)

## Team

| Member      | GitHub                                             |
|-------------|----------------------------------------------------|
| Alvin Xu    | [@superalv123](https://github.com/superalv123)     |
| Jace Deng   | [@Jiahao-Deng03](https://github.com/Jiahao-Deng03) |
| Kyubinn Kim | [@dannykimNU](https://github.com/dannykimNU)       |

## Tech Stack

- **Language:** Java 11
- **Build:** Gradle 8.10 (Kotlin DSL)
- **Test framework:** JUnit 5.10
- **CI:** GitHub Actions (`.github/workflows/main.yml`)
- **Branch protection:** `main` requires a passing CI build and one approving review per pull request


## Build and Test

The project uses the Gradle wrapper, so no Gradle installation is required.

```bash
# Compile and run all tests
./gradlew build

# Run only the test suite
./gradlew test

# Clean previous build artifacts
./gradlew clean
```

CI runs `./gradlew build` on every pull request and on every push to `main`.

## Run the Game

Run instructions will be added once the application entry point is implemented.

## Development Workflow

1. Create a feature branch off `main` using the format `<author>/<scope>` (for example `kyubinn/knight-movement`).
2. For each feature, document the BVA in `docs/bva/<feature>.md` before writing code.
3. Write failing JUnit tests that reflect the BVA, then implement the feature until the tests pass (TDD).
4. Open a pull request against `main`. CI must pass and at least one teammate must approve before merging.
5. Update `docs/weekly-reports/report.md` at the end of each week with planning and progress notes.

## Acknowledgements

- Course materials and rubric: COMP_SCI 380 Software Quality Engineering, taught by Dr. Yiji Zhang, Northwestern University.
- Reference textbook: *Clean Code* by Robert C. Martin.