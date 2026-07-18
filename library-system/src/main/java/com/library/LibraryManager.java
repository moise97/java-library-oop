package com.library;

import com.library.model.Book;
import com.library.model.Librarian;
import com.library.model.Student;
import com.library.model.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManager {

    private static final Path CATALOG_FILE = Paths.get("library_catalog.txt");

    private static final List<Book> catalog = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final List<Transaction> transactions = new ArrayList<>();
    private static final Librarian librarian = new Librarian("L001", "Head Librarian");
    private static final Scanner scanner = new Scanner(System.in);
    private static int studentCounter = 1;
    private static int transactionCounter = 1;

    public static void main(String[] args) {
        System.out.println("=== Library Management System ===");
        System.out.println("Logged in as: " + librarian.getName());
        loadCatalog();

        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> addBook();
                case "2" -> borrowBook();
                case "3" -> listBooks();
                case "0" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add a book");
        System.out.println("2. Borrow a book");
        System.out.println("3. List all books");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    // Option 1 — Librarian only
    private static void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();

        librarian.addBook(new Book(title, author), catalog);
        saveCatalog();
    }

    // Option 2 — asks for student name, creates the student if first time seen
    private static void borrowBook() {
        if (catalog.isEmpty()) {
            System.out.println("No books in the catalog yet.");
            return;
        }

        List<Book> available = catalog.stream().filter(Book::isAvailable).toList();
        if (available.isEmpty()) {
            System.out.println("No books are currently available.");
            return;
        }

        System.out.println("\nAvailable books:");
        for (int i = 0; i < available.size(); i++) {
            System.out.printf("  %d. \"%s\" by %s%n", i + 1,
                    available.get(i).getTitle(), available.get(i).getAuthor());
        }
        System.out.print("Select a book (number): ");
        int bookIndex = parseIndex(scanner.nextLine().trim(), available.size());
        if (bookIndex < 0) return;

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        Student student = findOrCreateStudent(name);

        Book book = available.get(bookIndex);
        boolean success = student.borrowBook(book);
        if (success) {
            String txnId = "TXN" + String.format("%03d", transactionCounter++);
            transactions.add(new Transaction(txnId, student, book));
            System.out.printf("Success! \"%s\" borrowed by %s (Transaction: %s)%n",
                    book.getTitle(), student.getName(), txnId);
            saveCatalog();
        }
    }

    // Option 3
    private static void listBooks() {
        if (catalog.isEmpty()) {
            System.out.println("No books in the catalog yet.");
            return;
        }
        System.out.println("\n--- All Books ---");
        for (Book book : catalog) {
            String status = book.isAvailable() ? "Available" : "Borrowed";
            System.out.printf("  %-30s | %-20s | %s%n",
                    book.getTitle(), book.getAuthor(), status);
        }
    }

    // Looks up an existing student by name (case-insensitive), or creates a new one
    private static Student findOrCreateStudent(String name) {
        return students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> {
                    String id = "S" + String.format("%03d", studentCounter++);
                    Student newStudent = new Student(id, name);
                    students.add(newStudent);
                    System.out.println("New student registered: " + name + " (ID: " + id + ")");
                    return newStudent;
                });
    }

    private static void saveCatalog() {
        List<String> lines = new ArrayList<>();
        for (Book book : catalog) {
            lines.add(book.getTitle() + "|" + book.getAuthor() + "|" + book.isAvailable());
        }
        try {
            Files.write(CATALOG_FILE, lines);
        } catch (IOException e) {
            System.out.println("Warning: could not save catalog (" + e.getMessage() + ")");
        }
    }

    private static void loadCatalog() {
        if (!Files.exists(CATALOG_FILE)) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(CATALOG_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|", 3);
                if (parts.length != 3) continue;
                Book book = new Book(parts[0], parts[1]);
                book.setAvailable(Boolean.parseBoolean(parts[2]));
                catalog.add(book);
            }
            System.out.println("Loaded " + catalog.size() + " book(s) from " + CATALOG_FILE + ".");
        } catch (IOException e) {
            System.out.println("Warning: could not load catalog (" + e.getMessage() + ")");
        }
    }

    private static int parseIndex(String input, int max) {
        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= max) {
                System.out.println("Invalid selection.");
                return -1;
            }
            return index;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return -1;
        }
    }
}
