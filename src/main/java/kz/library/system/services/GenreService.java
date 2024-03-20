package kz.library.system.services;

import kz.library.system.models.dto.GenreDTO;
import kz.library.system.models.dto.GenreCreateDTO;

import java.util.List;

public interface GenreService {

    List<GenreDTO> findAllGenres();

    GenreDTO findGenreById(Long id);

    void deleteGenreById(Long id);

    void saveGenre(GenreCreateDTO saveGenreDTO);

    void updateGenre(Long courseId, GenreCreateDTO saveGenreDTO);
}
