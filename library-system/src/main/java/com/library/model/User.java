package com.library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class User {

    private final String id;
    private String name;
    private final List<Book> borrowedBooks = new ArrayList<>();

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract int getMaxBorrowLimit();

    public boolean borrowBook(Book book) {
        if (!book.isAvailable()) {
            System.out.println("\"" + book.getTitle() + "\" is not available.");
            return false;
        }
        if (borrowedBooks.size() >= getMaxBorrowLimit()) {
            System.out.println(name + " has reached the borrow limit of " + getMaxBorrowLimit() + " books.");
            return false;
        }
        borrowedBooks.add(book);
        book.setAvailable(false);
        return true;
    }

    public boolean returnBook(Book book) {
        if (!borrowedBooks.remove(book)) {
            System.out.println(name + " does not have \"" + book.getTitle() + "\".");
            return false;
        }
        book.setAvailable(true);
        return true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }
}
