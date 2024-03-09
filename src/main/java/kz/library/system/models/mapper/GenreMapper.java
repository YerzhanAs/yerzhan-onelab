package kz.library.system.models.mapper;

import kz.library.system.domains.entities.Genre;
import kz.library.system.models.dto.GenreDTO;

public class GenreMapper {

    public static GenreDTO entityToDto(Genre genre){
        return GenreDTO.builder()
                .id(genre.getId())
                .genreName(genre.getGenreName())
                .description(genre.getDescription())
                .build();
    }

    public static Genre dtoToEntity(GenreDTO genreDTO){
        return Genre.builder()
                .id(genreDTO.getId())
                .genreName(genreDTO.getGenreName())
                .description(genreDTO.getDescription())
                .build();
    }
}
