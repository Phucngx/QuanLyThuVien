package com.qltv.QLTV.Repository;

import com.qltv.QLTV.Entity.Permissions;
import com.qltv.QLTV.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
