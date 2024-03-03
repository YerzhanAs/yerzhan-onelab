package kz.library.system.domains.repositories;

import kz.library.system.domains.entities.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();

    Book findById(Long id);

    void save(Book book);

    void deleteById(Long id);

    int count();

    void updateBookByAuthorId(Long authorId, String newTitle, String newIsbn, String newLanguage);

    List<Book> getBooksByAuthor(Long authorId);

    public List<Book> getBooksWithAuthorAndPublisher();

}
