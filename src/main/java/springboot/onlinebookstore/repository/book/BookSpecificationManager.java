package springboot.onlinebookstore.repository.book;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import springboot.onlinebookstore.exception.KeyNotSupportException;
import springboot.onlinebookstore.model.Book;
import springboot.onlinebookstore.repository.SpecificationManager;
import springboot.onlinebookstore.repository.SpecificationProvider;

@Component
@RequiredArgsConstructor
public class BookSpecificationManager implements SpecificationManager<Book> {
    private final Map<String, SpecificationProvider<Book>> providersMap;

    @Autowired
    public BookSpecificationManager(List<SpecificationProvider<Book>> specifications) {
        providersMap = specifications.stream()
                .collect(Collectors.toMap(SpecificationProvider::getFilterKey,
                        Function.identity()));
    }

    @Override
    public Specification<Book> get(String filterKey, String[] parameters) {
        if (!providersMap.containsKey(filterKey)) {
            throw new KeyNotSupportException("Key " + filterKey + " is not supported");
        }
        return providersMap.get(filterKey).getSpecification(parameters);
    }
}
