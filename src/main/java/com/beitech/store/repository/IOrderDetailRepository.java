package com.beitech.store.repository;

import com.beitech.store.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author James Martinez
 */
@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}