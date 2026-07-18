package com.library.model;

public class Student extends User {

    private static final int MAX_BORROW_LIMIT = 5;

    public Student(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxBorrowLimit() {
        return MAX_BORROW_LIMIT;
    }
}
