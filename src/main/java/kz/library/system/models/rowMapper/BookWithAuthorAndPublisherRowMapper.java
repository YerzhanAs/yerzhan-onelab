package kz.library.system.models.rowMapper;

import kz.library.system.domains.entities.Author;
import kz.library.system.domains.entities.Book;
import kz.library.system.domains.entities.Publisher;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookWithAuthorAndPublisherRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("b.id"));
        book.setTitle(rs.getString("b.title"));
        book.setIsbn(rs.getString("b.isbn"));
        book.setLanguage(rs.getString("b.language"));

        Author author = new Author();
        author.setId(rs.getLong("a.id"));
        author.setName(rs.getString("a.name"));
        book.setAuthor(author);

        Publisher publisher = new Publisher();
        publisher.setId(rs.getLong("p.id"));
        publisher.setPublisherName(rs.getString("p.publisherName"));
        publisher.setPublisherYear(rs.getInt("p.publisherYear"));
        book.setPublisher(publisher);

        return book;
    }
}
