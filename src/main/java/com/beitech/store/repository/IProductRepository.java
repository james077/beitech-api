package com.beitech.store.repository;


import com.beitech.store.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author James Martinez
 */
@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

}