package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "edit_user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user,
                          @RequestParam(value = "roleIds") List<Long> roleIds) {
        Set<Role> roles = roleService.findByIds(roleIds);
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        return "edit_user";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute User user,
                           @RequestParam(value = "roleIds") List<Long> roleIds) {
        Set<Role> roles = roleService.findByIds(roleIds);
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}