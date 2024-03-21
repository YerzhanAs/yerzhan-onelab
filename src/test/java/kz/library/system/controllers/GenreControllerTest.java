package kz.library.system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.library.system.models.dto.GenreCreateDTO;
import kz.library.system.models.dto.GenreDTO;
import kz.library.system.services.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllGenres_ReturnsListAndStatusOk() throws Exception {
        GenreDTO genreDTO = GenreDTO.builder()
                .id(1L)
                .genreName("Fantasy")
                .build();

        GenreDTO genreDTO2 = GenreDTO.builder()
                .id(2L)
                .genreName("Science Fiction")
                .build();

        List<GenreDTO> genres = List.of(genreDTO, genreDTO2);

        given(genreService.findAllGenres()).willReturn(genres);

        mockMvc.perform(get("/api/v1/genre/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(genres.size()))
                .andExpect(jsonPath("$[0].id").value(genres.get(0).getId()))
                .andExpect(jsonPath("$[0].genreName").value(genres.get(0).getGenreName()))
                .andExpect(jsonPath("$[1].id").value(genres.get(1).getId()))
                .andExpect(jsonPath("$[1].genreName").value(genres.get(1).getGenreName()));
    }


    @Test
    void getGenreById_ReturnsGenreAndStatusOk() throws Exception {
        Long genreId = 1L;
        GenreDTO genreDTO = GenreDTO.builder()
                .id(genreId)
                .genreName("Fantasy")
                .build();

        given(genreService.findGenreById(genreId)).willReturn(genreDTO);

        mockMvc.perform(get("/api/v1/genre/{id}", genreId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(genreId))
                .andExpect(jsonPath("$.genreName").value("Fantasy"));
    }

    @Test
    void saveGenre_ReturnsHttpStatusOk() throws Exception {
        GenreCreateDTO saveGenreDTO =  GenreCreateDTO.builder()
                .genreName("Fantasy")
                .build();
        doNothing().when(genreService).saveGenre(saveGenreDTO);

        mockMvc.perform(post("/api/v1/genre/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveGenreDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteGenre_ReturnsHttpStatusOk() throws Exception {
        Long genreId = 1L;
        doNothing().when(genreService).deleteGenreById(genreId);

        mockMvc.perform(delete("/api/v1/genre/delete/{id}", genreId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateGenre_ReturnsHttpStatusOk() throws Exception {
        Long genreId = 1L;
        GenreCreateDTO saveGenreDTO =  GenreCreateDTO.builder()
                .genreName("Fantasy")
                .build();
        doNothing().when(genreService).updateGenre(eq(genreId), any(GenreCreateDTO.class));

        mockMvc.perform(put("/api/v1/genre/{id}", genreId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveGenreDTO)))
                .andExpect(status().isOk());
    }



}
