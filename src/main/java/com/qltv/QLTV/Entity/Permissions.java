package com.qltv.QLTV.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "Permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Permissions {
        @Id
        @Column(name = "PermissionId")
        @GeneratedValue(strategy = GenerationType.UUID)
        String permissionId;

        @Column(name = "PermissionName")
        String permissionName;

        @ManyToMany(mappedBy = "permissions")
        Set<Roles> roles;
}
