package kz.library.system.domains.repositories;

import kz.library.system.domains.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// TODO: ADD FLYWAY
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE (:isbn is NULL OR b.isbn = :isbn) " +
            "AND (:language is NULL OR b.language = :language) " +
            "AND (:title is NULL OR b.title = :title)")
    Page<Book> searchBooks(Pageable pageable, String isbn, String language, String title);

    boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE b.author.name = :authorName")
    List<Book> findBooksByAuthorName(@Param("authorName") String authorName);

    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.genreName = :genreName")
    List<Book> findBooksByGenreName(@Param("genreName") String genreName);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.author.name = :authorName")
    Long countBooksByAuthor(@Param("authorName") String authorName);

}
