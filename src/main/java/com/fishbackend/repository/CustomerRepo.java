package com.fishbackend.repository;

import com.fishbackend.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    boolean existsByShopNameIsAndNicIsAndStatusIs(String shopName, String nic, int status);
    boolean existsByIdIs(Long id);
    Page<Customer> findAllByStatusIs(int status, Pageable page);

    Customer findByIdIs(Long CustomerId);

    @Query(value = "select t.id, t.create_date, t.customer_id, c.shop_name, t.who_issued, t.status from token t join customer c on t.customer_id = c.id where t.id = ?1", nativeQuery = true)
    List<Object[]> getAllCustomer(Long tokenId);

    @Query(value = "select * from customer where concat(id, nic, shop_name, shop_owner_name) like %?1% and status = 1", nativeQuery = true)
    List<Customer> searchCustomer(String value, Pageable page);
}
