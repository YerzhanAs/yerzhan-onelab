package kz.library.system.services.impl;

import kz.library.system.domains.entities.Publisher;
import kz.library.system.domains.repositories.PublisherRepository;
import kz.library.system.models.dto.PublisherDTO;
import kz.library.system.models.mapper.PublisherMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceImplTest {
    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @Test
    void testFindAllPublishers() {
        List<Publisher> publishers = new ArrayList<>();
        publishers.add(Publisher.builder().build());
        publishers.add(Publisher.builder().build());
        when(publisherRepository.findAll()).thenReturn(publishers);

        List<PublisherDTO> result = publisherService.findAllPublishers();

        assertEquals(2, result.size());
    }

    @Test
    void testFindPublisherById() {
        Long publisherId = 1L;
        Publisher publisher = Publisher.builder().id(publisherId).publisherName("Publisher1").build();
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher));

        PublisherDTO result = publisherService.findPublisherById(publisherId);

        assertEquals("Publisher1", result.getPublisherName());
    }

    @Test
    void testDeletePublisherById() {
        Long publisherId = 1L;
        publisherService.deletePublisherById(publisherId);

        verify(publisherRepository, times(1)).deleteById(publisherId);
    }

}
