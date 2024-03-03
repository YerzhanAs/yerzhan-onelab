package kz.library.system.services;

import kz.library.system.domains.repositories.AuthorRepository;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kz.library.system.models.mapper.AuthorMapper.dtoToEntity;
import static kz.library.system.models.mapper.AuthorMapper.entityToDto;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<AuthorDTO> findAllAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public AuthorDTO findAuthorById(Long id) {
        return entityToDto(authorRepository.findById(id));
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    public void saveAuthor(AuthorDTO authorDTO) {
        authorRepository.save(dtoToEntity(authorDTO));
    }


}
