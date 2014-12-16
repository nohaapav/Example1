package eu.nohaapav.example.checkoutservice.domain;

import java.math.BigDecimal;

/**
 * Book domain representation.
 *
 * @author pavol.noha@gmail.com
 */
public class Book {

    private String title;
    private BigDecimal price;
    private int year;

    public Book(String title, BigDecimal price, int year) {
        this.title = title;
        this.price = price;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }
}
