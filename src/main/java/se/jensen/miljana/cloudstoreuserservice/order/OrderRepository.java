package se.jensen.miljana.cloudstoreuserservice.order;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.miljana.cloudstoreuserservice.order.model.CustomerOrder;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {


    List<CustomerOrder> findByUserEmail(String userEmail);

}
