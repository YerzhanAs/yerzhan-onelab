package kz.library.system.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.library.system.models.dto.BookCreateDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.request.SearchBookRequest;
import kz.library.system.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
@Tag(name = "books", description = "Books Management APIs")
public class BookController {

    private final BookService bookService;

    private static final String SWAGGER_TAG = "books";

    @Operation(summary = "Get All Books",
            description = "Returns all books",
            tags = {SWAGGER_TAG})
    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @Operation(summary = "Get Book By Id",
            description = "Return Book by id",
            tags = {SWAGGER_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id){
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @Operation(summary = "Save Book",
            description = "Save book",
            tags = {SWAGGER_TAG})
    @PutMapping("/save")
    public ResponseEntity<HttpStatus> saveBook(@Valid @RequestBody BookCreateDTO bookCreateDTO) {
        bookService.saveBook(bookCreateDTO);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Delete Book by id",
            description = "Delete book by id",
            tags = {SWAGGER_TAG})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") Long id){
        bookService.deleteBookById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Update Book",
            description = "Update book",
            tags = {SWAGGER_TAG})
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBook(@Valid @RequestBody BookCreateDTO bookCreateDTO,
                                                   @PathVariable("id") Long id){
        bookService.updateBook(id, bookCreateDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Get Book By Author Name",
            description = "Return book by author name",
            tags = {SWAGGER_TAG})
    @GetMapping("/bookbyauthor")
    public ResponseEntity<List<BookDTO>> getBookByAuthorName(@RequestParam("authorName") String authorName){
        return ResponseEntity.ok(bookService.findBooksByAuthor(authorName));
    }

    @Operation(summary = "Get Book By Genre Name",
            description = "Return book by genre name",
            tags = {SWAGGER_TAG})
    @GetMapping("/bookbygenre")
    public ResponseEntity<List<BookDTO>> getBookByGenreName(@RequestParam("genreName") String genreName){
        return ResponseEntity.ok(bookService.findBooksByGenreName(genreName));
    }

    @Operation(summary = "Search Books",
            description = "Search books",
            tags = {SWAGGER_TAG})
    @PostMapping("/search")
    public ResponseEntity<Page<BookDTO>> searchKnoxRecords(
            @RequestBody SearchBookRequest request,
            Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.search(request, pageable));
    }
}
