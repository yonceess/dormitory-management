package com.diploma.news;

import com.diploma.items.Items;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface NewsRepository  extends JpaRepository<News, Integer> {

    News findByName(String name);
    Optional<News> findByFileName(String fileName);


    @Query("SELECT p FROM News p")
    Page<News> pageNews(Pageable pageable);

    @Query(value = "SELECT nextval('id')", nativeQuery =
            true)
    Integer getNextSeriesId();

}
