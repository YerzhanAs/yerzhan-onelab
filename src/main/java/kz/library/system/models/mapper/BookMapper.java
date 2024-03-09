package kz.library.system.models.mapper;

import kz.library.system.domains.entities.Book;
import kz.library.system.models.dto.BookDTO;

// TODO : ADD MAPSTRUCT
public class BookMapper {

    public static BookDTO entityToDto(Book book){
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .language(book.getLanguage())
                .publisher(book.getPublisher())
                .build();
    }

    public static Book dtoToEntity(BookDTO bookDTO){
        return Book.builder()
                .id(bookDTO.getId())
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .language(bookDTO.getLanguage())
                .publisher(bookDTO.getPublisher())
                .build();
    }
}
