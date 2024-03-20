package kz.library.system.services;

import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.dto.AuthorCreateDTO;

import java.util.List;

public interface AuthorService {

    public List<AuthorDTO> findAllAuthors();

    public AuthorDTO findAuthorById(Long id);

    public void deleteAuthorById(Long id);

    public void saveAuthor(AuthorCreateDTO authorCreateDTO);

    void updateAuthor(Long id, AuthorCreateDTO authorCreateDTO);

}
