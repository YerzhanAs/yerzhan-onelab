package kz.library.system.services;

import kz.library.system.models.dto.PublisherCreateDTO;
import kz.library.system.models.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {

    void savePublisher(PublisherCreateDTO publisherCreateDTO);

    void deletePublisherById(Long id);

    PublisherDTO findPublisherById(Long id);

    List<PublisherDTO> findAllPublishers();

    void updatePublisher(Long genreId, PublisherCreateDTO publisherCreateDTO);

}
