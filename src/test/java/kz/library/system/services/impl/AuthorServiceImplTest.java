package kz.library.system.services.impl;

import kz.library.system.domains.entities.Author;
import kz.library.system.domains.repositories.AuthorRepository;
import kz.library.system.models.dto.AuthorCreateDTO;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.mapper.AuthorMapper;
import kz.library.system.services.AuthorService;
import kz.library.system.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void findAllAuthors_ReturnsAuthorList() {

        List<Author> authors = authorList();

        AuthorDTO authorDTO = AuthorDTO.builder().build();
        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.toAuthorDTO(any(Author.class))).thenReturn(authorDTO);

        List<AuthorDTO> result = authorService.findAllAuthors();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        verify(authorRepository).findAll();
    }

    @Test
    public void findAllAuthors_ThrowsNotFoundException_WhenNoAuthorsFound() {
        when(authorRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> authorService.findAllAuthors());

        verify(authorRepository, times(1)).findAll();
        verify(authorMapper, never()).toAuthorDTO(any(Author.class));
    }

    @Test
    public void findAuthorById_ReturnsAuthorDTO_WhenAuthorFound() {
        Long authorId = 1L;
        Author mockAuthor = Author.builder()
                .id(authorId)
                .name("Yerzhan")
                .build();
        AuthorDTO mockAuthorDTO = AuthorDTO.builder()
                .id(authorId)
                .name("Yerzhan")
                .build();;

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(mockAuthor));
        when(authorMapper.toAuthorDTO(mockAuthor)).thenReturn(mockAuthorDTO);

        AuthorDTO resultDTO = authorService.findAuthorById(authorId);

        assertNotNull(resultDTO);
        assertEquals(authorId, resultDTO.getId());
        assertEquals("Yerzhan", resultDTO.getName());

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorMapper, times(1)).toAuthorDTO(mockAuthor);
    }

    @Test
    public void findAuthorById_ThrowsNotFoundException_WhenAuthorNotFound() {
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.findAuthorById(authorId));

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorMapper, never()).toAuthorDTO(any());
    }

    @Test
    public void deleteAuthorById_CallsDeleteMethodOfRepository() {
        Long authorId = 1L;

        authorService.deleteAuthorById(authorId);

        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    public void saveAuthor_CallsSaveMethodOfRepositoryWithCorrectAuthor() {
        // Arrange
        AuthorCreateDTO authorCreateDTO = new AuthorCreateDTO();
        authorCreateDTO.setName("Yerzhan");
        Author mappedAuthor = new Author();
        mappedAuthor.setName("Yerzhan");

        when(authorMapper.toAuthor(authorCreateDTO)).thenReturn(mappedAuthor);

        authorService.saveAuthor(authorCreateDTO);

        verify(authorMapper, times(1)).toAuthor(authorCreateDTO);
        verify(authorRepository, times(1)).save(mappedAuthor);
    }

    @Test
    public void updateAuthor_SuccessfullyUpdatesExistingAuthor() {
        Long authorId = 1L;
        Author existingAuthor = Author.builder()
                .id(authorId)
                .name("Yerz")
                .build();

        AuthorCreateDTO authorCreateDTO = AuthorCreateDTO.builder()
                                         .name("YerzBuzz")
                                         .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));

        authorService.updateAuthor(authorId, authorCreateDTO);

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).save(existingAuthor);
        assertEquals("YerzBuzz", existingAuthor.getName(), "Author's name should be updated");
    }

    @Test
    public void updateAuthor_ThrowsNotFoundException_WhenAuthorNotFound() {
        Long authorId = 1L;
        AuthorCreateDTO authorCreateDTO =AuthorCreateDTO.builder()
                .name("Arman")
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> authorService.updateAuthor(authorId, authorCreateDTO),
                "Expected updateAuthor() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Author not found with ID: " + authorId), "Exception message should indicate author was not found");
    }


    public List<Author> authorList(){
        Author author = Author.builder()
                .id(1L)
                .name("Yerzhan")
                .build();

        Author author2 = Author.builder()
                .id(1L)
                .name("Yerzhan")
                .build();

        return List.of(author, author2);
    }
}
