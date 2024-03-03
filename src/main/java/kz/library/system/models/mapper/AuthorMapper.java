package kz.library.system.models.mapper;

import kz.library.system.domains.entities.Author;
import kz.library.system.models.dto.AuthorDTO;

public class AuthorMapper {

    public static AuthorDTO entityToDto(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

    public static Author dtoToEntity(AuthorDTO authorDTO) {
        return Author.builder()
                .id(authorDTO.getId())
                .name(authorDTO.getName())
                .build();
    }
}
