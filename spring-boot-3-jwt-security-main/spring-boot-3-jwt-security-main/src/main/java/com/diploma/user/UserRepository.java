package com.diploma.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  Optional<User> findByIdCard(String idCard);


  @Query("SELECT p FROM User p WHERE p.role != 'ROLE_ADMIN'")
    Page<User> pageUser(Pageable pageable);

  @Query("SELECT p FROM User p WHERE p.firstname ILIKE %?1% AND p.role != 'ROLE_ADMIN'"
          + " OR p.lastname ILIKE %?1% AND p.role != 'ROLE_ADMIN'"
          + " OR p.email ILIKE %?1% AND p.role != 'ROLE_ADMIN'"
          + " OR p.apartment ILIKE %?1% AND p.role != 'ROLE_ADMIN'"
          + " OR p.dormitory ILIKE %?1% AND p.role != 'ROLE_ADMIN'"
          + " OR CONCAT(p.idCard, '') ILIKE %?1% AND p.role != 'ROLE_ADMIN'")
  Page<User> search(String keyword, Pageable pageable);


  @Query("SELECT p FROM User p WHERE p.dormitory ILIKE %?1%")
  Page<User> searchDorm(String keyword, Pageable pageable);


}
