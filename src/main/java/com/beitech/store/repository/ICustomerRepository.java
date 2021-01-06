package com.beitech.store.repository;

import com.beitech.store.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author James Martinez
 */
@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

}