package kz.library.system.services.impl;

import kz.library.system.domains.entities.Author;
import kz.library.system.domains.entities.Book;
import kz.library.system.domains.repositories.BookRepository;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.dto.GenreDTO;
import kz.library.system.models.mapper.AuthorMapper;
import kz.library.system.services.impl.BookServiceImpl;
import kz.library.system.utils.exceptions.BookAlreadyExistsException;
import kz.library.system.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testFindAllBooks_WhenBooksExist() {
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(Book.builder().build());
        mockBooks.add(Book.builder().build());

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<BookDTO> result = bookService.findAllBooks();

        assertEquals(2, result.size());
        assertNotNull(result);
    }

    @Test
    void testFindAllBooks_WhenNoBooksExist() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> {
            bookService.findAllBooks();
        });
    }

    @Test
    void testFindBookById_WhenBookExists() {
        Long id = 1L;
        Book mockBook = Book.builder().build();
        mockBook.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(mockBook));

        BookDTO result = bookService.findBookById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testFindBookById_WhenBookDoesNotExist() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            bookService.findBookById(id);
        });
    }

    @Test
    void testDeleteBookById_WhenBookExists() {
        Long id = 1L;

        bookService.deleteBookById(id);

        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteBookById_WhenBookDoesNotExist() {
        Long id = 1L;
        doThrow(EmptyResultDataAccessException.class).when(bookRepository).deleteById(id);

        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookService.deleteBookById(id);
        });
    }


    @Test
    void testSaveBook_WhenBookDoesNotExist() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn("1234567890");
        when(bookRepository.existsByIsbn(bookDTO.getIsbn())).thenReturn(false);

        assertDoesNotThrow(() -> {
            bookService.saveBook(bookDTO);
        });

        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void testSaveBook_WhenBookExists() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn("1234567890");
        when(bookRepository.existsByIsbn(bookDTO.getIsbn())).thenReturn(true);

        BookAlreadyExistsException exception = assertThrows(BookAlreadyExistsException.class, () -> {
            bookService.saveBook(bookDTO);
        });
        assertEquals("Book with ISBN " + bookDTO.getIsbn() + " already exists.", exception.getMessage());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testFindBookByIsbnAndLanguage() {
        String isbn = "1234567890";
        String language = "English";
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book());
        mockBooks.add(new Book());
        when(bookRepository.findBooksByIsbnAndLanguage(isbn, language)).thenReturn(mockBooks);

        List<BookDTO> result = bookService.findBookByIsbnAndLanguage(isbn, language);

        assertEquals(mockBooks.size(), result.size());
    }

    @Test
    void testUpdateBook_WhenBookExists() {
        // Arrange
        Long id = 1L;
        BookDTO updatedBookDTO = new BookDTO();
        updatedBookDTO.setTitle("Updated Title");
        updatedBookDTO.setIsbn("1234567890");
        updatedBookDTO.setLanguage("English");
        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setTitle("Original Title");
        existingBook.setIsbn("0987654321");
        existingBook.setLanguage("Spanish");
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));

        assertDoesNotThrow(() -> {
            bookService.updateBook(id, updatedBookDTO);
        });

        assertEquals(updatedBookDTO.getTitle(), existingBook.getTitle());
        assertEquals(updatedBookDTO.getIsbn(), existingBook.getIsbn());
        assertEquals(updatedBookDTO.getLanguage(), existingBook.getLanguage());
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testUpdateBook_WhenBookDoesNotExist() {
        Long id = 1L;
        BookDTO updatedBookDTO = new BookDTO();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookService.updateBook(id, updatedBookDTO);
        });
        assertEquals("Book not found with ID: " + id, exception.getMessage());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testFindBooksByAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(Book.builder().build());
        mockBooks.add(Book.builder().build());
        when(bookRepository.findBooksByAuthor(any())).thenReturn(mockBooks);

        List<Book> result = bookService.findBooksByAuthor(authorDTO);

        assertEquals(mockBooks.size(), result.size());
    }

    @Test
    void testFindBooksByGenres() {
        String genreName = "Fantasy";
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(Book.builder().build());
        expectedBooks.add(Book.builder().build());
        when(bookRepository.findBooksByGenre(genreName)).thenReturn(expectedBooks);

        List<Book> result = bookService.findBooksByGenres(genreName);

        assertEquals(expectedBooks.size(), result.size());
    }

    @Test
    void testCountBooksByAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        long expectedCount = 5L;
        when(bookRepository.countBooksByAuthor(any(Author.class))).thenReturn(expectedCount);

        long result = bookService.countBooksByAuthor(authorDTO);

        assertEquals(expectedCount, result);
    }

    @Test
    void testUpdateBookGenre() {
        Book book = new Book();
        book.setId(1L);
        book.setGenres(new HashSet<>());

        GenreDTO newGenre = new GenreDTO();
        newGenre.setGenreName("Gaming");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.updateBookGenre(1L, newGenre);

        verify(bookRepository, times(1)).save(book);


        assertEquals(1, book.getGenres().size());
        assertEquals("Gaming", book.getGenres().iterator().next().getGenreName());
    }


}
