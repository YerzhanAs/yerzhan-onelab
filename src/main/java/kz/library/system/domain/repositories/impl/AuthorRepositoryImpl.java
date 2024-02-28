package kz.library.system.domain.repositories.impl;

import kz.library.system.domain.entities.Author;
import kz.library.system.domain.entities.Book;
import kz.library.system.domain.repositories.AuthorRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private static final List<Author> authors = new ArrayList<>();

    @Override
    public List<Author> findAll() {
        return authors;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authors.stream().filter(author -> author.getId().equals(id)).findFirst();
    }

    @Override
    public Author save(Author author) {
        author.setId((long) (authors.size() + 1));
        authors.add(author);
        return author;
    }

    @Override
    public void deleteById(Long id) {
        authors.removeIf(author -> author.getId().equals(id));
    }
}
