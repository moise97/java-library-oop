# Java Library Management System

A command-line Java application for managing a library catalog, built from the ground up using core Object-Oriented Programming principles. Users can add books, borrow them, and view the catalog — with all data persisted to a local file so nothing is lost between sessions.

---

## Core Features

- **User roles** — A pre-authenticated `Librarian` manages the catalog; `Student` users borrow books with an enforced limit of 5 books at a time
- **Book management** — Add books to the catalog, borrow them, and track availability in real time
- **Transaction records** — Every borrow is recorded as a `Transaction` with the student name, book title, and a timestamp
- **File persistence** — The catalog is saved to `library_catalog.txt` after every change and reloaded automatically on startup — no database required

---

## OOP Principles Used

### Encapsulation
All fields in `Book` (`title`, `author`, `isAvailable`) and `User` (`id`, `name`, `borrowedBooks`) are `private`. State is only accessible or modified through public getters, setters, and controlled methods like `borrowBook()` and `toggleAvailability()`.

### Inheritance
`Student` and `Librarian` both extend the abstract `User` class. Shared logic — borrow limits enforcement, book checkout, return handling — is defined once in `User` and inherited by both subclasses. Each subclass only defines what makes it unique.

### Abstraction
`User` is declared `abstract` with an abstract method `getMaxBorrowLimit()`. This forces every subclass to declare its own limit while keeping the borrowing logic in one place. The caller never needs to know which type of user it is dealing with.

### Composition
`User` contains a `List<Book>` representing currently borrowed books. `Transaction` is composed of a `User`, a `Book`, and a `LocalDateTime` — it does not extend any of them, it links them together.

---

## Project Structure

```
src/
└── main/java/com/library/
    ├── LibraryManager.java        # Entry point — CLI menu and persistence
    └── model/
        ├── Book.java              # Encapsulated book entity
        ├── User.java              # Abstract base user class
        ├── Student.java           # Extends User, limit of 5 books
        ├── Librarian.java         # Extends User, can add books to catalog
        └── Transaction.java       # Immutable borrow event record
```

---

## How to Run

**Prerequisites:** Java 11 or higher installed.

**1. Compile**
```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

**2. Run**
```bash
java -cp out com.library.LibraryManager
```

---

## How to Use

When you start the program, you are automatically logged in as the Head Librarian.

```
=== Library Management System ===
Logged in as: Head Librarian

--- Menu ---
1. Add a book
2. Borrow a book
3. List all books
0. Exit
```

### Adding a book (Librarian)
Select option `1`, then enter the title and author when prompted:
```
Choose an option: 1
Enter title: Clean Code
Enter author: Robert Martin
"Clean Code" added to the catalog by Head Librarian.
```

### Borrowing a book (Student)
Select option `2`, choose a book from the list, then enter the student's name. If the student is new, they are registered automatically:
```
Choose an option: 2

Available books:
  1. "Clean Code" by Robert Martin
Select a book (number): 1
Enter student name: Alice
New student registered: Alice (ID: S001)
Success! "Clean Code" borrowed by Alice (Transaction: TXN001)
```

### Listing all books
Select option `3` to see every book and its current status:
```
Choose an option: 3

--- All Books ---
  Clean Code                     | Robert Martin        | Borrowed
```

### Persistence
The catalog is saved to `library_catalog.txt` in the project root after every change. The next time you run the program, it loads automatically:
```
Loaded 1 book(s) from library_catalog.txt.
```

---

## Tech Stack

| | |
|---|---|
| Language | Java 11+ |
| Build | `javac` (no build tool required) |
| Storage | Plain text file (`library_catalog.txt`) |
| Architecture | OOP — Encapsulation, Inheritance, Abstraction, Composition |
