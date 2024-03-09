package kz.library.system.services.impl;

import kz.library.system.domains.entities.Publisher;
import kz.library.system.domains.repositories.PublisherRepository;
import kz.library.system.models.dto.PublisherDTO;
import kz.library.system.models.mapper.PublisherMapper;
import kz.library.system.services.PublisherService;
import kz.library.system.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kz.library.system.models.mapper.PublisherMapper.dtoToEntity;
import static kz.library.system.models.mapper.PublisherMapper.entityToDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public List<PublisherDTO> findAllPublishers() {
        List<Publisher> publishers = publisherRepository.findAll();

        if(publishers.isEmpty()){
            throw  new NotFoundException("No publishers found");
        }

        return publishers.stream()
                .map(PublisherMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PublisherDTO findPublisherById(Long id) {
        return entityToDto(publisherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Publisher not found with ID: " + id)));
    }

    @Transactional
    @Override
    public void deletePublisherById(Long id) {
        publisherRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void savePublisher(PublisherDTO publisherDTO) {
        publisherRepository.save(dtoToEntity(publisherDTO));
    }

}
