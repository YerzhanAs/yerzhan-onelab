package kz.library.system.kafka.consumers;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.library.system.domains.entities.Book;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.services.BookService;
import kz.library.system.services.impl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookConsumer {

    private final BookService bookService;

    private final ObjectMapper objectMapper;

    public BookConsumer(BookService bookService, ObjectMapper objectMapper) {
        this.bookService = bookService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void updateBooksInDB(String message) {
        try {
            List<BookDTO> books = objectMapper.readValue(message, new TypeReference<List<BookDTO>>(){});
            log.info("Books received from warehouse -> {}", books);
            bookService.saveAllBook(books);
        } catch (IOException e) {
            log.error("An error occurred while deserializing the book list", e);
        }
    }
}
