package com.fishbackend.repository;

import com.fishbackend.entity.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
    Page<Token> findAllByStatusIs(int status, Pageable page);

    @Query(value = "select * from token where id = ?1", nativeQuery = true)
    Token getTokenById(Long id);

    @Query(value = "select t.id, t.create_date, c.shop_name from token t join customer c on t.customer_id = c.id where t.id = ?1 and t.status = 1",nativeQuery = true)
    List<Object[]> getDetailsByTokenId(Long tokenId);

    @Query(value = "select * from token where DATEDIFF(now() , create_date) >= ?1 and status = 1", nativeQuery = true)
    List<Token> getAllTokenByMoreThanDay(int day);

    @Query(value = "select count(id) from token where DATEDIFF(now() , create_date) >= ?1 and status = 1", nativeQuery = true)
    long getAllTokenByMoreThanDayCount(int day);


    @Query(value = "select * from token where DATEDIFF(now() , create_date) <= 1 and status = 1", nativeQuery = true)
    List<Token> getDailyStock();
    @Query(value = "select count(id) from token where DATEDIFF(now() , create_date) <= 1 and status = 1", nativeQuery = true)
    long getDailyStockCount();

    @Query(value = "select * from token where DATEDIFF(now() , create_date) <= 1 and status = 2", nativeQuery = true)
    List<Token> getDailyPaidStock();

    @Query(value = "select * from token where id like %?1% and status = 1", nativeQuery = true)
    Page<Token> searchStock(Long value, Pageable page);
}
