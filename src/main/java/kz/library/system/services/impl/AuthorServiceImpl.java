package kz.library.system.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import kz.library.system.domains.entities.Author;
import kz.library.system.domains.repositories.AuthorRepository;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.mapper.AuthorMapper;
import kz.library.system.services.AuthorService;
import kz.library.system.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static kz.library.system.models.mapper.AuthorMapper.dtoToEntity;
import static kz.library.system.models.mapper.AuthorMapper.entityToDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDTO> findAllAuthors() {
        List<Author> authors = authorRepository.findAll();

        if(authors.isEmpty()){
            throw  new NotFoundException("No authors found");
        }

        return authors.stream()
                .map(AuthorMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO findAuthorById(Long id) {
        return entityToDto(authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found with ID: " + id)));
    }

    @Transactional
    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveAuthor(AuthorDTO authorDTO) {
        authorRepository.save(dtoToEntity(authorDTO));
    }

    @Transactional
    @Override
    public void updateAuthor(Long id, AuthorDTO updatedAuthorDTO) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found with ID: " + id));

        existingAuthor.setName(updatedAuthorDTO.getName());

        authorRepository.save(existingAuthor);
    }


}
