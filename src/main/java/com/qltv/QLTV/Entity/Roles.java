package com.qltv.QLTV.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Roles {
    @Id
    @Column(name = "RoleId")
    @GeneratedValue(strategy = GenerationType.UUID)
    String roleId;

    @Column(name = "RoleName")
    String roleName;

    @ManyToMany(mappedBy = "roles")
    Set<Users> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Roles_Permissions",
            joinColumns = @JoinColumn(name = "RoleId"),
            inverseJoinColumns = @JoinColumn(name = "PermissionId")
    )
    Set<Permissions> permissions;
}
