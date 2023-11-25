package springboot.onlinebookstore.repository.book.specification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import springboot.onlinebookstore.model.Book;
import springboot.onlinebookstore.repository.SpecificationProvider;

@Slf4j
@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String FILTER_KEY = "authorIn";
    private static final String FIELD_NAME = "author";

    @Override
    public String getFilterKey() {
        log.info("AuthorSpecificationProvider getFilterKey() method called");
        return FILTER_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] parameters) {
        log.info("AuthorSpecificationProvider getSpecification() "
                + "method called with parameters: {}", parameters);
        return ((root, query, criteriaBuilder) -> root.get(FIELD_NAME)
                .in(parameters));
    }
}
