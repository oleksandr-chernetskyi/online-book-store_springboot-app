package springboot.onlinebookstore.repository.order;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springboot.onlinebookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("FROM Order o LEFT JOIN FETCH o.orderItems "
            + "LEFT JOIN FETCH o.user u WHERE u.id = :userId ")
    List<Order> findAllOrdersByUserId(Long userId);

    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findById(Long id);
}
