package com.diploma.items;

import com.diploma.news.News;
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
}
