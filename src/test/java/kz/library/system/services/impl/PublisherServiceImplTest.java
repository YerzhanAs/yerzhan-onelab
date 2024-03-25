package kz.library.system.services.impl;

import kz.library.system.domains.entities.Publisher;
import kz.library.system.domains.repositories.PublisherRepository;
import kz.library.system.models.dto.PublisherCreateDTO;
import kz.library.system.models.dto.PublisherDTO;
import kz.library.system.models.mapper.PublisherMapper;
import kz.library.system.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {
    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private PublisherMapper publisherMapper;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @Test
    void findAllPublishers_ReturnsNonEmptyList_WhenPublishersFound() {
        Publisher publisher = new Publisher();
        List<Publisher> publishers = List.of(publisher);

        PublisherDTO publisherDTO = new PublisherDTO();
        when(publisherRepository.findAll()).thenReturn(publishers);
        when(publisherMapper.toPublisherDTO(any(Publisher.class))).thenReturn(publisherDTO);

        List<PublisherDTO> result = publisherService.findAllPublishers();

        assertFalse(result.isEmpty(), "Result should not be empty");
        assertEquals(1, result.size(), "Result list should contain exactly one element");
        verify(publisherMapper, times(1)).toPublisherDTO(any(Publisher.class));
    }

    @Test
    void findAllPublishers_ThrowsNotFoundException_WhenNoPublishersFound() {
        when(publisherRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> publisherService.findAllPublishers(),
                "Expected findAllPublishers() to throw, but it didn't");

        verify(publisherRepository, times(1)).findAll();
        verify(publisherMapper, never()).toPublisherDTO(any(Publisher.class));
    }

    @Test
    void findPublisherById_ReturnsPublisherDTO_WhenPublisherFound() {
        Long id = 1L;
        Publisher publisher = new Publisher();
        PublisherDTO publisherDTO = new PublisherDTO();

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));
        when(publisherMapper.toPublisherDTO(publisher)).thenReturn(publisherDTO);

        PublisherDTO result = publisherService.findPublisherById(id);

        assertNotNull(result, "PublisherDTO should not be null");
        verify(publisherRepository, times(1)).findById(id);
        verify(publisherMapper, times(1)).toPublisherDTO(publisher);
    }

    @Test
    void findPublisherById_ThrowsNotFoundException_WhenPublisherNotFound() {
        Long id = 1L;
        when(publisherRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> publisherService.findPublisherById(id),
                "Expected findPublisherById() to throw NotFoundException, but it didn't");
    }

    @Test
    void deletePublisherById_CallsDeleteMethodOfRepository() {
        Long id = 1L;

        publisherService.deletePublisherById(id);

        verify(publisherRepository, times(1)).deleteById(id);
    }

    @Test
    void savePublisher_CallsSaveMethodOfRepositoryWithCorrectPublisher() {
        PublisherCreateDTO publisherCreateDTO = new PublisherCreateDTO();
        when(publisherMapper.toPublisher(publisherCreateDTO)).thenReturn(new Publisher());

        publisherService.savePublisher(publisherCreateDTO);

        verify(publisherMapper, times(1)).toPublisher(publisherCreateDTO);
        verify(publisherRepository, times(1)).save(any(Publisher.class));
    }

    @Test
    void updatePublisher_UpdatesExistingPublisher_WhenPublisherFound() {
        Long publisherId = 1L;
        Publisher existingPublisher = new Publisher();
        PublisherCreateDTO publisherCreateDTO = new PublisherCreateDTO();
        publisherCreateDTO.setPublisherName("Updated Name");

        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(existingPublisher));

        publisherService.updatePublisher(publisherId, publisherCreateDTO);

        verify(publisherRepository, times(1)).findById(publisherId);
        verify(publisherRepository, times(1)).save(existingPublisher);
        assertEquals("Updated Name", existingPublisher.getPublisherName());
    }

    @Test
    void updatePublisher_ThrowsNotFoundException_WhenPublisherNotFound() {
        Long publisherId = 1L;
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> publisherService.updatePublisher(publisherId, new PublisherCreateDTO()));
    }

}
