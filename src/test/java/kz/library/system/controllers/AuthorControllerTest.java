package kz.library.system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.library.system.models.dto.AuthorCreateDTO;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllAuthors_ReturnsAllAuthors() throws Exception {
        AuthorDTO authorDTO1 = AuthorDTO.builder()
                .id(1L)
                .name("Yerzhan")
                .build();
        AuthorDTO authorDTO2 = AuthorDTO.builder()
                .id(2L)
                .name("Yeraka")
                .build();
        when(authorService.findAllAuthors()).thenReturn(List.of(authorDTO1, authorDTO2));

        mockMvc.perform(get("/api/v1/author/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(authorService, times(1)).findAllAuthors();
    }

    @Test
    void getAuthorById_ReturnsAuthor() throws Exception {
        Long authorId = 1L;
        AuthorDTO authorDTO = AuthorDTO.builder()
                .id(authorId)
                .name("Yerzhan")
                .build();

        given(authorService.findAuthorById(authorId)).willReturn(authorDTO);

        mockMvc.perform(get("/api/v1/author/{id}", authorId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(authorId))
                .andExpect(jsonPath("$.name").value("Yerzhan"));
    }


    @Test
    void saveAuthor_ReturnsHttpStatusOk() throws Exception {
        AuthorCreateDTO authorCreateDTO = AuthorCreateDTO.builder()
                .name("Yerzhan")
                .build();

        doNothing().when(authorService).saveAuthor(authorCreateDTO);

        mockMvc.perform(post("/api/v1/author/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorCreateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAuthor_ReturnsHttpStatusOk() throws Exception {
        Long authorId = 1L;
        doNothing().when(authorService).deleteAuthorById(authorId);

        mockMvc.perform(delete("/api/v1/author/delete/{id}", authorId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAuthor_ReturnsHttpStatusOk() throws Exception {
        Long authorId = 1L;
        AuthorCreateDTO authorCreateDTO = AuthorCreateDTO.builder()
                .name("Yerzhan")
                .build();
        doNothing().when(authorService).updateAuthor(eq(authorId), any(AuthorCreateDTO.class));

        mockMvc.perform(patch("/api/v1/author/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorCreateDTO)))
                .andExpect(status().isOk());
    }


}
