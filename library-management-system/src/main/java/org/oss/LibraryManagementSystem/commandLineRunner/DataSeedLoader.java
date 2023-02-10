package org.oss.LibraryManagementSystem.commandLineRunner;

import com.github.javafaker.Faker;
import org.oss.LibraryManagementSystem.models.*;
import org.oss.LibraryManagementSystem.models.enums.Status;
import org.oss.LibraryManagementSystem.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeedLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    private final WorkRepository workRepository;

    private final BookRepository bookRepository;

    public DataSeedLoader(UserRepository userRepository,
                          RoleRepository roleRepository,
                          AuthorRepository authorRepository,
                          CategoryRepository categoryRepository,
                          WorkRepository workRepository,
                          BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.workRepository = workRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
        loadAuthorData();
        loadCategoryData();
        loadWorkData();
        loadBookData();
    }

    private void loadUserData() throws ParseException {
        var roleMember = roleRepository.findByName("MEMBER");
        Set<Role> roles = new HashSet<>();
        roles.add(roleMember);

        var user1 = new User("Anamarija", "Papić", "$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22", "anamarija@oss.org", new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse("1970/07/19").getTime()), "+4444444444", true, roles);

        var user2 = new User("Ivana", "Mihanović", "$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22", "ivana@oss.org", new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse("2010/10/20").getTime()), "+5555555555", true, roles);

        var user3 = new User("Petar", "Vidović", "$2a$12$3tZr1OLzCU1mojVnwAtBlOOy9WkJNM2Yifbr3iAxVQBFXlqIAlw22", "petar@oss.org", new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse("1950/05/19").getTime()), "+6666666666", true, roles);

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

    private void loadAuthorData() {

        if (authorRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                var faker = new Faker();

                var firstName = faker.name().firstName();
                var lastName = faker.name().lastName();

                var author = new Author(firstName, lastName);
                authorRepository.save(author);
            }
            System.out.println("Seeded author table successfully");
        }
    }

    private void loadCategoryData() {

        if (categoryRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                var faker = new Faker();

                var name = faker.book().genre();

                var category = new Category(name);
                categoryRepository.save(category);
            }
            System.out.println("Seeded category table successfully");
        }
    }

    private void loadWorkData() {

        if (workRepository.count() <= 5) {
            for (int i = 0; i < 20; i++) {
                var faker = new Faker();

                var title = faker.book().title();
                var description = faker.lorem().sentence();

                var author = authorRepository.getOneRandom();
                Set<Author> authors = new HashSet<>();
                authors.add(author);

                var category = categoryRepository.getOneRandom();
                Set<Category> categories = new HashSet<>();
                categories.add(category);

                var work = new Work(title, description, authors, categories);
                workRepository.save(work);
            }
            System.out.println("Seeded work table successfully");
        }
    }

    private void loadBookData() {

        if (bookRepository.count() <= 5) {
            for (int i = 0; i < 30; i++) {
                var faker = new Faker();

                var work = workRepository.getOneRandom();
                var publisherName = faker.book().publisher();
                var yearOfPublishing = new Timestamp(System.currentTimeMillis());
                var isbn = faker.code().isbn13(true);
                var bookStatus = Status.OK;
                var available = true;

                var book = new Book(work, publisherName, yearOfPublishing, isbn, bookStatus, available);
                bookRepository.save(book);
            }
            System.out.println("Seeded book table successfully");
        }
    }

}
