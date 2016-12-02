package com.discount.books;

public class BookSetDiscount {
	private int differentCopies;
    private int discount;

    public BookSetDiscount(int differentCopies, int discount){
        this.differentCopies = differentCopies;
        this.discount = discount;
    }

    public int getDifferentCopies(){
        return differentCopies;
    }

    public int getDiscount(){
        return discount;
    }
}
