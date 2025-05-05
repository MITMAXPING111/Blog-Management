package com.example.blog_management.util;

import com.example.blog_management.entities.User;
import com.example.blog_management.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
        @Autowired
        private UserRepo userRepo;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = userRepo.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

                Set<GrantedAuthority> authorities = new HashSet<>();

                authorities.addAll(user.getRoles().stream()
                                .flatMap(role -> role.getPermissions().stream())
                                .map(perm -> new SimpleGrantedAuthority(perm.getName().toString()))
                                .toList());

                return new org.springframework.security.core.userdetails.User(
                                user.getEmail(),
                                user.getPassword(),
                                authorities);
        }

}
