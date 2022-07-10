package com.fishbackend.repository;

import com.fishbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query(value = "select * from user where user_name = ?1 and status = 1", nativeQuery = true)
    User getUserByUsername(String username);
    boolean existsByUsernameIsAndStatusIs(String username, int status);
    boolean existsByIdIs(Long userId);
    Page<User> findAllByStatusIs(int status, Pageable page);
}
