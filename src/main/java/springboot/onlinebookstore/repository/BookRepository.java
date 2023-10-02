package springboot.onlinebookstore.repository;

import java.util.List;
import java.util.Optional;
import springboot.onlinebookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> getAll();

    Optional<Book> findById(Long id);
}
