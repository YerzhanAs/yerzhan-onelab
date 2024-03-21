package kz.library.system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.library.system.models.dto.PublisherCreateDTO;
import kz.library.system.models.dto.PublisherDTO;
import kz.library.system.services.PublisherService;
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

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PublisherService publisherService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void getAllPublishers_ReturnsListAndStatusOk() throws Exception {
//        PublisherDTO publisherDTO = PublisherDTO.builder()
//                .id(1L)
//                .publisherName("Almaty")
//                .build();
//        PublisherDTO publisherDTO2 = PublisherDTO.builder()
//                .id(2L)
//                .publisherName("Mockva")
//                .build();
//        List<PublisherDTO> publishers = List.of(publisherDTO, publisherDTO2);
//
//        given(publisherService.findAllPublishers()).willReturn(publishers);
//
//        mockMvc.perform(get("/api/v1/publisher/all")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.size()").value(publishers.size()))
//                .andExpect(jsonPath("$[0].id").value(publishers.get(0).getId()))
//                .andExpect(jsonPath("$[0].publisherName").value(publishers.get(0).getPublisherName()))
//                .andExpect(jsonPath("$[1].id").value(publishers.get(1).getId()))
//                .andExpect(jsonPath("$[1].publisherName").value(publishers.get(1).getPublisherName()));
//    }
//
//
//    @Test
//    public void getPublisherById_ReturnsPublisherAndStatusOk() throws Exception {
//        Long publisherId = 1L;
//        PublisherDTO publisherDTO = PublisherDTO.builder()
//                .id(publisherId)
//                .publisherName("Almaty")
//                .build();
//
//        given(publisherService.findPublisherById(publisherId)).willReturn(publisherDTO);
//
//        mockMvc.perform(get("/api/v1/publisher/{id}", publisherId)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(publisherId))
//                .andExpect(jsonPath("$.publisherName").value("Almaty"));
//    }
//
//    @Test
//    public void savePublisher_ReturnsHttpStatusOk() throws Exception {
//        PublisherCreateDTO publisherCreateDTO = PublisherCreateDTO.builder()
//                .publisherName("Almaty")
//                .publisherYear(2000)
//                .address("Satbayeva 86")
//                .build();
//        doNothing().when(publisherService).savePublisher(publisherCreateDTO);
//
//        mockMvc.perform(post("/api/v1/publisher/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(publisherCreateDTO)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void deletePublisher_ReturnsHttpStatusOk() throws Exception {
//        Long publisherId = 1L;
//        doNothing().when(publisherService).deletePublisherById(publisherId);
//
//        mockMvc.perform(delete("/api/v1/publisher/delete/{id}", publisherId)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void updatePublisher_ReturnsHttpStatusOk() throws Exception {
//        Long publisherId = 1L;
//        PublisherCreateDTO publisherCreateDTO = PublisherCreateDTO.builder()
//                .publisherName("Almaty")
//                .publisherYear(2000)
//                .address("Satbayeva 86")
//                .build();
//        doNothing().when(publisherService).updatePublisher(eq(publisherId), any(PublisherCreateDTO.class));
//
//        mockMvc.perform(put("/api/v1/publisher/{id}", publisherId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(publisherCreateDTO)))
//                .andExpect(status().isOk());
//    }
}
