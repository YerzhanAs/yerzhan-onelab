package kz.library.system.services;

import kz.library.system.domains.entities.Book;
import kz.library.system.domains.repositories.BookRepository;
import kz.library.system.models.dto.BookDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testFindAllBooks() {

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", "123456", "English", null, null));
        books.add(new Book(2L, "Book 2", "7891011", "Spanish", null, null));
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> bookDTOs = bookService.findAllBooks();

        assertEquals(2, bookDTOs.size());
        assertEquals("Book 1", bookDTOs.get(0).getTitle());
        assertEquals("Book 2", bookDTOs.get(1).getTitle());
    }

}
