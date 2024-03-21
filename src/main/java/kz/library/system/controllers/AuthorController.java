package kz.library.system.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.library.system.models.dto.AuthorCreateDTO;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "authors", description = "Authors Management APIs")
public class AuthorController {

    private final AuthorService authorService;

    private static final String SWAGGER_TAG = "authors";

    @Operation(summary = "Get All Authors",
            description = "Returns all authors",
            tags = {SWAGGER_TAG})
    @GetMapping("/all")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors(){
        return ResponseEntity.ok(authorService.findAllAuthors());
    }

    @Operation(summary = "Get Author By Id",
            description = "Return author by id",
            tags = {SWAGGER_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id){
        return ResponseEntity.ok(authorService.findAuthorById(id));
    }

    @Operation(summary = "Save Author",
            description = "Save author",
            tags = {SWAGGER_TAG})
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveAuthor(@Valid @RequestBody AuthorCreateDTO authorCreateDTO) {
        authorService.saveAuthor(authorCreateDTO);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Delete Author by id",
            description = "Delete author by id",
            tags = {SWAGGER_TAG})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Update Author",
            description = "Update author",
            tags = {SWAGGER_TAG})
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAuthor(@Valid @RequestBody AuthorCreateDTO authorCreateDTO,
                                                      @PathVariable("id") Long id){
        authorService.updateAuthor(id, authorCreateDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
