package kz.library.system.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.library.system.models.dto.GenreDTO;
import kz.library.system.models.dto.GenreCreateDTO;
import kz.library.system.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genre")
@Tag(name = "genres", description = "Genres Management APIs")
public class GenreController {

    private final GenreService genreService;

    private static final String SWAGGER_TAG = "genres";

    @Operation(summary = "Get All Genres",
            description = "Returns all genres",
            tags = {SWAGGER_TAG})
    @GetMapping("/all")
    public ResponseEntity<List<GenreDTO>> getAllGenres(){
        return ResponseEntity.ok(genreService.findAllGenres());
    }

    @Operation(summary = "Get Genre By Id",
            description = "Return genre by id",
            tags = {SWAGGER_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable("id") Long id){
        return ResponseEntity.ok(genreService.findGenreById(id));
    }

    @Operation(summary = "Save Genre",
            description = "Save Genre",
            tags = {SWAGGER_TAG})
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveGenre(@Valid @RequestBody GenreCreateDTO saveGenreDTO ) {
        genreService.saveGenre(saveGenreDTO);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Delete Genre by id",
            description = "Delete genre by id",
            tags = {SWAGGER_TAG})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteGenre(@PathVariable("id") Long id){
        genreService.deleteGenreById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Update Genre",
            description = "Update genre ",
            tags = {SWAGGER_TAG})
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateGenre(@Valid @RequestBody GenreCreateDTO saveGenreDTO,
                                                  @PathVariable("id") Long id){
        genreService.updateGenre(id, saveGenreDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
