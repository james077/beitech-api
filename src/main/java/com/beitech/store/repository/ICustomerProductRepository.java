package com.beitech.store.repository;

import com.beitech.store.domain.OrderDetail;
import com.beitech.store.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author James Martinez
 */
@Repository
public interface ICustomerProductRepository extends JpaRepository<OrderDetail, Integer> {

    @Query("SELECT cp.product FROM CustomerProduct cp WHERE cp.customer.id = :customer_id  ")
    List<Product> findProductsByCustomer(@Param("customer_id") int customer_id);

}