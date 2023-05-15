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
}
