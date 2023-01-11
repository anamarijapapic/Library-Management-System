package org.oss.LibraryManagementSystem.commandLineRunner;

import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.models.enums.Role;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Component
public class MyRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);

    private final UserRepository userRepo;

    public MyRunner(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public void run(String...args) throws Exception {

        var passwordEncoder = new BCryptPasswordEncoder();
        var encodedPassword = passwordEncoder.encode("admin");

        var sdf = new SimpleDateFormat("yyyy/MM/dd");
        var date = sdf.parse("1998/7/11");
        var timestamp = new Timestamp(date.getTime());

        var admin = new User(
                "admin",
                "admin",
                encodedPassword,
                "admin@oss.org",
                timestamp,
                Role.ADMIN.label,
                "+123456789"
        );

        if (userRepo.findByEmail("admin@oss.org") == null) {
            System.out.println("Email admin@oss.org not found, creating user");
            userRepo.save(admin);
        }

        var root = new User(
                "root",
                "root",
                encodedPassword,
                "root@oss.org",
                timestamp,
                Role.ADMIN.label,
                "+987654321"
        );

        if (userRepo.findByEmail("root@oss.org") == null) {
            System.out.println("Email root@oss.org not found, creating user");
            userRepo.save(root);
        }
    }

}
