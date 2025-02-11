package com.qltv.QLTV.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Users {
    @Id
    @Column(name = "UserId")
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;

    @Column(name = "Username")
    String userName;

    @Column(name = "Password")
    String password;

    @Column(name = "Fullname")
    String fullName;

    @Column(name = "Phone")
    String phone;

    @Column(name = "Email")
    String email;

    @Column(name = "Dob")
    LocalDate dob;

    @Column(name = "Address")
    String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Users_Roles",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    Set<Roles> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<BorrowBooks> borrowBooks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Articles> articles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Comments> comments;
}
