package kz.library.system.services.impl;

import kz.library.system.domains.entities.Genre;
import kz.library.system.domains.repositories.GenreRepository;
import kz.library.system.models.dto.GenreDTO;
import kz.library.system.models.mapper.GenreMapper;
import kz.library.system.services.GenreService;
import kz.library.system.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import static kz.library.system.models.mapper.GenreMapper.dtoToEntity;
import static kz.library.system.models.mapper.GenreMapper.entityToDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<GenreDTO> findAllGenres(){
        List<Genre> genres = genreRepository.findAll();

        if(genres.isEmpty()){
            throw  new NotFoundException("No genres found");
        }

        return genres.stream()
                .map(GenreMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GenreDTO findGenreById(Long id) {
        return entityToDto(genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id)));
    }

    @Transactional
    @Override
    public void deleteGenreById(Long id){
        genreRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveGenre(GenreDTO genreDTO){
        genreRepository.save(dtoToEntity(genreDTO));
    }

}
