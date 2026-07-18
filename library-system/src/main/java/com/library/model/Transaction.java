package com.library.model;

import java.time.LocalDateTime;

public class Transaction {

    private final String transactionId;
    private final User user;
    private final Book book;
    private final LocalDateTime borrowDate;

    public Transaction(String transactionId, User user, Book book) {
        this.transactionId = transactionId;
        this.user = user;
        this.book = book;
        this.borrowDate = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + transactionId + "'" +
                ", user=" + user.getName() +
                ", book='" + book.getTitle() + "'" +
                ", borrowDate=" + borrowDate +
                "}";
    }
}
