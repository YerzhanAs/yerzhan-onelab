package kz.library.system.domains.repositories.impl;

import kz.library.system.domains.entities.Publisher;
import kz.library.system.domains.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PublisherRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Publisher> findAll() {
        return jdbcTemplate.query("SELECT * FROM t_publishers", new BeanPropertyRowMapper<>(Publisher.class));
    }

    @Override
    public Publisher findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM t_publishers WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Publisher.class));
    }

    @Override
    public void save(Publisher publisher) {
        jdbcTemplate.update("INSERT INTO t_publishers (publisher_name, publisher_year) VALUES(?, ?)", publisher.getPublisherName(), publisher.getPublisherYear());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM t_publishers WHERE id=?", id);
    }

    @Override
    public List<Publisher> findByPublisherYear(int publisherYear) {
        return jdbcTemplate.query("SELECT * FROM t_publishers WHERE publisher_year=?", new Object[]{publisherYear},
                new BeanPropertyRowMapper<>(Publisher.class));
    }
}
