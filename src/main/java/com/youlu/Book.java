package com.youlu;

import com.alibaba.excel.annotation.ExcelProperty;

public class Book {
    @ExcelProperty(index = 0)
    private String isbn;

    @ExcelProperty(index = 1)
    private String price;

    @ExcelProperty(index = 2)
    private String title;

    @ExcelProperty(index = 3)
    private String deal;

    public Book() {}

    public Book(String isbn, String price, String title, String deal) {
        this.isbn = isbn;
        this.price = price;
        this.title = title;
        this.deal = deal;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getPrice() {
        return this.price;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDeal() {
        return this.deal;
    }
}

