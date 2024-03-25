package kz.library.system.services.impl;

import kz.library.system.domains.entities.Book;
import kz.library.system.domains.repositories.BookRepository;
import kz.library.system.models.dto.BookCreateDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.mapper.BookMapper;
import kz.library.system.models.request.SearchBookRequest;
import kz.library.system.services.BookService;
import kz.library.system.utils.exceptions.BookAlreadyExistsException;
import kz.library.system.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public List<BookDTO> findAllBooks(){
        List<Book> books = bookRepository.findAll();

        if(books.isEmpty()){
            throw  new NotFoundException("No books found");
        }

        return books.stream()
                .map(bookMapper::toBookDTO)
                .toList();
    }

    @Override
    public BookDTO findBookById(Long id) {
        return  bookMapper.toBookDTO(bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id)));
    }

    @Transactional
    @Override
    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveBook(BookCreateDTO bookCreateDTO){
        if (bookRepository.existsByIsbn(bookCreateDTO.getIsbn())) {
            throw new BookAlreadyExistsException("Book with ISBN " + bookCreateDTO.getIsbn() + " already exists.");
        }
        bookRepository.save(bookMapper.toBook(bookCreateDTO));
    }

    @Override
    public Page<BookDTO> search(SearchBookRequest request, Pageable pageable) {
        return bookRepository.searchBooks(pageable, request.getIsbn(),
                        request.getLanguage(), request.getTitle()).map(bookMapper::toBookDTO);
    }
    @Transactional
    @Override
    public void updateBook(Long id, BookCreateDTO bookCreateDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));

        Book book = bookMapper.toBook(bookCreateDTO);

        existingBook.setTitle(book.getTitle());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setLanguage(book.getLanguage());
        existingBook.setGenres(book.getGenres());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublishers(book.getPublishers());

        bookRepository.save(existingBook);
    }

    @Override
    public List<BookDTO> findBooksByAuthor(String authorName) {
        return bookRepository.findBooksByAuthorName(authorName).stream()
                .map(bookMapper::toBookDTO)
                .toList();
    }

    @Override
    public List<BookDTO> findBooksByGenreName(String genreName) {
        return bookRepository.findBooksByGenreName(genreName).stream()
                .map(bookMapper::toBookDTO)
                .toList();
    }

    @Override
    public Long countBooksByAuthor(String authorName){
        return bookRepository.countBooksByAuthor(authorName);
    }

    @Transactional
    @Override
    public void saveAllBook(List<BookDTO> bookDTOList){
        List<Book> books = bookDTOList.stream()
                .map(bookMapper::toBook)
                .toList();

        bookRepository.saveAll(books);
    }

}
