package kz.library.system.domain.repositories.impl;

import kz.library.system.domain.entities.Author;
import kz.library.system.domain.entities.Book;
import kz.library.system.domain.entities.Publisher;
import kz.library.system.domain.repositories.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private static final List<Book> books = new ArrayList<>();

    static {
        books.add(Book.builder().id(1L)
                .title("Star wars: first war of emprie")
                .isbn("978-0-306-40615-7")
                .language("English")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .build());

        books.add(Book.builder().id(2L)
                .title("Lord of the Rings")
                .isbn("954-0-306-40612-7")
                .language("English")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .build());

        books.add(Book.builder().id(3L)
                .title("The journay of Samurai")
                .isbn("954-0-306-40612-4")
                .language("English")
                .author(Author.builder().build())
                .publisher(Publisher.builder().build())
                .build());
    }
    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    @Override
    public Book save(Book book) {
        book.setId((long) (books.size() + 1));
        books.add(book);
        return book;
    }

    @Override
    public void deleteById(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}
