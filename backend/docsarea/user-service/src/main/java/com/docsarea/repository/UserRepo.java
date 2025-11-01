package com.docsarea.repository;

import com.docsarea.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    boolean existsByUsername (String username ) ;
    boolean existsByEmail (String email ) ;
    Optional<User> findByUsername(String username) ;
    List<User> findByUsernameStartingWith(String value, Pageable pageable) ;
    void deleteByUsername(String username) ;
}
