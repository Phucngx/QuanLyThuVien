package com.qltv.QLTV.Repository;

import com.qltv.QLTV.Entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, String> {
    Optional<Permissions> findByPermissionName(String permissionName);
    boolean existsByPermissionName(String permissionName);
    List<Permissions> findAllByPermissionNameIn(Set<String> permissionName);
}
