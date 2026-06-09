package com.ecommerce.config;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.User;
import com.ecommerce.enums.UserRole;
import com.ecommerce.enums.UserStatus;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("User")
                    .email("admin@ecommerce.com")
                    .mobileNumber("9999999999")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .status(UserStatus.ACTIVE)
                    .build();
            userRepository.save(admin);

            User customer = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john@example.com")
                    .mobileNumber("8888888888")
                    .password(passwordEncoder.encode("customer123"))
                    .role(UserRole.CUSTOMER)
                    .status(UserStatus.ACTIVE)
                    .build();
            userRepository.save(customer);

            log.info("Default users seeded successfully");
        }

        if (categoryRepository.count() == 0) {
            categoryRepository.save(Category.builder().name("Electronics").description("Electronic devices and accessories").build());
            categoryRepository.save(Category.builder().name("Clothing").description("Apparel and fashion items").build());
            categoryRepository.save(Category.builder().name("Books").description("Books and publications").build());
            categoryRepository.save(Category.builder().name("Home & Kitchen").description("Home appliances and kitchen items").build());
            categoryRepository.save(Category.builder().name("Sports").description("Sports equipment and gear").build());
            log.info("Default categories seeded successfully");
        }
    }
}
