package kz.library.system.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.library.system.models.dto.PublisherCreateDTO;
import kz.library.system.models.dto.PublisherDTO;
import kz.library.system.services.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publisher")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "publishers", description = "Publishers Management APIs")
public class PublisherController {

    private final PublisherService publisherService;

    private static final String SWAGGER_TAG = "publishers";

    @Operation(summary = "Get All Publishers",
            description = "Returns all publishers",
            tags = {SWAGGER_TAG})
    @GetMapping("/all")
    public ResponseEntity<List<PublisherDTO>> getAllPublishers(){
        return ResponseEntity.ok(publisherService.findAllPublishers());
    }

    @Operation(summary = "Get Publisher By Id",
            description = "Return publisher by id",
            tags = {SWAGGER_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable("id") Long id){
        return ResponseEntity.ok(publisherService.findPublisherById(id));
    }

    @Operation(summary = "Save Publisher",
            description = "Save publisher",
            tags = {SWAGGER_TAG})
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> savePublisher(@Valid @RequestBody PublisherCreateDTO publisherCreateDTO) {
        publisherService.savePublisher(publisherCreateDTO);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Delete Publisher by id",
            description = "Delete publisher by id",
            tags = {SWAGGER_TAG})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePublisher(@PathVariable("id") Long id){
        publisherService.deletePublisherById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Update Publisher",
            description = "Update publisher",
            tags = {SWAGGER_TAG})
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updatePublisher(@Valid @RequestBody PublisherCreateDTO publisherCreateDTO,
                                                  @PathVariable("id") Long id){
        publisherService.updatePublisher(id, publisherCreateDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
