package com.qltv.QLTV.Service;

import com.qltv.QLTV.DTO.Request.User.CreateUserRequest;
import com.qltv.QLTV.DTO.Request.User.UpdateUserRequest;
import com.qltv.QLTV.DTO.Response.UserReponse;
import com.qltv.QLTV.Entity.Roles;
import com.qltv.QLTV.Entity.Users;
import com.qltv.QLTV.Exception.ApplicationException;
import com.qltv.QLTV.Exception.ErrorCode;
import com.qltv.QLTV.Mapper.UserMapper;
import com.qltv.QLTV.Repository.RoleRepository;
import com.qltv.QLTV.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UsersService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public UserReponse createUser(CreateUserRequest request){
        if(userRepository.existsByUserName(request.getUserName())){
            throw new ApplicationException(ErrorCode.NAME_EXISTS);
        }
        Users users = userMapper.toUser(request);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Set<Roles> roles = new HashSet<>();
        Roles role = roleRepository.findByRoleName("USER");
        roles.add(role);
        users.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(users));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserReponse updateUser(String id, UpdateUserRequest request){
        Users user = userRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Roles> roles = roleRepository.findAllByRoleNameIn(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserReponse> getAllUser(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return userRepository.findAll(pageable).map(userMapper::toUserResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserReponse getDetailUser(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND)));
    }

    @PostAuthorize("returnObject.userName == authentication.name")
    public UserReponse getMyInfo(){
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        return userMapper.toUserResponse(userRepository.findByUserName(username).orElseThrow(() -> new ApplicationException(ErrorCode.USERNAME_NOT_EXISTS)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserReponse> search(String keywords, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return userRepository.searchUsers(keywords, pageable).map(userMapper::toUserResponse);
    }

}
