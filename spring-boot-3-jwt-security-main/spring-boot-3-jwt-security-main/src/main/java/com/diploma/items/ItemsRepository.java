package com.diploma.items;

import com.diploma.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items, Integer> {

    List<Items> findByEmail(String email);

    @Query("SELECT p FROM Items p")
    Page<Items> pageItems(Pageable pageable);

    @Query("SELECT p FROM Items p WHERE p.name ILIKE %?1%"
            + " OR p.dormitory ILIKE %?1% "
            + " OR p.apartment ILIKE %?1%"
            + " OR p.email ILIKE %?1% "
            + " OR p.problem ILIKE %?1% "
            + " OR  CONCAT(p.phone, '') ILIKE %?1% "
            + " OR CONCAT(p.room, '') ILIKE %?1% ")
    Page<Items> search(String keyword, Pageable pageable);
}
