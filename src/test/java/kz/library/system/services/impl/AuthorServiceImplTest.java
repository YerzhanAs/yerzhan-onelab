package kz.library.system.services.impl;

import kz.library.system.domains.entities.Author;
import kz.library.system.domains.repositories.AuthorRepository;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private List<Author> authors;

    @BeforeEach
    void setUp() {
        authors = new ArrayList<>();
        authors.add(Author.builder().id(1L).name("John Doe").build());
        authors.add(Author.builder().id(2L).name("Jane Smith").build());
    }

    @Test
    void testFindAllAuthors() {
        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorDTO> authorDTOs = authorService.findAllAuthors();

        assertEquals(authors.size(), authorDTOs.size());
    }

    @Test
    void testFindAllAuthorsWhenEmpty() {
        when(authorRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> authorService.findAllAuthors());
    }

    @Test
    void testFindAuthorById() {
        Long authorId = 1L;
        Author author = authors.get(0);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        AuthorDTO authorDTO = authorService.findAuthorById(authorId);

        assertEquals(author.getId(), authorDTO.getId());
        assertEquals(author.getName(), authorDTO.getName());
    }

    @Test
    void testFindAuthorByIdNotFound() {
        Long authorId = 99L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.findAuthorById(authorId));
    }

    @Test
    void testDeleteAuthorById() {
        Long authorId = 1L;

        authorService.deleteAuthorById(authorId);

        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    void testSaveAuthor() {
        AuthorDTO authorDTO = AuthorDTO.builder().name("John Doe").build();

        authorService.saveAuthor(authorDTO);

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testUpdateAuthor() {
        Long authorId = 1L;
        Author existingAuthor = Author.builder().id(authorId).name("John Doe").build();
        AuthorDTO updatedAuthorDTO = AuthorDTO.builder().id(authorId).name("Jane Smith").build();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));

        authorService.updateAuthor(authorId, updatedAuthorDTO);

        verify(authorRepository, times(1)).save(existingAuthor);
        assertEquals(updatedAuthorDTO.getName(), existingAuthor.getName());
    }

    @Test
    void testUpdateAuthorNotFound() {
        Long authorId = 99L;
        AuthorDTO updatedAuthorDTO = AuthorDTO.builder().id(authorId).name("Jane Smith").build();
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.updateAuthor(authorId, updatedAuthorDTO));
    }
}
