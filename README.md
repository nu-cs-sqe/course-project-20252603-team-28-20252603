![Gradle Build](https://github.com/nu-cs-sqe/course-project-20252603-team-28-20252603/actions/workflows/main.yml/badge.svg)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23638202)

# Chess — CS 380 Team 28

A 2D chess game built in Java for COMP_SCI 380: Software Quality Engineering (Spring 2026). Most features start with a BVA doc and JUnit tests. PIT runs mutation testing, and CI gates every PR to main.

## Project Scope

The game covers standard chess rules plus a chess clock.

**Standard rules**
- All six piece types (Pawn, Knight, Bishop, Rook, Queen, King) with correct movement and capture
- Check, checkmate, and stalemate detection
- Pawn promotion

Scope REVISED: not adding castling, en passant, threefold repetition, fifty-move rule, or insufficient material draws.

**Team-defined feature: Chess clock**
- Each side has a starting time (configurable)
- The active player's clock ticks down. The other player's pauses.
- If your clock hits zero, you lose.

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

All changes go through pull requests to `main`. CI must pass and at least one teammate must approve before merging.

## Acknowledgements

- Course materials and rubric: COMP_SCI 380 Software Quality Engineering, taught by Dr. Yiji Zhang, Northwestern University.
- Reference textbook: *Clean Code* by Robert C. Martin.
