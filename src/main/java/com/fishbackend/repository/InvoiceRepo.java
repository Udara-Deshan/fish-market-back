package com.fishbackend.repository;

import com.fishbackend.entity.Invoice;
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
public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
    @Query(value = "select d.fish_name, d.fish_weight, d.price, i.total_price,i.id, i.number_of_days,  cr.room_number , ct.type_name  from description d left join cooling_room cr on d.cooling_room_id = cr.id left join cooling_room_type ct on cr.room_type_id = ct.id left join token t on d.token_id = t.id join invoice i on t.id = i.token_id where t.id = ?1  and i.status = 1", nativeQuery = true)
    List<Object[]> getDetailsByTokenId(Long tokenId);

    @Query(value = "select sum(invoice.total_price) from invoice where DATEDIFF(now() , bill_issue_date) <= 1 and status = 1", nativeQuery = true)
    double getDailyIncome();

    @Query(value = "select * from invoice where token_id = ?1 and status = 1", nativeQuery = true)
    Invoice getInvoiceByTokenId(Long tokenId);

    @Query(value = "select * from invoice where DATEDIFF(now() , bill_issue_date) <= 1 and status = 1", nativeQuery = true)
    List<Invoice> getDailyIncomeList();
}
