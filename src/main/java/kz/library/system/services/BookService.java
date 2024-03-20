package kz.library.system.services;

import kz.library.system.domains.entities.Book;
import kz.library.system.models.dto.BookCreateDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.request.SearchBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    List<BookDTO> findAllBooks();

    BookDTO findBookById(Long id);

    void deleteBookById(Long id);

    void saveBook(BookCreateDTO bookCreateDTO);

    void saveAllBook(List<BookDTO> bookDTOList);

    Page<BookDTO> search(SearchBookRequest request, Pageable pageable);

    void updateBook(Long id, BookCreateDTO bookCreateDTO);

    List<BookDTO> findBooksByAuthor(String authorName);

    List<BookDTO> findBooksByGenreName(String genreName);

    Long countBooksByAuthor(String authorName);
}
