package com.docsarea.repository;

import com.docsarea.model.InvitationLink;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.util.annotation.NonNull;

import java.util.List;
import java.util.Optional;

public interface InvitationLinkRepo extends JpaRepository<InvitationLink , String> {
    @NonNull
    Optional<InvitationLink> findById(@Nullable String id) ;
    boolean existsById(@Nullable String id) ;
    Page<InvitationLink> findByGroupIdAndCreatedByOrderByCreatedAtDesc(String groupId , String createdBy , Pageable page) ;
    Page<InvitationLink> findByGroupIdOrderByCreatedAtDesc(String groupId , Pageable page) ;
}
