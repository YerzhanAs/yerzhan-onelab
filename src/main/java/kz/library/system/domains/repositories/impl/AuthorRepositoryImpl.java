package kz.library.system.domains.repositories.impl;

import kz.library.system.domains.entities.Author;
import kz.library.system.domains.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query("SELECT * FROM t_authors", new BeanPropertyRowMapper<>(Author.class));
    }

    @Override
    public Author findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM t_authors WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Author.class));
    }

    @Override
    public void save(Author author) {
        jdbcTemplate.update("INSERT INTO t_authors (name) VALUES(?)", author.getName());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM t_authors WHERE id=?", id);
    }
}
