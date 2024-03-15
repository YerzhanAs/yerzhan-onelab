package kz.library.system.kafka.consumers;

import java.util.List;
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

    public BookConsumer(BookService bookService) {
        this.bookService = bookService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void updateBooksInDB(List<BookDTO> books){
//        bookService.saveAllBook(books);
        log.info("Books received from warehouse -> {}", books);
    }
}
