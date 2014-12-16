package eu.nohaapav.example.checkoutservice.service;

import eu.nohaapav.example.checkoutservice.domain.Book;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static eu.nohaapav.example.checkoutservice.utils.CalculationUtils.round;
import static org.junit.Assert.assertEquals;

/**
 * Checkout service test class.
 *
 * @author pavol.noha@gmail.com
 */
public class CheckoutServiceTest {

    private static final int BIGGER_THAN_CRITERION = 1;
    private static final int BOOK_DISCOUNT_CRITERION = 2000;
    private static final BigDecimal BOOK_DISCOUNT_PERCENTAGE = new BigDecimal(10.00);
    private static final BigDecimal TOTAL_DISCOUNT_CRITERION = new BigDecimal(30.00);
    private static final BigDecimal TOTAL_DISCOUNT_PERCENTAGE = new BigDecimal(5.00);

    private CheckoutService checkoutService;

    @Before
    public void setUp() {
        checkoutService = new CheckoutService();
    }

    @Test
    public void testOnlyPublishAfterDiscountStrategyApplied() {
        List<Book> booksToPurchase = Arrays.asList(
                new Book("The Terrible Privacy of Maxwell Sim", new BigDecimal(13.14), 2010),
                new Book("Three Men in a Boat", new BigDecimal(12.87), 1889)
        );

        BigDecimal expectedPrice = new BigDecimal(24.70);
        BigDecimal actualPrice = performCheckoutWithPublishAfterAndTotalDiscountStrategy(booksToPurchase);

        assertEquals(round(expectedPrice), actualPrice);
    }

    @Test
    public void testOnlyTotalDiscountStrategyApplied() {
        List<Book> booksToPurchase = Arrays.asList(
                new Book("Still Life With Woodpecker", new BigDecimal(11.05), 1980),
                new Book("Three Men in a Boat", new BigDecimal(12.87), 1889),
                new Book("Great Expectations", new BigDecimal(13.21), 1861)
        );

        BigDecimal expectedPrice = new BigDecimal(35.27);
        BigDecimal actualPrice = performCheckoutWithPublishAfterAndTotalDiscountStrategy(booksToPurchase);

        assertEquals(round(expectedPrice), actualPrice);
    }

    @Test
    public void testBothPublishAfterAndTotalDiscountStrategyApplied() {
        List<Book> booksToPurchase = Arrays.asList(
                new Book("The Terrible Privacy of Maxwell Sim", new BigDecimal(13.14), 2010),
                new Book("Three Men in a Boat", new BigDecimal(12.87), 1889),
                new Book("Great Expectations", new BigDecimal(13.21), 1861)
        );

        BigDecimal expectedPrice = new BigDecimal(36.01);
        BigDecimal actualPrice = performCheckoutWithPublishAfterAndTotalDiscountStrategy(booksToPurchase);

        assertEquals(round(expectedPrice), actualPrice);
    }

    @Test
    public void testEmptyListOfBooksToPurchase() {
        List<Book> booksToPurchase = new ArrayList<>();

        BigDecimal expectedPrice = new BigDecimal(0);
        BigDecimal actualPrice = performCheckoutWithPublishAfterAndTotalDiscountStrategy(booksToPurchase);

        assertEquals(round(expectedPrice), actualPrice);
    }

    private BigDecimal performCheckoutWithPublishAfterAndTotalDiscountStrategy(List<Book> booksToPurchase) {
        return checkoutService.performCheckout(booksToPurchase,
                (book) -> book.getYear() > BOOK_DISCOUNT_CRITERION,
                BOOK_DISCOUNT_PERCENTAGE,
                (subtotal) -> subtotal.compareTo(TOTAL_DISCOUNT_CRITERION) == BIGGER_THAN_CRITERION,
                TOTAL_DISCOUNT_PERCENTAGE);
    }
}
