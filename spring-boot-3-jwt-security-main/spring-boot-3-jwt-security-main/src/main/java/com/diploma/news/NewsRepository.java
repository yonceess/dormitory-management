package com.diploma.news;

import com.diploma.items.Items;
import jdk.jfr.Registered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    @Query("SELECT p FROM News p WHERE p.fileName NOT LIKE '%statement%'")
    Page<News> pageNews(Pageable pageable);
}
