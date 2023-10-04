package springboot.onlinebookstore.repository.book.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import springboot.onlinebookstore.model.Book;
import springboot.onlinebookstore.repository.SpecificationProvider;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String FILTER_KEY = "titleContains";
    private static final String FIELD_NAME = "title";

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] parameters) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get(FIELD_NAME),
                    "%" + parameters[0] + "%");
            return criteriaBuilder.and(predicate);
        });
    }
}
