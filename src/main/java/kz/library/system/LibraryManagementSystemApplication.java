package kz.library.system;


import kz.library.system.domains.entities.Publisher;
import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.dto.PublisherDTO;
import kz.library.system.models.mapper.AuthorMapper;
import kz.library.system.models.mapper.PublisherMapper;
import kz.library.system.services.AuthorService;
import kz.library.system.services.BookService;
import kz.library.system.services.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LibraryManagementSystemApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LibraryManagementSystemApplication.class);

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public LibraryManagementSystemApplication(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        AuthorDTO author1 = AuthorDTO.builder().name("Tolkin").build();
        AuthorDTO author2 = AuthorDTO.builder().name("Yerzhan").build();

        authorService.saveAuthor(author1);
        authorService.saveAuthor(author2);

        logger.info("Save authors");

        PublisherDTO publisher1 = PublisherDTO.builder().publisherName("Moskva").publisherYear(2017).build();
        PublisherDTO publisher2 = PublisherDTO.builder().publisherName("Almaty").publisherYear(2021).build();

        publisherService.savePublisher(publisher1);
        publisherService.savePublisher(publisher2);

        logger.info("Save publishers");

        List<PublisherDTO> publishers = publisherService.findAllPublishers();

        logger.info(" Get Publishers: {}", publishers);

        List<AuthorDTO> authors = authorService.findAllAuthors();
        logger.info(" Get Authors: {}", authors);

        BookDTO newBook1 = BookDTO.builder()
                .title("Star wars: last war of emprie")
                .isbn("955-0-306-40615-7")
                .language("Spain")
                .author(AuthorMapper.dtoToEntity(authors.get(0)))
                .publisher(PublisherMapper.dtoToEntity(publishers.get(0)))
                .build();


        BookDTO newBook2 = BookDTO.builder()
                .title("The best practice of Java")
                .isbn("744-0-306-40425-7")
                .language("English")
                .author(AuthorMapper.dtoToEntity(authors.get(1)))
                .publisher(PublisherMapper.dtoToEntity(publishers.get(1)))
                .build();

        BookDTO newBook3 = BookDTO.builder()
                .title("Rich Dad")
                .isbn("244-0-306-30425-7")
                .language("English")
                .author(AuthorMapper.dtoToEntity(authors.get(0)))
                .publisher(PublisherMapper.dtoToEntity(publishers.get(0)))
                .build();

        logger.info("Save books");
        bookService.saveBook(newBook1);
        bookService.saveBook(newBook2);
        bookService.saveBook(newBook3);

        logger.info("Find book by id");
        logger.info("Book: {}", bookService.findBookById(1L));

        logger.info("All books");
        logger.info("Books: {}", bookService.findAllBooks());

        logger.info("Search book by isbn and language");
        BookDTO book2 = bookService.findBookByLanguageAndIsbn(newBook1.getLanguage(), newBook1.getIsbn());
        logger.info("Found book: {}", book2);

        logger.info("Delete book by id: 1 ");
        bookService.deleteBookById(1L);

        authors.get(0).setId(null);

        BookDTO newBook4 = BookDTO.builder()
                .title("Rich Dad 2")
                .isbn("442-0-306-30425-7")
                .language("English")
                .author(AuthorMapper.dtoToEntity(authors.get(0)))
                .publisher(PublisherMapper.dtoToEntity(publishers.get(0)))
                .build();

        bookService.saveBook(newBook4);


    }
}
