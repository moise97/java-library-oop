# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

No build tool is configured yet. Compile and run with standard `javac`/`java` from the repo root:

```bash
# Compile all sources
javac -d out $(find src -name "*.java")

# Run the CLI
java -cp out com.library.LibraryManager
```

## Architecture

Plain Java library management system. No frameworks, no persistence — all state lives in memory for the duration of a run.

**Entry point / CLI:** `src/main/java/com/library/LibraryManager.java`
**Model classes:** `src/main/java/com/library/model/`

### Class hierarchy

`User` is an abstract base class. `Student` and `Librarian` extend it and override `getMaxBorrowLimit()` (5 and 10 respectively). Shared borrow/return logic lives entirely in `User` — subclasses add no duplicate logic.

- `Librarian` adds `addBook(Book, List<Book>)` to insert into a shared catalog list it does not own
- `User.getBorrowedBooks()` returns an unmodifiable view of the internal list

### Transaction model

A `Transaction` is an immutable record of a single borrow event: it captures the `User`, the `Book`, and a `LocalDateTime` stamped at construction. It has no setters. `LibraryManager` is responsible for creating transactions after a successful `user.borrowBook(book)` call.

### LibraryManager (CLI)

Owns three in-memory collections: `catalog` (`List<Book>`), `students` (`List<Student>`), and `transactions` (`List<Transaction>`). A single `Librarian` instance is pre-created as the system operator. Student IDs are auto-generated (`S001`, `S002`, …) and transaction IDs are auto-generated (`TXN001`, `TXN002`, …).

The borrow flow: filter catalog for available books → user selects book and student → call `student.borrowBook(book)` → on success, create and store a `Transaction`.
