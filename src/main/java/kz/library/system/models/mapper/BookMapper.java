package kz.library.system.models.mapper;

import kz.library.system.domains.entities.Book;
import kz.library.system.models.dto.BookCreateDTO;
import kz.library.system.models.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toBookDTO(Book book);

    Book toBook(BookDTO bookDTO);

    Book toBook(BookCreateDTO bookCreateDTO);
}
