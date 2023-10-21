package ru.gladun.historylearningplatform;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.gladun.historylearningplatform.entity.User;
import ru.gladun.historylearningplatform.repository.UserRepository;

import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository users;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User user1 = new User(1L, "user", this.passwordEncoder.encode("password"), this.passwordEncoder.encode("password"), null, null, null, null, Arrays.asList("ROLE_USER"), null, null);
        this.users.save(user1);

        User user2 = new User(2L, "admin", this.passwordEncoder.encode("password"), this.passwordEncoder.encode("password"), null, null, null, null, Arrays.asList("ROLE_USER", "ROLE_ADMIN"), null, null);
        this.users.save(user2);

        log.debug("printing all users...");
        this.users.findAll().forEach(v -> log.debug(" User :" + v.toString()));
    }
}
