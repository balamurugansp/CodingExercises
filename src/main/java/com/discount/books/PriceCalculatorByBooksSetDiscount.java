package com.discount.books;

import com.discount.cart.ShoppingCart;
import com.discount.cart.ShoppingCartItem;

import java.util.List;

public class PriceCalculatorByBooksSetDiscount implements ShoppingCart.PriceCalculator {

    private BookSetFactory booksSetFactory;

    public PriceCalculatorByBooksSetDiscount(BookSetFactory booksSetFactory){
        this.booksSetFactory = booksSetFactory;
    }

    public Double calculate(List<ShoppingCartItem> shoppingCartItems) {
        List<BookSet> setsOfDifferentBooks =
                booksSetFactory.getDifferentBooksSetsWithMaxTotalDiscount(shoppingCartItems);

        double totalPrice =0.0;
        double setPrice =0.0;

        for (BookSet booksSet:setsOfDifferentBooks){
            for (Book book:booksSet.getBooks()) {
                setPrice += book.getPrice();
            }

            setPrice = setPrice * (1.0 - (booksSet.getDiscount()/100.0));
            totalPrice +=setPrice;
            setPrice = 0;
        }

        return totalPrice;
    }
    
    
}