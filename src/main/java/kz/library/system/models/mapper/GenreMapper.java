package kz.library.system.models.mapper;

import kz.library.system.domains.entities.Genre;
import kz.library.system.models.dto.GenreDTO;
import kz.library.system.models.dto.GenreCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDTO toGenreDTO(Genre genre);

    Genre toGenre(GenreDTO genreDTO);

    Genre toGenre(GenreCreateDTO genreCreateDTO);

}
