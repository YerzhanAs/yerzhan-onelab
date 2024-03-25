package kz.library.system.services.impl;

import kz.library.system.domains.entities.Genre;
import kz.library.system.domains.repositories.GenreRepository;
import kz.library.system.models.dto.GenreCreateDTO;
import kz.library.system.models.dto.GenreDTO;
import kz.library.system.models.mapper.GenreMapper;
import kz.library.system.services.GenreService;
import kz.library.system.utils.exceptions.GenreExistException;
import kz.library.system.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Override
    public List<GenreDTO> findAllGenres(){
        List<Genre> genres = genreRepository.findAll();

        if(genres.isEmpty()){
            throw  new NotFoundException("No genres found");
        }

        return genres.stream()
                .map(genreMapper::toGenreDTO)
                .toList();
    }

    @Override
    public GenreDTO findGenreById(Long id) {
        return genreMapper.toGenreDTO(genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found with ID: " + id)));
    }

    @Transactional
    @Override
    public void deleteGenreById(Long id){
        genreRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveGenre(GenreCreateDTO saveGenreDTO){

        if(genreRepository.existsGenreByGenreName(saveGenreDTO.getGenreName()))
            throw new GenreExistException("This Genre is already exist");

        genreRepository.save(genreMapper.toGenre(saveGenreDTO));
    }

    @Override
    @Transactional
    public void updateGenre(Long genreId, GenreCreateDTO saveGenreDTO) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre not found with id "+genreId));

        if(genreRepository.existsGenreByGenreName(saveGenreDTO.getGenreName()))
            throw new GenreExistException("This Genre is already exist");

        genre.setGenreName(saveGenreDTO.getGenreName());
        genre.setDescription(saveGenreDTO.getDescription());

        genreRepository.save(genre);
    }

}
