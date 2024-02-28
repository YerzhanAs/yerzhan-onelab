package kz.library.system;


import kz.library.system.domain.entities.Book;
import kz.library.system.model.BookDTO;
import kz.library.system.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LibraryManagementSystemApplication implements CommandLineRunner {

    private static BookService bookService;

    @Autowired
    public LibraryManagementSystemApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //BookService
        // All books
        List<BookDTO> books = bookService.findAllBooks();
        for (BookDTO book : books) {
            System.out.println(book);
        }

        //Find book by id
        System.out.println("Book by Id");
        System.out.println(bookService.findBookById(1L));

        //Delete book by id
        System.out.println("Delete Book By Id");
        bookService.deleteBookById(3L);
        System.out.println(bookService.findBookById(3L));

        //Save book
        BookDTO newBook = BookDTO.builder()
                .title("Star wars: last war of emprie")
                .isbn("955-0-306-40615-7")
                .language("Spain")
                .build();

        System.out.println("Save book");
        bookService.saveBook(newBook);
        System.out.println(bookService.findBookById(3L));

        //Search book by isbn and language
        BookDTO book2 = bookService.findBookByLanguageAndIsbn(newBook.getLanguage(), newBook.getIsbn());
        System.out.println("Search book by isbn and language");
        System.out.println(book2);
    }
}
