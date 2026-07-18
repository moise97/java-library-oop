package com.library.model;

import java.util.List;

public class Librarian extends User {

    private static final int MAX_BORROW_LIMIT = 10;

    public Librarian(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxBorrowLimit() {
        return MAX_BORROW_LIMIT;
    }

    public void addBook(Book book, List<Book> catalog) {
        catalog.add(book);
        System.out.println("\"" + book.getTitle() + "\" added to the catalog by " + getName() + ".");
    }
}
