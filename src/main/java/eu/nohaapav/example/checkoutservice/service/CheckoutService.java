package eu.nohaapav.example.checkoutservice.service;

import eu.nohaapav.example.checkoutservice.domain.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static eu.nohaapav.example.checkoutservice.utils.CalculationUtils.percentage;
import static eu.nohaapav.example.checkoutservice.utils.CalculationUtils.round;

/**
 * Checkout service handle final checkout with different discount strategies
 * when customer is decided to pay.
 *
 * @author pavol.noha@gmail.com
 */
public class CheckoutService {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);

    /**
     * Perform final shop checkout with given discount predicates/strategies.
     *
     * @param books                   books being purchased
     * @param bookDiscountPredicate   apply discount to every book that match predicate
     * @param bookDiscountPercentage  discount being applied to book that match bookDiscountPredicate
     * @param totalDiscountPredicate  apply total discount if ordered books match predicate
     * @param totalDiscountPercentage discount being applied to total price that match totalDiscountPredicate
     * @return rounded total price with discount or zero value if the list of books being purchased is empty
     */
    public BigDecimal performCheckout(final List<Book> books,
                                      final Predicate<Book> bookDiscountPredicate,
                                      final BigDecimal bookDiscountPercentage,
                                      final Predicate<BigDecimal> totalDiscountPredicate,
                                      final BigDecimal totalDiscountPercentage) {

        final Optional<BigDecimal> finalPrice = books.stream()
                .map((book) -> bookDiscountPredicate.test(book)
                        ? getPriceAfterDiscount(book.getPrice(), bookDiscountPercentage)
                        : book.getPrice())
                .reduce((a, b) -> a.add(b))
                .map((subtotal) -> totalDiscountPredicate.test(subtotal)
                        ? getPriceAfterDiscount(subtotal, totalDiscountPercentage)
                        : subtotal);

        final BigDecimal finalPriceBeforeRound = finalPrice.orElse(DEFAULT_PRICE);
        return round(finalPriceBeforeRound);
    }

    /**
     * Return discounted price from original one.
     *
     * @param originalPrice      price to be discounted
     * @param discountPercentage percentage value that should be discounted from original price
     * @return price with discount applied
     */
    private BigDecimal getPriceAfterDiscount(final BigDecimal originalPrice,
                                             final BigDecimal discountPercentage) {

        final BigDecimal discountPrice = percentage(originalPrice, discountPercentage);
        return originalPrice.subtract(discountPrice);
    }
}
