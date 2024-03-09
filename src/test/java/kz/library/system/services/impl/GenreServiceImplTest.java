package kz.library.system.services.impl;

import kz.library.system.domains.repositories.GenreRepository;
import kz.library.system.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;


    @Test
    void testFindAllGenresEmpty() {
        when(genreRepository.findAll()).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> genreService.findAllGenres());
    }


    @Test
    void testFindGenreByIdNotFound() {
        Long genreId = 99L;
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> genreService.findGenreById(genreId));
    }




}
