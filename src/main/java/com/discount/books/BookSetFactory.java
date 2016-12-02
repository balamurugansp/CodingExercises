package com.discount.books;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.discount.cart.ShoppingCartItem;

public class BookSetFactory {
	List<BookSetDiscount> discounts;

	public BookSetFactory(List<BookSetDiscount> discounts) {
		this.discounts = discounts;
	}

	public List<BookSet> getDifferentBooksSetsWithMaxTotalDiscount(List<ShoppingCartItem> shoppingCartItems) {

		List<BookSet> optimizeSetList;

		optimizeSetList = getBestCombinationBooksSets(shoppingCartItems);

		return optimizeSetList;
	}

	private List<BookSet> getBestCombinationBooksSets(List<ShoppingCartItem> shoppingCartItems) {
		List<List<BookSet>> differentBooksSetsCombinations = new ArrayList<>();

		for (int i = shoppingCartItems.size(); i >= 1; i--) {
			differentBooksSetsCombinations.add(calculateDifferentBooksSetsByMaxSize(shoppingCartItems, i));
		}

		List<BookSet> optimizeSetList;

		if (differentBooksSetsCombinations.size() > 1)
			optimizeSetList = selectBooksSetsWithMaxDiscount(differentBooksSetsCombinations);
		else
			optimizeSetList = differentBooksSetsCombinations.get(0);
		return optimizeSetList;
	}

	private List<BookSet> calculateDifferentBooksSetsByMaxSize(List<ShoppingCartItem> shoppingCartItems,
			int maxSizeSet) {
		List<ShoppingCartItem> remainingShoppingCartItems = cloneShoppingCartItems(shoppingCartItems);
		List<BookSet> setsOfDifferentBooks = new ArrayList<>();

		while (remainingShoppingCartItems.size() > 0) {
			final BookSet oneSetOfDifferentBooks = createNextSet(remainingShoppingCartItems, maxSizeSet);
			setsOfDifferentBooks.add(oneSetOfDifferentBooks);
		}

		return setsOfDifferentBooks;
	}

	private BookSet createNextSet(List<ShoppingCartItem> remainingShoppingCartItems, int maxSizeSet) {
		HashSet<Book> books = new HashSet<>();

		for (ShoppingCartItem item : new ArrayList<>(remainingShoppingCartItems)) {

			books.add(item.getBook());

			if (item.getQuantity() == 1)
				remainingShoppingCartItems.remove(item);
			else
				item.changeQuantity(item.getQuantity() - 1);

			if (books.size() == maxSizeSet)
				break;
		}

		BookSet booksSet = new BookSet(books, getDiscount(books.size()));

		return booksSet;
	}

	private List<BookSet> selectBooksSetsWithMaxDiscount(List<List<BookSet>> booksSetsCombinations) {
		List<BookSet> maxDiscountBooksSets = null;
		int maxBooksSetsDiscount = 0;
		int totalBooksSetsDiscount = 0;

		for (List<BookSet> booksSets : booksSetsCombinations) {
			for (BookSet booksSet : booksSets) {
				totalBooksSetsDiscount += booksSet.getDiscount();
			}

			if (maxBooksSetsDiscount < totalBooksSetsDiscount) {
				maxDiscountBooksSets = booksSets;
				maxBooksSetsDiscount = totalBooksSetsDiscount;
			}

			totalBooksSetsDiscount = 0;
		}

		return maxDiscountBooksSets;
	}

	private List<ShoppingCartItem> cloneShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
		List<ShoppingCartItem> shoppingCartItemsCopy = new ArrayList<>();

		for (ShoppingCartItem item : shoppingCartItems) {
			shoppingCartItemsCopy.add(new ShoppingCartItem(item.getBook(), item.getQuantity()));
		}

		return shoppingCartItemsCopy;
	}

	private int getDiscount(int differentBooksCount) {
		int defaultDiscount = 0;

		for (BookSetDiscount discount : discounts) {
			if (differentBooksCount == discount.getDifferentCopies())
				return discount.getDiscount();
		}

		return defaultDiscount;
	}
}
