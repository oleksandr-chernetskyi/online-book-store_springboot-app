package springboot.onlinebookstore.repository.book.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import springboot.onlinebookstore.model.Book;
import springboot.onlinebookstore.repository.SpecificationProvider;

@Slf4j
@Component
public class HighestPriceSpecificationProvider implements SpecificationProvider<Book> {
    private static final String FILTER_KEY = "maxPrice";
    private static final String FIELD_NAME = "price";

    @Override
    public String getFilterKey() {
        log.info("HighestPriceSpecificationProvider getFilterKey() method called");
        return FILTER_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] parameters) {
        log.info("HighestPriceSpecificationProvider getSpecification() "
                + "method called with parameters: {}", parameters);
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder
                    .lessThanOrEqualTo(root.get(FIELD_NAME), parameters[0]);
            return criteriaBuilder.and(predicate);
        });
    }
}
