package kz.library.system.services;

import kz.library.system.domains.entities.Book;
import kz.library.system.domains.entities.Genre;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.dto.GenreDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> findAllBooks();

    BookDTO findBookById(Long id);

    void deleteBookById(Long id);

    void saveBook(BookDTO bookDTO);

    void saveAllBook(List<BookDTO> bookDTOList);

    List<BookDTO> findBookByIsbnAndLanguage(String isbn, String language);

    void updateBook(Long id, BookDTO updatedBookDTO);

    List<Book> findBooksByAuthor(AuthorDTO authorDTO);

    List<Book> findBooksByGenres(String genreName);

    Long countBooksByAuthor(AuthorDTO authorDTO);

    void updateBookGenre(Long bookId, GenreDTO newGenre);



}
