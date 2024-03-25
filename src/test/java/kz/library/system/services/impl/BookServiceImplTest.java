package kz.library.system.services.impl;


import kz.library.system.domains.entities.Book;
import kz.library.system.domains.repositories.BookRepository;
import kz.library.system.models.dto.BookCreateDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.mapper.BookMapper;
import kz.library.system.models.request.SearchBookRequest;
import kz.library.system.utils.exceptions.BookAlreadyExistsException;
import kz.library.system.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void findAllBooks_ReturnsNonEmptyList_WhenBooksFound() {
        Book book = new Book();
        List<Book> books = List.of(book);

        BookDTO bookDTO = new BookDTO();
        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(bookDTO);

        List<BookDTO> result = bookService.findAllBooks();

        assertFalse(result.isEmpty(), "Result should not be empty");
        assertEquals(1, result.size(), "Result list should contain exactly one element");
        verify(bookMapper, times(1)).toBookDTO(any(Book.class));
    }

    @Test
    void findAllBooks_ThrowsNotFoundException_WhenNoBooksFound() {

        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> bookService.findAllBooks(),
                "Expected findAllBooks() to throw, but it didn't");
    }

    @Test
    void findBookById_ReturnsBookDTO_WhenBookFound() {
        Long id = 1L;
        Book book = new Book();
        BookDTO bookDTO = new BookDTO();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toBookDTO(book)).thenReturn(bookDTO);

        BookDTO result = bookService.findBookById(id);

        assertNotNull(result, "BookDTO should not be null");
        verify(bookRepository, times(1)).findById(id);
        verify(bookMapper, times(1)).toBookDTO(book);
    }

    @Test
    void findBookById_ThrowsNotFoundException_WhenBookNotFound() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findBookById(id),
                "Expected findBookById() to throw NotFoundException, but it didn't");
    }

    @Test
    void deleteBookById_CallsDeleteMethodOfRepository() {
        Long id = 1L;

        bookService.deleteBookById(id);

        verify(bookRepository, times(1)).deleteById(id);
    }


    @Test
    void saveBook_ThrowsBookAlreadyExistsException_WhenIsbnExists() {
        BookCreateDTO bookCreateDTO = new BookCreateDTO();
        bookCreateDTO.setIsbn("978-0-12345-678-9");

        when(bookRepository.existsByIsbn("978-0-12345-678-9")).thenReturn(true);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.saveBook(bookCreateDTO));
    }

    @Test
    void search_ReturnsPageOfBookDTO_WhenBooksFound() {
        SearchBookRequest request = new SearchBookRequest();
        request.setIsbn("123-456-789");
        request.setLanguage("English");
        request.setTitle("Test Book");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(new Book()));

        when(bookRepository.searchBooks(pageable, "123-456-789", "English", "Test Book")).thenReturn(bookPage);
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(new BookDTO());

        Page<BookDTO> result = bookService.search(request, pageable);

        assertFalse(result.isEmpty(), "Result should not be empty");
        assertEquals(1, result.getTotalElements(), "There should be exactly one book found");
    }

    @Test
    void updateBook_UpdatesAndSavesBook_WhenFound() {
        Long id = 1L;
        Book existingBook = new Book();
        existingBook.setTitle("The star wars");
        BookCreateDTO bookCreateDTO = new BookCreateDTO();
        Book updatedBook = new Book();
        updatedBook.setTitle("The star wars");

        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookMapper.toBook(bookCreateDTO)).thenReturn(updatedBook);

        bookService.updateBook(id, bookCreateDTO);

        verify(bookRepository, times(1)).save(existingBook);
        assertNotNull(existingBook.getTitle());
    }

    @Test
    void updateBook_ThrowsNotFoundException_WhenBookNotFound() {
        Long id = 1L;
        BookCreateDTO bookCreateDTO = new BookCreateDTO();

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.updateBook(id, bookCreateDTO));
    }

    @Test
    void findBooksByAuthor_ReturnsListOfBookDTO_WhenBooksFound() {
        String authorName = "Author Name";
        List<Book> books = List.of(new Book());
        List<BookDTO> bookDTOs = List.of(new BookDTO());

        when(bookRepository.findBooksByAuthorName(authorName)).thenReturn(books);
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(bookDTOs.get(0));

        List<BookDTO> result = bookService.findBooksByAuthor(authorName);

        assertFalse(result.isEmpty(), "Should return a non-empty list of BookDTO");
        assertEquals(bookDTOs.size(), result.size(), "The returned list size should match the expected size");
    }

    @Test
    void findBooksByGenreName_ReturnsListOfBookDTO_WhenBooksFound() {
        String genreName = "Fantasy";
        List<Book> books = List.of(new Book());
        List<BookDTO> bookDTOs = List.of(new BookDTO());

        when(bookRepository.findBooksByGenreName(genreName)).thenReturn(books);
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(bookDTOs.get(0));

        List<BookDTO> result = bookService.findBooksByGenreName(genreName);

        assertFalse(result.isEmpty(), "Should return a non-empty list of BookDTO");
        assertEquals(bookDTOs.size(), result.size(), "The returned list size should match the expected size");
    }

    @Test
    void countBooksByAuthor_ReturnsCorrectCount_WhenBooksExist() {
        String authorName = "Author Name";
        Long expectedCount = 3L;

        when(bookRepository.countBooksByAuthor(authorName)).thenReturn(expectedCount);

        Long actualCount = bookService.countBooksByAuthor(authorName);

        assertEquals(expectedCount, actualCount, "The count of books by the author should match the expected count");
    }
}
