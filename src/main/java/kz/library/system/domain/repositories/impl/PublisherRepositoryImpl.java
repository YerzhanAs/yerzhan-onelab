package kz.library.system.domain.repositories.impl;

import kz.library.system.domain.entities.Publisher;
import kz.library.system.domain.repositories.PublisherRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

    private static final List<Publisher> publishers = new ArrayList<>();

    @Override
    public List<Publisher> findAll() {
        return publishers;
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publishers.stream().filter(publisher -> publisher.getId().equals(id)).findFirst();
    }

    @Override
    public Publisher save(Publisher publisher) {
        publisher.setId((long) (publishers.size() + 1));
        publishers.add(publisher);
        return publisher;
    }

    @Override
    public void deleteById(Long id) {
        publishers.removeIf(publisher -> publisher.getId().equals(id));
    }

    @Override
    public List<Publisher> findPublisherByPublishYear(int year) {
        return publishers.stream().filter(publisher -> publisher.getPublisherYear()==year).toList();
    }
}
