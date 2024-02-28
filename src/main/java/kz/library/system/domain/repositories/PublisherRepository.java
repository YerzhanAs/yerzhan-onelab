package kz.library.system.domain.repositories;

import kz.library.system.domain.entities.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {

    List<Publisher> findAll();

    Optional<Publisher> findById(Long id);

    Publisher save(Publisher publisher);

    void deleteById(Long id);

    List<Publisher> findPublisherByPublishYear(int year);
}
