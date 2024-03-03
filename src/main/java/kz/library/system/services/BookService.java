package kz.library.system.services;

import kz.library.system.domains.entities.Book;
import kz.library.system.domains.repositories.BookRepository;
import kz.library.system.models.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import kz.library.system.models.mapper.BookMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kz.library.system.models.mapper.BookMapper.dtoToEntity;
import static kz.library.system.models.mapper.BookMapper.entityToDto;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDTO> findAllBooks(){
        return bookRepository.findAll().stream()
                .map(BookMapper::entityToDto)
                .collect(Collectors.toList());
    }


    public BookDTO findBookById(Long id) {
        return entityToDto(bookRepository.findById(id));
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

        return foundBook.map(BookMapper::entityToDto).orElse(null);
    }


}
