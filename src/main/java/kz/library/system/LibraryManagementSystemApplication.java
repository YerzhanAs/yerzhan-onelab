package kz.library.system;

import kz.library.system.models.dto.AuthorDTO;
import kz.library.system.models.dto.BookDTO;
import kz.library.system.models.dto.GenreDTO;
import kz.library.system.models.dto.PublisherDTO;
import kz.library.system.models.mapper.AuthorMapper;
import kz.library.system.models.mapper.PublisherMapper;
import kz.library.system.services.impl.AuthorServiceImpl;
import kz.library.system.services.impl.BookServiceImpl;
import kz.library.system.services.impl.GenreServiceImpl;
import kz.library.system.services.impl.PublisherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
public class LibraryManagementSystemApplication implements CommandLineRunner {

    private final BookServiceImpl bookServiceImpl;
    private final AuthorServiceImpl authorServiceImpl;
    private final PublisherServiceImpl publisherServiceImpl;

    private final GenreServiceImpl genreServiceImpl;

    public LibraryManagementSystemApplication(BookServiceImpl bookServiceImpl, AuthorServiceImpl authorServiceImpl, PublisherServiceImpl publisherServiceImpl, GenreServiceImpl genreServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
        this.authorServiceImpl = authorServiceImpl;
        this.publisherServiceImpl = publisherServiceImpl;
        this.genreServiceImpl = genreServiceImpl;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        GenreDTO genre1 = GenreDTO.builder()
                .genreName("Fantasy")
                .description("Books about Fantasy")
                .build();

        GenreDTO genre2 = GenreDTO.builder()
                .genreName("Science")
                .description("Books about Science")
                .build();

        genreServiceImpl.saveGenre(genre1);
        genreServiceImpl.saveGenre(genre2);


        AuthorDTO author1 = AuthorDTO.builder()
                .name("Tolkin")
                .build();
        AuthorDTO author2 = AuthorDTO.builder()
                .name("Yerzhan")
                .build();

        authorServiceImpl.saveAuthor(author1);
        authorServiceImpl.saveAuthor(author2);
        log.info("Save authors");

        PublisherDTO publisher1 = PublisherDTO.builder()
                .publisherName("Moskva")
                .publisherYear(2017)
                .address("Red square 12")
                .build();
        PublisherDTO publisher2 = PublisherDTO.builder()
                .publisherName("Almaty")
                .publisherYear(2021)
                .address("Zibek Zholy 40")
                .build();

        publisherServiceImpl.savePublisher(publisher1);
        publisherServiceImpl.savePublisher(publisher2);
        log.info("Save publishers");

        List<PublisherDTO> publishers = publisherServiceImpl.findAllPublishers();
        log.info(" Get Publishers: {}", publishers);

        List<AuthorDTO> authors = authorServiceImpl.findAllAuthors();
        log.info(" Get Authors: {}", authors);

        List<GenreDTO> genres = genreServiceImpl.findAllGenres();
        log.info(" Get Genres: {}", genres);

        BookDTO newBook1 = BookDTO.builder()
                .title("Star wars: last war of emprie")
                .isbn("455-3-402-40612-7")
                .language("Spain")
                .author(AuthorMapper.dtoToEntity(authors.get(0)))
                .publisher(PublisherMapper.dtoToEntity(publishers.get(0)))
                .build();

        BookDTO newBook2 = BookDTO.builder()
                .title("The best practice of Java")
                .isbn("452-0-306-12215-7")
                .language("English")
                .author(AuthorMapper.dtoToEntity(authors.get(1)))
                .publisher(PublisherMapper.dtoToEntity(publishers.get(1)))
                .build();

        BookDTO newBook3 = BookDTO.builder()
                .title("Rich Dad")
                .isbn("777-0-406-40615-5")
                .language("English")
                .author(AuthorMapper.dtoToEntity(authors.get(0)))
                .publisher(PublisherMapper.dtoToEntity(publishers.get(0)))
                .build();

        bookServiceImpl.saveBook(newBook1);
        bookServiceImpl.saveBook(newBook2);
        bookServiceImpl.saveBook(newBook3);
        log.info("Save books");

        log.info("Find book by id");
        log.info("Book: {}", bookServiceImpl.findBookById(1L));

        log.info("All books");
        log.info("Books: {}", bookServiceImpl.findAllBooks());

        log.info("Delete book by id: 1 ");
        bookServiceImpl.deleteBookById(1L);

        log.info("Find book by isbn and language: {}",
                bookServiceImpl.findBookByIsbnAndLanguage("777-0-406-40615-5", "English"));

        log.info("Find count books by Author: {}", bookServiceImpl.countBooksByAuthor(authors.get(0)));

        log.info("Update Book Genre ");
        bookServiceImpl.updateBookGenre(2L, genres.get(0));


        log.info("Save book with exists isbn");
        BookDTO newBook4 = BookDTO.builder()
                .title("Rich Dad 2")
                .isbn("777-0-406-40615-5")
                .language("English")
                .author(AuthorMapper.dtoToEntity(authors.get(0)))
                .publisher(PublisherMapper.dtoToEntity(publishers.get(0)))
                .build();
        bookServiceImpl.saveBook(newBook4);


    }
}
