package kz.library.system.domains.repositories.impl;

import kz.library.system.domains.entities.Book;
import kz.library.system.domains.repositories.BookRepository;
import kz.library.system.models.rowMapper.BookWithAuthorAndPublisherRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM t_books", new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Book findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM t_books WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO t_books (title, isbn, language, author_id, publisher_id) " +
                        "VALUES(?, ?, ?, ?, ?)", book.getTitle(), book.getIsbn(), book.getLanguage(),
                         book.getAuthor().getId(), book.getPublisher().getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM t_books WHERE id=?", id);
    }

    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select t_books from books", Integer.class);
    }


    @Override
    public void updateBookByAuthorId(Long authorId, String newTitle, String newIsbn, String newLanguage) {
        String query = "UPDATE t_books SET title = ?, isbn = ?, language = ? WHERE author_id = ?";
        jdbcTemplate.update(query, newTitle, newIsbn, newLanguage, authorId);
    }

    @Override
    public List<Book> getBooksByAuthor(Long authorId) {
        String query = "SELECT * FROM t_books WHERE author_id = ?";
        return jdbcTemplate.query(query, new Object[]{authorId}, new BeanPropertyRowMapper<>(Book.class));
    }


    @Override
    public List<Book> getBooksWithAuthorAndPublisher() {
        String query = "SELECT b.*, a.*, p.* " +
                "FROM t_books b " +
                "INNER JOIN t_authors a ON b.author_id = a.id " +
                "INNER JOIN t_publishers p ON b.publisher_id = p.id";

        return jdbcTemplate.query(query, new BookWithAuthorAndPublisherRowMapper());
    }


}
