package com.discount.books;

import java.util.HashSet;

public class BookSet {
	private HashSet<Book> books;
    private int discount;

    public BookSet(HashSet<Book> books, int discount){
        this.books = books;
        this.discount = discount;
    }

    public HashSet<Book> getBooks() {
        return books;
    }

    public int getDiscount() {
        return discount;
    }
}
