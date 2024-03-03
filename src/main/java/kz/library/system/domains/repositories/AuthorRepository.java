package kz.library.system.domains.repositories;

import kz.library.system.domains.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();
    Author findById(Long id);
    void save(Author author);
    void deleteById(Long id);
}
