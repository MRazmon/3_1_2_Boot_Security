package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    void save(User user);
    void saveUser(User user, Set<Role> roles);
    void deleteById(Long id);
}