package com.docsarea.repository;

import com.docsarea.model.Group;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.util.annotation.NonNull;

import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<Group, String> {

    @NonNull
    Optional<Group> findById(@Nullable String id) ;
    Page <Group> findByOwner(String owner , Pageable pageable) ;
}
