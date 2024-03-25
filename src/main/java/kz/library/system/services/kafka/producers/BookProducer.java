package kz.library.system.services.kafka.producers;

import kz.library.system.models.dto.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class BookProducer {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private KafkaTemplate<String, List<BookDTO>> kafkaTemplate;

    public BookProducer(KafkaTemplate<String, List<BookDTO>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 3600000)
    public void sendBooksHourly() {
        List<BookDTO> books = generateBooks();
        kafkaTemplate.send(topicName, books);
        log.info("Books sent: {}", books);
    }

    public void sendBooks(List<BookDTO> data){
        log.info("Sending list of books -> {}", data);
        kafkaTemplate.send(topicName, data);
    }

    private List<BookDTO> generateBooks() {
        BookDTO book1 = BookDTO.builder()
                .title("The best practice of Java")
                .isbn("978-3-16-148410-0")
                .language("English")
                .build();

        BookDTO book2 = BookDTO.builder()
                .title("Creating minecraft with java")
                .isbn("978-3-14-158410-0")
                .language("English")
                .build();
        return Arrays.asList(book1, book2);
    }
}
