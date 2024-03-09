package kz.library.system.domains.repositories;

import kz.library.system.domains.entities.Author;
import kz.library.system.domains.entities.Book;
import kz.library.system.domains.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

// TODO: ADD FLYWAY
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE (:isbn is NULL OR b.isbn = :isbn) " +
            "AND (:language is NULL OR b.language = :language)")
    List<Book> findBooksByIsbnAndLanguage(String isbn, String language);

    boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE b.author = :author")
    List<Book> findBooksByAuthor(Author author);

    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.genreName = :genreName")
    List<Book> findBooksByGenre(@Param("genreName") String genreName);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.author = :author")
    Long countBooksByAuthor(@Param("author") Author author);



}
