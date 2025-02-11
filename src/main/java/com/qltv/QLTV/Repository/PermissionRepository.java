package com.qltv.QLTV.Repository;

import com.qltv.QLTV.Entity.Permissions;
import com.qltv.QLTV.Entity.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, String> {
    Optional<Permissions> findByPermissionName(String permissionName);
    boolean existsByPermissionName(String permissionName);
    List<Permissions> findAllByPermissionNameIn(Set<String> permissionName);

    @Query(value = "select * from permissions where permission_name = ?1", countQuery = "select count(*) from permissions where permission_name = ?1", nativeQuery = true)
    Page<Permissions> search(String permissionName, Pageable pageable);
}
