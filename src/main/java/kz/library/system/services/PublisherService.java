package kz.library.system.services;

import kz.library.system.domains.repositories.PublisherRepository;
import kz.library.system.models.dto.PublisherDTO;
import kz.library.system.models.mapper.PublisherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kz.library.system.models.mapper.PublisherMapper.dtoToEntity;
import static kz.library.system.models.mapper.PublisherMapper.entityToDto;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public List<PublisherDTO> findAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(PublisherMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public PublisherDTO findPublisherById(Long id) {
        return entityToDto(publisherRepository.findById(id));
    }

    public void deletePublisherById(Long id) {
        publisherRepository.deleteById(id);
    }

    public void savePublisher(PublisherDTO publisherDTO) {
        publisherRepository.save(dtoToEntity(publisherDTO));
    }



}
