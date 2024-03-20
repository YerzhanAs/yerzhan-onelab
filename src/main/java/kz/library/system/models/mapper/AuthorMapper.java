package kz.library.system.models.mapper;

import kz.library.system.domains.entities.Author;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.dto.AuthorCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO toAuthorDTO(Author author);

    Author toAuthor(AuthorDTO authorDTO);

    Author toAuthor(AuthorCreateDTO authorCreateDTO);
}
