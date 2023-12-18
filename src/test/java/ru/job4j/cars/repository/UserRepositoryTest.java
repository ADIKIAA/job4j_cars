package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {

    private static SessionFactory sessionFactory;

    private static CrudRepository crudRepository;

    private static UserRepository userRepository;

    @BeforeAll
    public static void setup() {
        try {

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("test-hibernate.cfg.xml").build();

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();

            crudRepository = new CrudRepository(sessionFactory);
            userRepository = new HbmUserRepository(crudRepository);

        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @AfterAll
    public static void tear() {
        sessionFactory.close();
    }

    @AfterEach
    public void deleteAll() {
        var users = crudRepository.query(
                "from User",
                User.class
        );
        for (User user : users) {
            userRepository.delete(user.getId());
        }
    }

    @Test
    public void createSomeUserThenFindByLogin() {
        User user1 = new User();
        user1.setLogin("login_1@test.com");
        user1.setPassword("password_1");
        User user2 = new User();
        user2.setLogin("login_2@test.com");
        user2.setPassword("password_2");

        userRepository.create(user1);
        userRepository.create(user2);

        var rsl1 = userRepository.findByLogin("login_1@test.com").get();
        var rsl2 = userRepository.findByLogin("login_2@test.com").get();

        assertThat(rsl1).usingRecursiveComparison().isEqualTo(user1);
        assertThat(rsl2).usingRecursiveComparison().isEqualTo(user2);
    }

    @Test
    public void createSomeUserThenDeleteOneAndFindByLikeLogin() {
        User user1 = new User();
        user1.setLogin("login_1@test.com");
        user1.setPassword("password_1");
        User user2 = new User();
        user2.setLogin("login_2@test.com");
        user2.setPassword("password_2");
        User user3 = new User();
        user3.setLogin("login_3@test.com");
        user3.setPassword("password_3");

        List<User> expected = List.of(user2, user3);

        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);
        userRepository.delete(user1.getId());

        var rsl = userRepository.findByLikeLogin("@test");

        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void createUserThemUpdateOneAndFindById() {
        User user1 = new User();
        user1.setLogin("login_1@test.com");
        user1.setPassword("password_1");

        userRepository.create(user1);

        String newLogin = "new_login";
        String newPassword = "new_password";
        user1.setLogin(newLogin);
        user1.setPassword(newPassword);

        userRepository.update(user1);

        var rsl = userRepository.findById(user1.getId()).get();

        assertThat(rsl.getLogin()).isEqualTo(newLogin);
        assertThat(rsl.getPassword()).isEqualTo(newPassword);
    }

    @Test
    public void createSomeUserThenFindAllOrderById() {
        User user1 = new User();
        user1.setLogin("login_1@test.com");
        user1.setPassword("password_1");
        User user2 = new User();
        user2.setLogin("login_2@test.com");
        user2.setPassword("password_2");
        User user3 = new User();
        user3.setLogin("login_3@test.com");
        user3.setPassword("password_3");

        List<User> expected = List.of(user1, user2, user3);

        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);

        var rsl = userRepository.findAllOrderById();

        assertThat(rsl).isEqualTo(expected);
    }

}