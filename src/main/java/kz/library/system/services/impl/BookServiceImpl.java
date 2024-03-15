package kz.library.system.services.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kz.library.system.domains.entities.Author;
import kz.library.system.domains.entities.Book;
import kz.library.system.domains.entities.Genre;
import kz.library.system.domains.repositories.BookRepository;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.dto.GenreDTO;
import kz.library.system.models.mapper.AuthorMapper;
import kz.library.system.models.mapper.GenreMapper;
import kz.library.system.services.BookService;
import kz.library.system.utils.exceptions.BookAlreadyExistsException;
import kz.library.system.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import kz.library.system.models.mapper.BookMapper;
import org.springframework.transaction.annotation.Transactional;
import static kz.library.system.models.mapper.BookMapper.dtoToEntity;
import static kz.library.system.models.mapper.BookMapper.entityToDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<BookDTO> findAllBooks(){
        List<Book> books = bookRepository.findAll();

        if(books.isEmpty()){
            throw  new NotFoundException("No books found");
        }

        return books.stream()
                .map(BookMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO findBookById(Long id) {
        return entityToDto(bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id)));
    }

    @Transactional
    @Override
    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveBook(BookDTO bookDTO){
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new BookAlreadyExistsException("Book with ISBN " + bookDTO.getIsbn() + " already exists.");
        }
        bookRepository.save(dtoToEntity(bookDTO));
    }

    @Override
    public List<BookDTO> findBookByIsbnAndLanguage(String isbn, String language) {
        return bookRepository.findBooksByIsbnAndLanguage(isbn, language).stream()
                .map(BookMapper::entityToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public void updateBook(Long id, BookDTO updatedBookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));

        existingBook.setTitle(updatedBookDTO.getTitle());
        existingBook.setIsbn(updatedBookDTO.getIsbn());
        existingBook.setLanguage(updatedBookDTO.getLanguage());
        existingBook.setGenres(updatedBookDTO.getGenres());
        existingBook.setAuthor(updatedBookDTO.getAuthor());
        existingBook.setPublisher(updatedBookDTO.getPublisher());

        bookRepository.save(existingBook);
    }

    @Override
    public List<Book> findBooksByAuthor(AuthorDTO authorDTO) {
        Author author = AuthorMapper.dtoToEntity(authorDTO);
        return bookRepository.findBooksByAuthor(author);
    }

    @Override
    public List<Book> findBooksByGenres(String genreName) {
        return bookRepository.findBooksByGenre(genreName);
    }

    @Override
    public Long countBooksByAuthor(AuthorDTO authorDTO){
        Author author = AuthorMapper.dtoToEntity(authorDTO);
        return bookRepository.countBooksByAuthor(author);
    }

    @Transactional
    public void updateBookGenre(Long bookId, GenreDTO newGenre) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId));

        book.getGenres().clear();

        Genre genre = GenreMapper.dtoToEntity(newGenre);
        book.getGenres().add(genre);

        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void saveAllBook(List<BookDTO> bookDTOList){
        List<Book> books = bookDTOList.stream()
                .map(BookMapper::dtoToEntity)
                .collect(Collectors.toList());

        bookRepository.saveAll(books);
    }


}
