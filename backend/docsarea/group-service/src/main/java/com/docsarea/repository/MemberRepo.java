package com.docsarea.repository;

import com.docsarea.enums.GroupRoles;
import com.docsarea.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member ,String> {
    Optional<Member> findByGroupIdAndUsername(String groupId , String username) ;
    boolean existsByUsernameAndGroupId(String username , String groupId) ;
    @Query("SELECT username FROM Member WHERE groupId = :groupId AND role = :role")
    List<String> findMembersByGroupIdAndRole(String groupId , GroupRoles role) ;
    Page<Member> findByGroupId(String groupId , Pageable page) ;
    List<Member> findByUsernameStartingWithAndGroupIdAndRole(String value, String groupId ,GroupRoles role , Pageable pageable) ;
    Page<Member> findByUsername(String username , Pageable pageable) ;
    Page<Member> findByUsernameAndRoleIsNot(String username , GroupRoles role , Pageable pageable) ;

}
