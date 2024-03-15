package kz.library.system;

import kz.library.system.kafka.producers.BookProducer;
import kz.library.system.services.AuthorService;
import kz.library.system.services.BookService;
import kz.library.system.services.GenreService;
import kz.library.system.services.PublisherService;
import kz.library.system.services.impl.AuthorServiceImpl;
import kz.library.system.services.impl.BookServiceImpl;
import kz.library.system.services.impl.GenreServiceImpl;
import kz.library.system.services.impl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LibraryManagementSystemApplication implements CommandLineRunner {


    private final BookProducer bookProducer;

    public LibraryManagementSystemApplication(BookProducer bookProducer) {
        this.bookProducer = bookProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Написал producer который в определенный промежуток времени
        // будет отправлять новые данные с скалады на consumer.
        // Consumer будет сохранять полученные данные в БД.
        // Промежуток времени указал 30 секунд
        bookProducer.sendBooksHourly();
    }
}
