package com.example.demo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookRepository {
    @Cacheable("books")
    public Book getByIsbn(String isbn) {
        System.out.println("Start getting...");
        //simulateSlowService();
        return new Book(isbn, "BOOK");
    }

    private void simulateSlowService() {
        try {
            long time = 5000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public class Book {

        private String isbn;
        private String name;

        public Book() {}
        public Book(String isbn, String name) {
            this.isbn = isbn;
            this.name = name;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
