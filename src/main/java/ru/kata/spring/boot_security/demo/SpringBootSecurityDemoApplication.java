package ru.kata.spring.boot_security.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository,
	                                  RoleRepository roleRepository,
	                                  PasswordEncoder passwordEncoder) {
		return args -> {
			// Создаём роли, если их нет
			Role roleUser = roleRepository.findByName("ROLE_USER")
					.orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
			Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
					.orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

			// Создаём админа, если его нет
			if (userRepository.findByUsername("admin").isEmpty()) {
				User admin = new User("admin", passwordEncoder.encode("admin"), "admin@mail.com");
				admin.setRoles(Set.of(roleAdmin, roleUser));
				userRepository.save(admin);
			}

			// Создаём обычного пользователя
			if (userRepository.findByUsername("user").isEmpty()) {
				User user = new User("user", passwordEncoder.encode("user"), "user@mail.com");
				user.setRoles(Set.of(roleUser));
				userRepository.save(user);
			}
		};
	}
}