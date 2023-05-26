package com.diploma.form;

import com.diploma.items.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {

    @Query("SELECT p FROM Form p")
    Page<Form> pageForm(Pageable pageable);

    @Query("SELECT p FROM Form p WHERE p.name ILIKE %?1%"
            + " OR p.email ILIKE %?1% "
            + " OR p.phone ILIKE %?1%"
            + " OR p.address ILIKE %?1% "
            + " OR p.date ILIKE %?1% "
            + " OR p.reason ILIKE %?1% "
            + " OR  CONCAT(p.phone, '') ILIKE %?1% "
            + " OR CONCAT(p.room, '') ILIKE %?1% ")
    Page<Form> search(String keyword, Pageable pageable);
}
