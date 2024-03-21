package kz.library.system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.library.system.models.dto.BookCreateDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.request.SearchBookRequest;
import kz.library.system.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;


    @Test
    public void getAllBooks_ReturnsListAndStatusOk() throws Exception {
        BookDTO book1 = BookDTO.builder()
                .title("The best practice of Java")
                .isbn("452-0-306-12215-7")
                .language("English")
                .build();

        List<BookDTO> books = Collections.singletonList(book1);

        given(bookService.findAllBooks()).willReturn(books);

        mockMvc.perform(get("/api/v1/books/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(books.get(0).getId()))
                .andExpect(jsonPath("$[0].title").value("The best practice of Java"));
    }

    @Test
    public void getBookById_ReturnsBookAndStatusOk() throws Exception {
        Long bookId = 1L;

        BookDTO book1 = BookDTO.builder()
                .id(1L)
                .title("The best practice of Java")
                .isbn("452-0-306-12215-7")
                .language("English")
                .build();

        given(bookService.findBookById(bookId)).willReturn(book1);

        mockMvc.perform(get("/api/v1/books/{id}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("The best practice of Java"));
    }

    @Test
    void saveBook_ReturnsHttpStatusOk() throws Exception {
        BookCreateDTO bookCreateDTO = BookCreateDTO.builder()
                .title("The best practice of Java")
                .isbn("452-0-306-12215-7")
                .language("English")
                .build();
        doNothing().when(bookService).saveBook(bookCreateDTO);

        mockMvc.perform(put("/api/v1/books/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBook_ReturnsHttpStatusOk() throws Exception {
        Long bookId = 1L;
        doNothing().when(bookService).deleteBookById(bookId);

        mockMvc.perform(delete("/api/v1/books/delete/{id}", bookId))
                .andExpect(status().isOk());
    }

    @Test
    void updateBook_ReturnsHttpStatusOk() throws Exception {
        Long bookId = 1L;
        BookCreateDTO bookCreateDTO = BookCreateDTO.builder()
                .title("The best practice of Java")
                .isbn("452-0-306-12215-7")
                .language("English")
                .build();
        doNothing().when(bookService).updateBook(bookId, bookCreateDTO);

        mockMvc.perform(put("/api/v1/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void getBookByAuthorName_ReturnsBooksAndStatusOk() throws Exception {
        String authorName = "Author Name";
        List<BookDTO> books = Collections.singletonList(new BookDTO());
        given(bookService.findBooksByAuthor(authorName)).willReturn(books);

        mockMvc.perform(get("/api/v1/books/bookbyauthor")
                        .param("authorName", authorName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty());
    }

    @Test
    void getBookByGenreName_ReturnsBooksAndStatusOk() throws Exception {
        String genreName = "Fantasy";
        List<BookDTO> books = Collections.singletonList(new BookDTO());
        given(bookService.findBooksByGenreName(genreName)).willReturn(books);

        mockMvc.perform(get("/api/v1/books/bookbygenre")
                        .param("genreName", genreName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty());
    }

    @Test
    void searchBooks_ReturnsPageOfBooksAndStatusOk() throws Exception {
        BookDTO book1 = BookDTO.builder()
                .title("The best practice of Java")
                .isbn("452-0-306-12215-7")
                .language("English")
                .build();

        SearchBookRequest request = new SearchBookRequest();
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookDTO> bookPage = new PageImpl<>(Collections.singletonList(book1), pageable, 1);

        given(bookService.search(request, pageable)).willReturn(bookPage);

        mockMvc.perform(post("/api/v1/books/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
