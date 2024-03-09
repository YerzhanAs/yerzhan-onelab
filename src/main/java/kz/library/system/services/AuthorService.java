package kz.library.system.services;

import kz.library.system.models.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {

    public List<AuthorDTO> findAllAuthors();

    public AuthorDTO findAuthorById(Long id);

    public void deleteAuthorById(Long id);

    public void saveAuthor(AuthorDTO authorDTO);

    void updateAuthor(Long id, AuthorDTO updatedAuthorDTO);

}
