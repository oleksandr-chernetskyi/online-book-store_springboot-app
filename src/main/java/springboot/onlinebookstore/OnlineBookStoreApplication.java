package springboot.onlinebookstore;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springboot.onlinebookstore.model.Book;
import springboot.onlinebookstore.service.BookService;

@SpringBootApplication
public class OnlineBookStoreApplication {
    private final BookService bookService;

    @Autowired
    public OnlineBookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book firstBook = new Book();
            firstBook.setTitle("Quentin Durward");
            firstBook.setAuthor("Sir Walter Scott");
            firstBook.setIsbn("978-0748605798");
            firstBook.setPrice(BigDecimal.valueOf(450));
            firstBook.setDescription("Historical adventure novel about a Scottish knight");
            firstBook.setCoverImage("Knight on horseback with drawn sword");

            Book secondBook = new Book();
            secondBook.setTitle("Captain Blood");
            secondBook.setAuthor("Rafael Sabatini");
            secondBook.setIsbn("978-5-6044983-0-9");
            secondBook.setPrice(BigDecimal.valueOf(400));
            secondBook.setDescription("adventure novel about pirates of the 'New World'");
            secondBook.setCoverImage("Elegantly dressed man on the deck of a pirate ship");

            bookService.save(firstBook);
            bookService.save(secondBook);
            System.out.println(bookService.findAll());
        };
    }
}
