package com.auth.server.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Map<String, UserDetails> useres;

    static {
        useres = new HashMap<>();
        useres.put("admin", new User("admin", "123", new ArrayList<>()));
        useres.put("user1", new User("user1", "123", new ArrayList<>()));
        useres.put("user2", new User("user2", "123", new ArrayList<>()));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return useres.get(username);
    }
}
