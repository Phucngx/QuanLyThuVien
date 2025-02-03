package com.qltv.QLTV.Repository;

import com.qltv.QLTV.Entity.Permissions;
import com.qltv.QLTV.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {
    boolean existsByRoleName(String permissionName);
    List<Roles> findAllByRoleNameIn(Set<String> roleName);
    Roles findByRoleName(String roleName);
}
