package kz.library.system.services;

import kz.library.system.models.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {

    void savePublisher(PublisherDTO publisherDTO);

    void deletePublisherById(Long id);

    PublisherDTO findPublisherById(Long id);

    List<PublisherDTO> findAllPublishers();

}
