package com.example.admingiadien.Service;

import com.example.admingiadien.DTO.UsersDTO;
import com.example.admingiadien.Entity.Roles;
import com.example.admingiadien.Entity.Users;
import com.example.admingiadien.Mapper.UserMapper;
import com.example.admingiadien.Repository.RoleRepository;
import com.example.admingiadien.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với tên đăng nhập: " + username);
        }
        // Tạo UserDetails từ thông tin của Users
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().getName()) // Sử dụng "authorities" thay vì "roles" nếu không có tiền tố "ROLE_"
                .build();
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UsersDTO saveUser(UsersDTO userDTO) {
        Users users = userMapper.toEntity(userDTO);
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        users.setCreatedAt(LocalDateTime.now());
        users.setUpdatedAt(LocalDateTime.now());
        Roles role = roleRepository.findByName("USER");
        if (role == null) {
            throw new RuntimeException("Không tìm thấy vai trò mặc định");
        }
        users.setRole(role);
        userRepository.save(users);
        return userMapper.toDto(users);
    }

    public UsersDTO saveAdmin(UsersDTO userDTO) {
        Users users = userMapper.toEntity(userDTO);
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        users.setCreatedAt(LocalDateTime.now());
        users.setUpdatedAt(LocalDateTime.now());
        Roles role = roleRepository.findByName("ADMIN");
        if (role == null) {
            throw new RuntimeException("Không tìm thấy vai trò ADMIN");
        }
        users.setRole(role);
        userRepository.save(users);
        return userMapper.toDto(users);
    }

    public String getUserRole(String username) {
        Users user = userRepository.findByUsername(username);
        if (user != null && user.getRole() != null) {
            return user.getRole().getName();
        }
        return null;
    }

    public boolean authenticate(String username, String password) {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public boolean isAdmin(String username) {
        Users user = userRepository.findByUsername(username);
        return user != null && user.getRole() != null && user.getRole().getName().equalsIgnoreCase("ADMIN");
    }
}
