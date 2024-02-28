package kz.library.system.domain.repositories;

import kz.library.system.domain.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Author save(Author author);
    void deleteById(Long id);
}
