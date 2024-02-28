package kz.library.system.services;

import kz.library.system.domain.entities.Book;
import kz.library.system.domain.repositories.BookRepository;
import kz.library.system.model.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDTO> findAllBooks(){
        return bookRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }


    public BookDTO findBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(this::entityToDto).orElse(null);
    }

    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }

    public void saveBook(BookDTO bookDTO){
        bookRepository.save(dtoToEntity(bookDTO));
    }

    public BookDTO findBookByLanguageAndIsbn(String language, String isbn){
        List<Book> books = bookRepository.findAll();

        Optional<Book> foundBook = books.stream()
                .filter(b -> b.getLanguage().equals(language) && b.getIsbn().equals(isbn))
                .findFirst();

        return foundBook.map(this::entityToDto).orElse(null);
    }

    private BookDTO entityToDto(Book book){
        return BookDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .language(book.getLanguage())
                .publisher(book.getPublisher())
                .build();
    }

    private Book dtoToEntity(BookDTO bookDTO){
        return Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .language(bookDTO.getLanguage())
                .publisher(bookDTO.getPublisher())
                .build();
    }



}
