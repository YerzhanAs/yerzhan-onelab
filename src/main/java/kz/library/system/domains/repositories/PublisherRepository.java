package kz.library.system.domains.repositories;

import kz.library.system.domains.entities.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {

    List<Publisher> findAll();
    Publisher findById(Long id);
    void save(Publisher publisher);
    void deleteById(Long id);

    List<Publisher> findByPublisherYear(int publisherYear);
}
