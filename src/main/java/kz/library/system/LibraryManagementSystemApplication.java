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

    private final BookService bookServiceImpl;
    private final AuthorService authorServiceImpl;
    private final PublisherService publisherServiceImpl;
    private final GenreService genreServiceImpl;

    private final BookProducer bookProducer;

    public LibraryManagementSystemApplication(BookService bookServiceImpl, AuthorService authorServiceImpl, PublisherService publisherServiceImpl, GenreService genreServiceImpl, BookProducer bookProducer) {
        this.bookServiceImpl = bookServiceImpl;
        this.authorServiceImpl = authorServiceImpl;
        this.publisherServiceImpl = publisherServiceImpl;
        this.genreServiceImpl = genreServiceImpl;
        this.bookProducer = bookProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        bookProducer.sendBooksHourly();
    }
}
