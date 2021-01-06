package com.beitech.store.repository;

import com.beitech.store.domain.Customer;
import com.beitech.store.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author James Martinez
 */
@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customer_id AND o.creationDate BETWEEN :initial_date AND :final_date ")
    List<Order> findOrdersByDateRange(@Param("customer_id") int customer_id, @Param("initial_date") LocalDate initial_date, @Param("final_date") LocalDate final_date);

    List<Order> findOrderByCustomer(Customer customer);
}