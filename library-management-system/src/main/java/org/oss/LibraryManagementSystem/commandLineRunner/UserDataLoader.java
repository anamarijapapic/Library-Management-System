package org.oss.LibraryManagementSystem.commandLineRunner;

import org.oss.LibraryManagementSystem.models.Role;
import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.repositories.RoleRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() throws ParseException {
        var roleMember = roleRepository.findByName("MEMBER");
        Set<Role> roles = new HashSet<>();
        roles.add(roleMember);

        var user1 = new User(
            "Anamarija",
            "Papić",
            "$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22",
            "anamarija@oss.org",
                new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse("1970/07/19").getTime()),
            "+4444444444",
            true,
            roles
        );

        var user2 = new User(
            "Ivana",
            "Mihanović",
            "$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22",
            "ivana@oss.org",
            new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse("2010/10/20").getTime()),
            "+5555555555",
            true,
            roles
        );

        var user3 = new User(
            "Petar",
            "Vidović",
            "$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22",
            "petar@oss.org",
            new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse("1950/05/19").getTime()),
            "+6666666666",
            true,
            roles
        );

        var users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        for (var user : users) {
            if (userRepository.findByEmail(user.getEmail()) == null) {
                System.out.println("Email " + user.getEmail() + " not found, creating user");
                userRepository.save(user);
            }
        }
    }

}
