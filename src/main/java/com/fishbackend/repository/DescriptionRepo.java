package com.fishbackend.repository;

import com.fishbackend.entity.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Repository
public interface DescriptionRepo extends JpaRepository<Description, Long> {

    List<Description> findAllByStatusIsAndTokenIdIs(int status, Long tokenId);
}
