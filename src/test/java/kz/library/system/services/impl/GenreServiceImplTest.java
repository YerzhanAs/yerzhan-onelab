package kz.library.system.services.impl;

import kz.library.system.domains.entities.Genre;
import kz.library.system.domains.repositories.GenreRepository;
import kz.library.system.models.dto.GenreCreateDTO;
import kz.library.system.models.dto.GenreDTO;
import kz.library.system.models.mapper.GenreMapper;
import kz.library.system.utils.exceptions.GenreExistException;
import kz.library.system.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    public void findAllGenres_ReturnsNonEmptyList_WhenGenresFound() {
        Genre genre = new Genre();
        List<Genre> genres = List.of(genre);

        GenreDTO genreDTO = new GenreDTO();
        when(genreRepository.findAll()).thenReturn(genres);
        when(genreMapper.toGenreDTO(any(Genre.class))).thenReturn(genreDTO);

        List<GenreDTO> result = genreService.findAllGenres();

        assertFalse(result.isEmpty(), "Result should not be empty");
        assertEquals(1, result.size(), "Result list should contain exactly one element");
        verify(genreMapper, times(1)).toGenreDTO(any(Genre.class));
    }

    @Test
    public void findAllGenres_ThrowsNotFoundException_WhenNoGenresFound() {
        when(genreRepository.findAll()).thenReturn(Collections.emptyList());

        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> genreService.findAllGenres(),
                "Expected findAllGenres() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("No genres found"), "Exception message should indicate no genres were found");
    }


    @Test
    public void findGenreById_ReturnsGenreDTO_WhenGenreFound() {
        Long genreId = 1L;
        Genre mockGenre = new Genre();
        GenreDTO mockGenreDTO = GenreDTO.builder()
                .id(genreId)
                .genreName("Fantasy")
                .build();

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(mockGenre));
        when(genreMapper.toGenreDTO(mockGenre)).thenReturn(mockGenreDTO);

        GenreDTO resultDTO = genreService.findGenreById(genreId);

        assertNotNull(resultDTO, "GenreDTO should not be null");
        assertEquals(genreId, resultDTO.getId(), "GenreDTO ID should match");
        assertEquals("Fantasy", resultDTO.getGenreName(), "GenreDTO name should match");

        verify(genreRepository, times(1)).findById(genreId);
        verify(genreMapper, times(1)).toGenreDTO(mockGenre);
    }

    @Test
    public void findGenreById_ThrowsNotFoundException_WhenGenreNotFound() {
        Long genreId = 1L;
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> genreService.findGenreById(genreId),
                "Expected findGenreById() to throw NotFoundException, but it didn't");

        verify(genreRepository, times(1)).findById(genreId);
        verify(genreMapper, never()).toGenreDTO(any(Genre.class));
    }

    @Test
    public void deleteGenreById_CallsDeleteMethodOfRepository() {
        Long genreId = 1L;

        genreService.deleteGenreById(genreId);

        verify(genreRepository, times(1)).deleteById(genreId);
    }

    @Test
    public void saveGenre_ThrowsGenreExistException_WhenGenreAlreadyExists() {
        GenreCreateDTO saveGenreDTO = GenreCreateDTO.builder()
                                    .genreName("Fantasy")
                                    .build();

        when(genreRepository.existsGenreByGenreName("Fantasy")).thenReturn(true);

        assertThrows(GenreExistException.class, () -> genreService.saveGenre(saveGenreDTO));

        verify(genreRepository, never()).save(any());
    }

    @Test
    public void saveGenre_SavesGenre_WhenNewGenre() {
        GenreCreateDTO saveGenreDTO = new GenreCreateDTO();
        saveGenreDTO.setGenreName("Fantasy");
        Genre genre = new Genre();

        when(genreRepository.existsGenreByGenreName("Fantasy")).thenReturn(false);
        when(genreMapper.toGenre(saveGenreDTO)).thenReturn(genre);

        genreService.saveGenre(saveGenreDTO);

        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    public void updateGenre_SuccessfullyUpdatesExistingGenre() {
        Long genreId = 1L;
        Genre existingGenre = Genre.builder()
                .id(genreId)
                .genreName("Original Name")
                .build();

        GenreCreateDTO saveGenreDTO = GenreCreateDTO.builder()
                .genreName("Updated Name")
                .description("Updated Description")
                .build();

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(existingGenre));
        when(genreRepository.existsGenreByGenreName("Updated Name")).thenReturn(false);

        genreService.updateGenre(genreId, saveGenreDTO);

        verify(genreRepository, times(1)).save(existingGenre);
        assertEquals("Updated Name", existingGenre.getGenreName());
        assertEquals("Updated Description", existingGenre.getDescription());
    }

    @Test
    public void updateGenre_ThrowsGenreExistException_WhenGenreNameAlreadyExists() {
        Long genreId = 1L;
        Genre existingGenre = Genre.builder()
                .id(genreId)
                .genreName("Original Name")
                .build();

        GenreCreateDTO saveGenreDTO = GenreCreateDTO.builder()
                .genreName("Existing Name")
                .build();

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(existingGenre));
        when(genreRepository.existsGenreByGenreName("Existing Name")).thenReturn(true);

        assertThrows(GenreExistException.class, () -> genreService.updateGenre(genreId, saveGenreDTO));
    }

}
