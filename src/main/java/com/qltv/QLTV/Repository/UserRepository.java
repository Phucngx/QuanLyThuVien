package com.qltv.QLTV.Repository;

import com.qltv.QLTV.DTO.Response.UserReponse;
import com.qltv.QLTV.Entity.Permissions;
import com.qltv.QLTV.Entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUserName(String userName);
    boolean existsByUserName(String userName);

//    @Query(value = "select * from users where userName = ?1", countQuery = "select count(*) from users where userName = ?1", nativeQuery = true)
//    Page<Users> search(String userName, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE " +
            "LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.address) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Users> searchUsers(@Param("keyword") String keyword, Pageable pageable);
}
